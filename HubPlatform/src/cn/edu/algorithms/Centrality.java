package cn.edu.algorithms;

import java.util.HashMap;
import java.util.List;

/**
 * 方法类
 * @author lwk
 * @version Apr 3, 2017 2:45:58 PM
 */
public abstract class Centrality {
	
	public List<String> nodes;
	public List<String[]> edges;
	public int[][] adjMatrix;
	
	/**
	 * 具体计算方法
	 */
	public abstract HashMap<String, Double> process();
	
	/**
	 * 构造无权重网络
	 */
	public int[][] makeMatrix(List<String> nodes, List<String[]> edges) {

		int len = nodes.size();
		int eLen = edges.size();
		int[][] data = new int[len][len];
		String[] edge;
		int m, n;
		try {
			for (m = 0; m < len; m++) {
				for (n = 0; n < len; n++) {
					data[m][n] = 0;
				}
			}
			for (int i = 0; i < eLen; i++) {
				edge = edges.get(i);
				for (m = 0; m < len; m++) {
					if (nodes.get(m).equals(edge[0])) {
						break;
					}
				}
				for (n = 0; n < len; n++) {
					if (nodes.get(n).equals(edge[1])) {
						break;
					}
				}
				if (n < len) {
					data[n][m] = 1;
					data[m][n] = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
	/**
	 * 构造加权网络
	 */
	public int[][] makeWeiMatrix(List<String> nodes, List<String[]> edges) {

		int len = nodes.size();
		int eLen = edges.size();
		int[][] data = new int[len][len];
		String[] edge;
		int m, n;
		for (m = 0; m < len; m++) {
			for (n = 0; n < len; n++) {
				data[m][n] = 0;
			}
		}
		for (int i = 0; i < eLen; i++) {
			edge = edges.get(i);
			for (m = 0; m < len; m++) {
				if (nodes.get(m).equals(edge[0])) {
					break;
				}
			}
			for (n = 0; n < len; n++) {
				if (nodes.get(n).equals(edge[1])) {
					break;
				}
			}
			if (n < len) {
				data[n][m] = Integer.parseInt(edge[2]);
				data[m][n] = Integer.parseInt(edge[2]);
			}
		}

		return data;
	}
}
