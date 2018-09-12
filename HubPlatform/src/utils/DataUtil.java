package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 数据操作
 * @author lwk
 * @version Mar 21, 2017 10:49:04 AM
 */
public class DataUtil {
	
	public static void compare(HashSet<String> xSet, HashSet<String> ySet){
		int sameNum = 0;
		for (String str : xSet) {
			if (ySet.contains(str)) {
				sameNum++;
			}
		}
		System.out.printf("比较的数据集合大小：%d,%d\n", xSet.size(), ySet.size());
		System.out.printf("两个集合中相同元素的数量：%d\n", sameNum);
	}
	
	public static void compare(HashSet<String> xSet, HashSet<String> ySet, HashSet<String> zSet){
		int sameNum = 0;
		for (String str : xSet) {
			if (ySet.contains(str) && zSet.contains(str)) {
				sameNum++;
			}
		}
		System.out.printf("比较的数据集合大小：%d,%d,%d\n", xSet.size(), ySet.size(), zSet.size());
		System.out.printf("三个集合中相同元素的数量：%d\n", sameNum);
	}
	
	public static void evaluate(List<String> nodes, HashSet<String> essentials){
		
		System.out.printf("蛋白质节点和关键蛋白质列表的大小为：%d, %d\n",nodes.size(),essentials.size());
		int[] topNums = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
		for (int topNum : topNums) {
			System.out.print(topNum + "\t");
		}
		System.out.println();

		for (int topNum : topNums) {
			if (nodes.size()>=topNum) {
				int tpNum = 0;
				for (int i = 0; i < topNum; i++) {
					String node = nodes.get(i);
					if (essentials.contains(node)) {
						tpNum = tpNum + 1;
					}
				}
				System.out.print(tpNum + "\t");
			}
		}
		System.out.println();
	}
	
	/**
	 * 将预测的关键蛋白与关键蛋白比对，按照Map格式将结果存储起来返回
	 * 
	 * @param nodes
	 * @param essentials
	 * @return
	 */
	public static int[] graphData(List<String> nodes, HashSet<String> essentials){
		int[] topNums = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

		int [] graphResult=new int [topNums.length];
		for (int n=0;n<topNums.length;n++) {
			if (nodes.size()>=topNums[n]) {
				int tpNum = 0;
				for (int i = 0; i < topNums[n]; i++) {
					String node = nodes.get(i);
					if (essentials.contains(node)) {
						tpNum = tpNum + 1;
					}
				}
				graphResult[n]=tpNum;
			}
		}
		
		return graphResult;
	}
	
	
	
	public static void jackknife(List<String> topList, HashSet<String> essentials){
		int tp = 0;
		for (int i = 0; i < 1000; i++) {
			if (essentials.contains(topList.get(i))) {
				tp = tp + 1;
			}
			if ((i+1)%10==0) {
				//System.out.println((double)tp / (i+1));
				System.out.println(tp);
			}
		}
	}

	public static void statistics(int top, List<String> nodes, HashSet<String> essentials){

		//蛋白质网络中已知关键蛋白质总数
		int essNum = 0;
		for (String node:nodes){
			if (essentials.contains(node)){
				essNum = essNum + 1;
			}
		}
		System.out.println("蛋白质网络中的已知关键蛋白质总数："+essNum);

		top = (top==0)?essNum:top;
		int tp=0,fp=0;
		for (int i = 0; i < top; i++) {
			if (essentials.contains(nodes.get(i))){
				tp = tp + 1;
			}else {
				fp = fp + 1;
			}
		}
		int fn=0,tn=0;
		for (int i = top; i < nodes.size(); i++) {
			if (essentials.contains(nodes.get(i))){
				fn = fn + 1;
			}else {
				tn = tn + 1;
			}
		}

		double sn = 1.0 * tp / (tp+fn);
		double sp = 1.0 * tn / (tn+fp);
		double ppv = 1.0 * tp / (tp+fp);
		double npv = 1.0 * tn / (tn+fn);
		double fScore = 2.0 * sn * ppv / (sn+ppv);
		double acc = 1.0 * (tp+tn) / (tp+tn+fp+fn);

		DecimalFormat df = new DecimalFormat("0.000");
		System.out.printf("%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%d\n",sn,sp,ppv,npv,fScore,acc,tp);

	}

	public static void differentiation(int top, List<String> ourNodes, List<String> othNodes, HashSet<String> essentials){
		List<String> ourTop = ourNodes.subList(0,top);
		List<String> othTop = othNodes.subList(0,top);

		List<String> difList = new ArrayList<>();
		int comNum = 0;
		for (String node:ourTop){
			if (!othTop.contains(node)){
				difList.add(node);
			}else {
				othTop.remove(node);
				comNum = comNum + 1;
			}
		}
		System.out.println("差异性蛋白质数目:"+difList.size()+";相同蛋白质数目:"+comNum);

		int ourEssNum = 0;
		for (String node:difList){
			if (essentials.contains(node)){
				ourEssNum = ourEssNum + 1;
			}
		}
		System.out.printf("所提方法的识别数目：%d\t%.3f\n",ourEssNum,(double)ourEssNum/difList.size());
		int othEssNum = 0;
		for (String node:othTop){
			if (essentials.contains(node)){
				othEssNum = othEssNum + 1;
			}
		}
		System.out.printf("对比方法的识别数目：%d\t%.3f\n",othEssNum,(double)othEssNum/othTop.size());
	}

	/**
	 * 不同TOP集时关键蛋白质所占比例
	 * @param topList
	 * @param essetnials
	 */
	public static void calculateFraction(List<String> topList, HashSet<String> essetnials){
		int essNum = 0;
		System.out.println("TOP\tFRACTION");
		for (int topLoc=0; topLoc<=1000; topLoc++){
			if (essetnials.contains(topList.get(topLoc))){
				essNum = essNum + 1;
			}
			if (topLoc%20 == 0){
				double fra = 1.0 * essNum / topLoc;
				System.out.println(topLoc+"\t"+fra);
			}
		}
	}
}
