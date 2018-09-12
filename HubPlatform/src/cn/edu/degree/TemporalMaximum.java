/**
 * 
 */
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

import cn.edu.algorithms.DC;
import cn.edu.algorithms.MNC;
import cn.edu.algorithms.NC;
import cn.edu.bean.DyProtein;
import utils.FileUtil;
import utils.NetUtil;
import utils.StrUtil;

/**
 * @author JiashuaiZhang
 *
 */
public class TemporalMaximum {

	public static final int tNum = 12;

	public static Map<String, Double> temporalCentrality(String centrality,
			String []fileContent, String path) {
				
		List<String> netList = StrUtil.str2List(fileContent[0]);
		
		//获取基因表达数据
		List<DyProtein> proList = new ArrayList<>();
		List<String> expList = StrUtil.str2List(fileContent[1]);
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
		
		HashMap<String, Double> maxMap = new HashMap<>();
		
		//构建动态网络
		for (int i = 0; i < tNum; i++) {
			HashSet<String> set = new HashSet<>();
			for (DyProtein pro : proList) {
				if (pro.getExps()[i]>pro.getThreshold()) {
					set.add(pro.getName());
				}
			}
			List<String> edges = NetUtil.getTempralNet(set, netList);
	
			// 将12个时刻的网络保存下来
			FileUtil.write2File(edges, path+"/temp/"+i+"_timeNetwork.txt");
			
			List<String> tNodes = getNodes(edges);
			List<String[]> tEdges = getEdges(edges);

			// 中心性计算
			HashMap<String, Double> tMap = null;
			if(centrality.equals("DC")){
				DC dc=new DC(tNodes, tEdges);
				tMap = dc.process();
			}else if(centrality.equals("NC")){
				NC nc=new NC(tNodes, tEdges);
				tMap  = nc.process();
			}else if(centrality.equals("MNC")){
				MNC mnc=new MNC(tNodes, tEdges);
				tMap  = mnc.process();
			}else{
				DC dc=new DC(tNodes, tEdges);
				tMap  = dc.process();
			}
			
			//记录各个时刻的最大值
			for(Entry<String, Double> entry:tMap.entrySet()){
				if (maxMap.containsKey(entry.getKey())) {
					double temp = maxMap.get(entry.getKey());
					if (temp<entry.getValue()) {
						maxMap.replace(entry.getKey(), entry.getValue());
					}
				}else {
					maxMap.put(entry.getKey(), entry.getValue());
				}
			}
	
		}
		
		//根据评分值降序排列
		List<Entry<String, Double>> list = new ArrayList<>(maxMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		
		FileUtil.writeList2File(list, path+"/temp/TemporalRank.txt");
		
		return maxMap;

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
