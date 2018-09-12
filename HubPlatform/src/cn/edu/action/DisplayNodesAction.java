/**
 * 
 */
package cn.edu.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.bean.MultiDegree;
import utils.FileUtil;

/**
 * @author JiashuaiZhang
 *
 */
public class DisplayNodesAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String path=request.getSession().getServletContext().getRealPath("");
	
		String proteinNumber=request.getParameter("topNodes");
		
		List<String> allNodes = FileUtil.readFile2List(path+"downloadFiles/multiCentrality.txt");
		List<MultiDegree> topNodes = getTopNodes(allNodes ,Integer.valueOf(proteinNumber));
		
		ActionContext context=ActionContext.getContext();
		context.put("topNodes", topNodes);
		
		return SUCCESS; 
	}

	private List<MultiDegree> getTopNodes(List<String> allNodes, int number) {
		// TODO Auto-generated method stub
		
		List<MultiDegree> multiDegrees=new ArrayList<MultiDegree>();
		int index=0;
		for(String node:allNodes){
			
			String []strNode=node.split("\t");
			
			MultiDegree multiCentrality=new MultiDegree();
			multiCentrality.setProteinName(strNode[0]);
			multiCentrality.setTSDegree(Double.valueOf(strNode[1]));
			multiCentrality.setTDegree(Double.valueOf(strNode[2]));
			multiCentrality.setSDegree(Double.valueOf(strNode[3]));
			multiCentrality.setDCValue(Double.valueOf(strNode[4]));
			multiCentrality.setMNCValue(Double.valueOf(strNode[5]));
			multiCentrality.setNCValue(Double.valueOf(strNode[6]));
			multiDegrees.add(multiCentrality);
			
			index++;
			if(index==number){
				break;
			}
		}
		
		return multiDegrees;
	}

	
	
	
}
