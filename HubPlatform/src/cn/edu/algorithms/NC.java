package cn.edu.algorithms;

import java.util.HashMap;
import java.util.List;

/**
 * 总和边聚集系数方法
 * @author lwk
 * @version Apr 3, 2017 3:30:27 PM
 */
public class NC extends Centrality{
	
	
	public NC(List<String> nodes, List<String[]> edges) {
		this.nodes = nodes;
		this.edges = edges;
		
		//构建邻接矩阵
		adjMatrix = makeMatrix(nodes, edges);
	}

	@Override
	public HashMap<String, Double> process() {
		HashMap<String, Double> map = new HashMap<>();
		
		int[] degrees = new int[nodes.size()];
		for (int i = 0; i <nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (adjMatrix[i][j]!=0) {
					degrees[i] = degrees[i] + 1;
				}
			}
		}
		
		for (int i = 0; i < nodes.size(); i++) {
			double result = 0;
			for (int j = 0; j < nodes.size(); j++) {
				if (adjMatrix[i][j] != 0) {
					int triNum = 0;
					for (int k = 0; k < nodes.size(); k++) {
						if (adjMatrix[i][k]!=0 && adjMatrix[k][j]!=0) {
							triNum = triNum + 1;
						}
					}
					int min = Math.min(degrees[i]-1, degrees[j]-1);
					if (min != 0) {
						result = result + (double) triNum / min;
					}
				}
			}
			map.put(nodes.get(i), result);
		}
		
		return map;
	}
	

}
