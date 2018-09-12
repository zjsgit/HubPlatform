/**
 * 
 */
package cn.edu.degree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;

import cn.edu.algorithms.DC;
import cn.edu.algorithms.MNC;
import cn.edu.algorithms.NC;
import utils.FileUtil;
import utils.StrUtil;

/**
 * @author JiashuaiZhang
 *
 */
public class StaticDegree {
		
	/**
	 * 根据网络计算出网络中节点的度中心性
	 * @param net
	 * @return
	 */
	public static Map<String,Double> calculateDC(String net,String path){
		
		List<String> netList = StrUtil.str2List(net);
		
		List<String> tNodes = getNodes(netList);
		
		List<String[]> tEdges = getEdges(netList);
			
		DC dc=new DC(tNodes, tEdges);
		HashMap<String, Double> dcMap = dc.process();
		
		//根据评分值降序排列
		List<Entry<String, Double>> list = new ArrayList<>(dcMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		
		FileUtil.writeList2File(list, path+"/temp/DCRank.txt");
		
		return dcMap;
		
	}
	
	/**
	 * 根据网络计算出网络中节点的MNC
	 * @param net
	 * @return
	 */
	public static Map<String,Double> calculateMNC(String net,String path){
		
		List<String> netList = StrUtil.str2List(net);
		
		List<String> tNodes = getNodes(netList);
		List<String[]> tEdges = getEdges(netList);
			
		MNC mnc=new MNC(tNodes, tEdges);
		HashMap<String, Double> mncMap = mnc.process();
		
		//根据评分值降序排列
		List<Entry<String, Double>> list = new ArrayList<>(mncMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		
		FileUtil.writeList2File(list, path+"/temp/MNCRank.txt");
		
		return mncMap;
		
	}
	
	/**
	 * 根据网络计算出网络中节点的NC
	 * @param net
	 * @return
	 */
	public static Map<String,Double> calculateNC(String net,String path){
		
		List<String> netList = StrUtil.str2List(net);
		
		List<String> tNodes = getNodes(netList);
		
		List<String[]> tEdges = getEdges(netList);
			
		NC nc=new NC(tNodes, tEdges);
		HashMap<String, Double> ncMap = nc.process();
		
		//根据评分值降序排列
		List<Entry<String, Double>> list = new ArrayList<>(ncMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		
		FileUtil.writeList2File(list, path+"/temp/NCRank.txt");
		
		return ncMap;
		
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
	
	
}
