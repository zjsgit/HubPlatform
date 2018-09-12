package cn.edu.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Maximum Neighborhood Component
 * @author lwk
 *
 */
public class MNC extends Centrality{

	public MNC(List<String> nodes, List<String[]> edges) {
		this.nodes = nodes;
		this.edges = edges;
		
		//构建邻接矩阵
		adjMatrix = makeMatrix(nodes, edges);
	}
	
	@Override
	public HashMap<String, Double> process() {
		
		HashMap<String, Double> map = new HashMap<>();
		
		for (int i = 0; i < nodes.size(); i++) {
			double result = 0;
			//节点i的邻居节点
			List<Integer> neighbors = new ArrayList<>();
			for (int j = 0; j < nodes.size(); j++) {
				if (adjMatrix[i][j] != 0) {
					neighbors.add(j);
				}
			}
			if (neighbors.size()>0) {
				//由邻居节点构成的子图
				int[][] subAdm = new int[neighbors.size()][neighbors.size()];
				subgraph(subAdm, neighbors);
				//求子图的最大连通分量
				ConnectedCompent cc = new ConnectedCompent(subAdm);
				result = (double) cc.getMaxCCVertex();
			}
			map.put(nodes.get(i), result);
		}
		
		return map;
	}
	
	/**
	 * 由邻居节点构成的网络的邻接矩阵
	 * @param subAdm
	 * @param neighbors
	 */
	private void subgraph(int[][] subAdm, List<Integer> neighbors) {
		
		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = i+1; j < neighbors.size(); j++) {
				if (adjMatrix[neighbors.get(i)][neighbors.get(j)] != 0) {
					subAdm[i][j] = 1;
					subAdm[j][i] = 1;
				}
			}
		}
	}

}
