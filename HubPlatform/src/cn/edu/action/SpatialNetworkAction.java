/**
 * 
 */
package cn.edu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
public class SpatialNetworkAction extends ActionSupport {

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
	 * 显示空间网络
	 * @return
	 * @throws Exception
	 */

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		List<String> network = FileUtil.readFile2List(path+"temp/spatialNetwork.txt");
		
		List<String> subNetwork = getSubSpatialNetwork(network,10,"outer");

		jsonResult=encapsulateSpatialNet2Json(subNetwork);
		
		return SUCCESS;
	}
	
	public String updateNetwork() throws Exception{
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String proteinNumber=request.getParameter("proteinNumber");
		String neighborsStyle=request.getParameter("neighborsStyle");
		
		List<String> network = FileUtil.readFile2List(path+"temp/spatialNetwork.txt");
		
		List<String> subNetwork = getSubSpatialNetwork(network,Integer.valueOf(proteinNumber),neighborsStyle);
		jsonResult=encapsulateSpatialNet2Json(subNetwork);
		
		
		return SUCCESS;
	}

	/**
	 * 获取指定个数节点的空间网络的局部网络
	 * @param net
	 * @param nodeNumber
	 * @return	包含前n个节点的局部网络
	 */
	public static List<String> getSubSpatialNetwork(List<String> net,int nodeNumber,String neighborsStyle){
		List<String> subNetwork=new ArrayList<String>();
		
		List<String> topProteins=FileUtil.readFile2List(path+"/temp/SpatialRank.txt");
		int index=0;
		switch(neighborsStyle){
			case "outer":
				for(String portein:topProteins){
					for(String edge:net){
						String [] nodes=edge.split("\t");
						if(nodes[0].equals(portein)||nodes[1].equals(portein)){
							subNetwork.add(edge);
						}
					}
					index++;
					if(index==nodeNumber){
						break;
					}
				}
			break;
			case "inner":
				List<String> topNumProteins=new ArrayList<String>();
				for(String protein:topProteins){
					index++;
					topNumProteins.add(protein);
					if(index==nodeNumber){
						break;
					}
				}
				for(String edge:net){
					String [] nodes=edge.split("\t");
					if(topNumProteins.contains(nodes[0]) && topNumProteins.contains(nodes[1])){
						subNetwork.add(edge);
					}
				}
			break;
			
		}
		
		return subNetwork;
	}
	
	/**
	 * 将空间网络数据封装成JSON格式数据以便Cytoscape可视化
	 * @param datasets
	 * 即一条条边组成的网络集合
	 * @return
	 * 封装成JSON格式的数据
	 */
	public static String encapsulateSpatialNet2Json(List<String> datasets){
		
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
			edge.put("data", data);
			edges.add(edge);
		}
		
		List<String> locations=FileUtil.readFile2List(path+"/temp/locations.txt");
		Map<String,String> proteinLocation = listConvertMap(locations);
		//JSON封装节点数据
		JSONArray nodes = new JSONArray();
		for (String proteinNode : proteinNodes) {
			JSONObject node = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("id", proteinNode);
			data.put("name", proteinNode);
			if(proteinLocation.containsKey(proteinNode)){
				data.put("label", proteinLocation.get(proteinNode));
			}else{
				data.put("label", "none");
			}
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
	
	public static  Map<String,String> listConvertMap(List<String> locations){
		Map<String,String> proteinLocation=new HashMap<String,String>();
		for(String location:locations){
			String [] protein=location.split("\t");
			proteinLocation.put(protein[0], protein[1]);
		}
		
		return proteinLocation;
	}
	
	
}
