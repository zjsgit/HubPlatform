package cn.edu.degree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.edu.algorithms.*;
import cn.edu.bean.*;
import utils.*;

/*
 * 使用DyWang构造时空动态网络，按照度中心性排序
 * 返回Map结果
 */


public class DyWang {

	public static String[] locations = {"CYTOSKELETON","CYTOSOL","ENDOPLASMIC","ENDOSOME",
			"EXTRACELLULAR","GOLGI","LYSOSOME","MITOCHONDRION","NUCLEUS","PEROXISOME","PLASMA"};
	
	public static final int tNum = 12;
	
	public static Map<String, Double> calculateTSCentrality(String centrality,
			String []fileContent){
		
		List<String> netList = StrUtil.str2List(fileContent[0]);
		List<String> expList = StrUtil.str2List(fileContent[1]);
		List<String> allSubs = StrUtil.str2List(fileContent[2]);
			
		
		//获取基因表达数据
		List<DyProtein> proList = new ArrayList<>();
		for (String exp : expList) {
			String[] infos = exp.split("\t");
			double[] arr = new double[tNum];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Double.parseDouble(infos[i + 1]);
			}
			proList.add(new DyProtein(infos[0], arr));
		}
		
		//计算活性阈值
		for (DyProtein pro : proList) {
			double threshold = getThreshold(pro.getExps());
			pro.setThreshold(threshold);

		}
		
		//每个亚细胞位置中对应的蛋白质节点集合
		HashMap<String, HashSet<String>> subMap = new HashMap<>();
		for (String location:locations) {
			HashSet<String> proteins = new HashSet<>();
			for (String str:allSubs) {
				String[] infos = str.toUpperCase().split("\t");
				if (infos[1].contains(location)) {
					proteins.add(infos[0]);
				}
			}
			subMap.put(location, proteins);

		}
		
		HashMap<String, Double> maxMap = new HashMap<>();
		
		//构建动态网络
		for (int i = 0; i < tNum; i++) {
			
			HashSet<String> set = new HashSet<>();
			for (DyProtein pro : proList) {
				if (pro.getExps()[i]>pro.getThreshold()) {
					set.add(pro.getName());
				}
			}
			List<String> tNet = NetUtil.getTempralNet(set, netList);

			for (String location:locations) {
				HashSet<String> proteins = subMap.get(location);
				List<String> subEdges = new ArrayList<>();
				for(String str:tNet){
					String[] arr = str.toUpperCase().split("\t");
					if (proteins.contains(arr[0])&&proteins.contains(arr[1])) {
						subEdges.add(str);
					}
				}
				
				//获取某一时刻的网络边和节点
				List<String> dsNodes = getNodes(subEdges);
				List<String[]> dsEdges = getEdges(subEdges);

				//中心性计算
				HashMap<String, Double> dsMap = null;
				if(centrality.equals("DC")){
					DC dc=new DC(dsNodes,dsEdges);
					dsMap = dc.process();
				}else if(centrality.equals("NC")){
					NC nc=new NC(dsNodes,dsEdges);
					dsMap = nc.process();
				}else if(centrality.equals("MNC")){
					MNC mnc=new MNC(dsNodes,dsEdges);
					dsMap = mnc.process();
				}else{
					DC dc=new DC(dsNodes,dsEdges);
					dsMap = dc.process();
				}
				
				//选择节点在所有时刻中的最大值作为评分的依据
				for(Entry<String, Double> entry:dsMap.entrySet()){
					if (maxMap.containsKey(entry.getKey())) {
						double oldValue = maxMap.get(entry.getKey());
						if (oldValue<entry.getValue()) {
							maxMap.replace(entry.getKey(), entry.getValue());
						}
					}else {
						maxMap.put(entry.getKey(), entry.getValue());
					}
				}
				
				
			}
		
		}
		
		return maxMap;
				
	}
		

	/**
	 * 输出时空子网络
	 * @param list 时空子网的边列表
	 * @param path 存储路径
	 */
	public static void printTSNetwork(List<String[]> list, String path){
		List<String> tmpList = new ArrayList<>();
		for(String[] arr:list){
			tmpList.add(arr[0]+"\t"+arr[1]);
		}
		FileUtil.write2File(tmpList, path);
	}
	
	/**
	 * 输出节点评分结果
	 * @param list
	 * @param set
	 * @param path
	 */
	public static void printCalculationResult(List<Entry<String, Double>> list, String path){
		
		List<String> resList = new ArrayList<>();
		for(Entry<String, Double> entry:list){
			resList.add(entry.getKey()+"\t"+entry.getValue());
		}
		FileUtil.write2File(resList, path);
		
	}

	/**
	 * 将[节点-DC值]取出前10，存放在Map中
	 * @param list
	 * @return
	 */
	public static Map<String,Double> topTenResult(List<Entry<String, Double>> list){

		Map<String,Double> result=new HashMap<String,Double>();
		int index=0;
		for(Entry<String, Double> entry:list){

			if (index==10){
				break;
			}
			result.put(entry.getKey(), entry.getValue());
			index++;
		}
		return result;
		
	}
	
	
	public static List<String> getNodes(List<String> tList) {
		
		HashSet<String> set = new HashSet<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			set.add(arr[0]);
			set.add(arr[1]);
		}
		return new ArrayList<>(set);
		
	}

	
	public static List<String[]> getEdges(List<String> tList) {
		List<String[]> list = new ArrayList<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			list.add(arr);
		}
		return list;
	}
	
	/**
	 * 获取对应蛋白质的活性阈值
	 * @param arr
	 * @return
	 */
	public static double getThreshold(double[] arr){
		DescriptiveStatistics ds = new DescriptiveStatistics(arr);
		double mu = ds.getMean();
		double sigma = ds.getStandardDeviation();
		double threshold = mu+ 3.0*sigma*(1-1.0/(1+sigma*sigma));
		return threshold;
	}
	
}
