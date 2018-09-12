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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.edu.algorithms.DC;
import cn.edu.algorithms.MNC;
import cn.edu.algorithms.NC;
import cn.edu.bean.Subcellular;
import utils.FileUtil;
import utils.StrUtil;

/**
 * 基于亚细胞定位信息和原始网络进行子网的划分，
 * 并返回按子网可信度由大到小排序的子网集合。
 * 
 * @author JiashuaiZhang
 * @param centrality
 * @param fileContent
 * 
 */
public class SpatialMaximum {

	public static String[] locations = { "CYTOSKELETON", "CYTOSOL", "ENDOPLASMIC", "ENDOSOME", "EXTRACELLULAR", "GOLGI",
			"LYSOSOME", "MITOCHONDRION", "NUCLEUS", "PEROXISOME", "PLASMA" };

	public static Map<String,Double> spatialCentrality(String centrality,String []fileContent,String path) {

		// 根据亚细胞定位信息划分子网络
		List<String> netList = StrUtil.str2List(fileContent[0]);
		List<String> allSubs = StrUtil.str2List(fileContent[2]);
		
		List<Subcellular> resList = new ArrayList<>();
		//获取符合亚细胞条件的蛋白质节点
		List<HashSet<String>> subList = new ArrayList<>();
		Map<String,String> nodePlace=new HashMap<String,String>();//节点的位置信息
		for(String location:locations){
			HashSet<String> proteins = new HashSet<>();
			
			for(String str:allSubs){
				String[] infos = str.toUpperCase().split("\t");
				if (infos[1].contains(location)) {
					proteins.add(infos[0]);
					if(!nodePlace.containsKey(infos[0])){
						nodePlace.put(infos[0], location);
						
					}
				}
			}
			
			subList.add(proteins);
		}
		
		write2File(nodePlace,path+"/temp/locations.txt");//将节点的位置信息写入文件
		
		//获取每个亚细胞子网中的点和边		
		for (int i = 0; i < locations.length; i++) {
			HashSet<String> proteins = subList.get(i);
			List<String> interactions = new ArrayList<>();
			for(String str:netList){
				String[] arr = str.toUpperCase().split("\t");
				if (proteins.contains(arr[0])&&proteins.contains(arr[1])) {
					interactions.add(str);
				}
			}
			//初始化亚细胞对象
			Subcellular subCell = new Subcellular(locations[i]);
			List<String> teNodes = getNodes(interactions);
			subCell.setNodes(teNodes);
			List<String[]> teEdges = getEdges(interactions);
			subCell.setEdges(teEdges);
			double result = 1.0 * teNodes.size();
			subCell.setReliability(result);
			
			resList.add(subCell);
		}
		
		//根据亚细胞区间的可信度值由小到大进行排序
		Collections.sort(resList, new Comparator<Subcellular>() {
			@Override
			public int compare(Subcellular o1, Subcellular o2) {
				return Double.compare(o2.getReliability(), o1.getReliability());
			}
		});
		
		// 用于存储每个节点的打分值
		HashMap<String, Double> scores = null;
		
		// 计算中心性值
		if(centrality.equals("DC")){
			DC dc=new DC(resList.get(0).getNodes(), resList.get(0).getEdges());
			scores = dc.process();
		}else if(centrality.equals("NC")){
			NC nc=new NC(resList.get(0).getNodes(), resList.get(0).getEdges());
			scores = nc.process();
		}else if(centrality.equals("MNC")){
			MNC mnc=new MNC(resList.get(0).getNodes(), resList.get(0).getEdges());
			scores = mnc.process();
		}else{
			DC dc=new DC(resList.get(0).getNodes(), resList.get(0).getEdges());
			scores = dc.process();
		}
		
		write2File(resList.get(0).getEdges(),path+"/temp/spatialNetwork.txt");
		
		// 根据评分值降序排列
		List<Entry<String, Double>> list = new ArrayList<>(scores.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		FileUtil.writeList2File(list, path+"/temp/SpatialRank.txt");
		
		return scores;
	}
	
	
	/**
	 * 根据网络列表获取所有节点集合
	 * @param tList
	 * @return
	 */
	public static List<String> getNodes(List<String> tList) {
		HashSet<String> set = new HashSet<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			set.add(arr[0]);
			set.add(arr[1]);
		}
		return new ArrayList<>(set);
	}
	
	/**
	 * 根据网络列表获取所有边集合
	 * @param tList
	 * @return
	 */
	public static List<String[]> getEdges(List<String> tList) {
		List<String[]> list = new ArrayList<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			list.add(arr);
		}
		return list;
	}
	
	
	
	public static void write2File(Map<String,String> proteins,String path){
		
		File file = new File(path);
		
        if (file.exists()) {
            System.out.println("文件已存在");
        } else {
            try {
                File fileParent = file.getParentFile();
                if (fileParent != null) {
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		try {
			PrintWriter writer = new PrintWriter(path);
			for(Entry<String, String> entry : proteins.entrySet()){
				writer.println(entry.getKey()+"\t"+entry.getValue());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public static void write2File(List<String[]> edges,String path){

		File file = new File(path);
		
        if (file.exists()) {
            System.out.println("文件已存在");
        } else {
            try {
                File fileParent = file.getParentFile();
                if (fileParent != null) {
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		try {
			PrintWriter writer = new PrintWriter(path);
			for(String []edge:edges){
				writer.println(edge[0]+"\t"+edge[1]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

}
