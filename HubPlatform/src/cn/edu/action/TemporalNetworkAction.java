/**
 * 
 */
package cn.edu.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.FileUtil;

/**
 * 对网络进行数据可视化
 * @author JiashuaiZhang
 *
 */
public class TemporalNetworkAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private static String path=ServletActionContext.getRequest().getSession().
			getServletContext().getRealPath("");
	
	private String jsonResult;

	/**
	 * @return the jsonResult
	 */
	public String getJsonResult() {
		return jsonResult;
	}

	/**
	 * @param jsonResult the jsonResult to set
	 */
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}


	/**
	 * 显示时间网络
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		List<String> network = FileUtil.readFile2List(path+"/temp/1_timeNetwork.txt");
		
		List<String> subNetwork = getSubTemporalNetwork(network,10);

		jsonResult=encapsulateNet2Json(subNetwork);
		
		return SUCCESS;
	}

	public String updateNetwork() throws Exception{
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String proteinNumber=request.getParameter("proteinNumber");
		String time=request.getParameter("selectTime");
		List<String> network = FileUtil.readFile2List(path+"/temp/"+time+"_timeNetwork.txt");
		List<String> subNetwork = getSubTemporalNetwork(network,Integer.valueOf(proteinNumber));
		jsonResult=encapsulateNet2Json(subNetwork);
		
		
		return SUCCESS;
	}

	
	/**
	 * 获取指定个数节点的时间网络的局部网络
	 * @param net
	 * @param nodeNumber
	 * @return	包含前n个节点的局部网络
	 */
	public static List<String> getSubTemporalNetwork(List<String> net,int nodeNumber){
		List<String> subNetwork=new ArrayList<String>();
		
		List<String> topProteins=FileUtil.readFile2List(path+"/temp/temporalRank.txt");
		for(int i=0;i<nodeNumber;i++){
			String node=topProteins.get(i);
			for(int j=0;j<net.size();j++){
				String [] nodes=net.get(j).split("\t");
				if(nodes[0].equals(node)||nodes[1].equals(node)){
					subNetwork.add(net.get(j));
				}
			}
		}
		
		return subNetwork;
	}
	
	/**
	 * 将网络数据封装成JSON格式数据以便Cytoscape可视化
	 * @param datasets
	 * 即一条条边组成的网络集合
	 * @return
	 * 封装成JSON格式的数据
	 */
	public static String encapsulateNet2Json(List<String> datasets){
		
		HashSet<String> proteinNodes = new HashSet<>();
		JSONArray edges = new JSONArray();
		for (String eachData : datasets) {
			String[] dataset = eachData.split("\\t|\\s");
			//获取所有的蛋白质节点
			proteinNodes.add(dataset[0]);
			proteinNodes.add(dataset[1]);
			//JSON封装边数据
			JSONObject edge = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("source", dataset[0]);
			data.put("target", dataset[1]);
//			if (dataset.length == 3) {
//				data.put("label", dataset[2]);
//			}
			edge.put("data", data);
			edges.add(edge);
		}
		//JSON封装节点数据
		JSONArray nodes = new JSONArray();
		for (String proteinNode : proteinNodes) {
			JSONObject node = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("id", proteinNode);
			data.put("name", proteinNode);
			node.put("data", data);
			nodes.add(node);
		}
		//Cytoscape.js的JSON格式数据
		JSONObject cydatas = new JSONObject();
		cydatas.put("nodes", nodes);
		cydatas.put("edges", edges);
		//System.out.println(cydatas.toString());
		
		return cydatas.toString();
	}
	
	
}
