package cn.edu.algorithms;

import java.util.HashMap;
import java.util.List;

/**
 * 度中心性方法
 * @author lwk
 * @version Apr 3, 2017 2:50:24 PM
 */
public class DC extends Centrality{
	
	public DC(List<String> nodes, List<String[]> edges) {
		this.nodes = nodes;
		this.edges = edges;
		
		//构建邻接矩阵
		adjMatrix = makeMatrix(nodes, edges);
	}

	@Override
	public HashMap<String, Double> process() {
		HashMap<String, Double> map = new HashMap<>();
		
		for (int i = 0; i < nodes.size(); i++) {
			int neiNum = 0;
			for (int j = 0; j < nodes.size(); j++) {
				if (adjMatrix[i][j] != 0) {
					neiNum = neiNum + 1;
				}
			}
			map.put(nodes.get(i), (double)neiNum);
		}
		
		return map;
	}
	
}
