/**
 * 
 */
package cn.edu.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.degree.NetworkFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.FileUtil;
import utils.NetUtil;
import utils.StrUtil;

/**
 * @author JiashuaiZhang
 *
 */
public class UploadFileAction extends ActionSupport {

	private static final long serialVersionUID=1L;
	
	private File uploadFile;
	private String uploadFileFileName;
	private String uploadFileContentType;
	private JSONObject jsonObject;

	/**
	 * @return the jsonObject
	 */
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	/**
	 * @param jsonObject the jsonObject to set
	 */
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * @return the uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @param uploadFileFileName the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	/**
	 * @return the uploadFileContentType
	 */
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	/**
	 * @param uploadFileContentType the uploadFileContentType to set
	 */
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
    	
		if(uploadFile!=null){
			
			FileInputStream fis=new FileInputStream(getUploadFile());
			
			int i = -1;  
			byte[] b = new byte[1024];  
			StringBuffer sb = new StringBuffer();  
			while ((i = fis.read(b)) != -1) {  
			    sb.append(new String(b, 0, i));  
			}  
			
			String str= sb.toString();  
			fis.close();
			
			jsonObject = filterNetwork(str) ;
				
		}
			
		return SUCCESS;
		
	}
	
	protected JSONObject filterNetwork(String fileContent){
		
		HttpServletRequest request=ServletActionContext.getRequest();
		
		//将要传入页面的数据变成json格式
        JSONObject jsonObject=new JSONObject();
        
        
		int []netBefore=new int[2];//存储网络过滤前后的节点数和边数
		List<String> net = StrUtil.str2List(fileContent);
		netBefore=NetUtil.getNodeEege4Net(net);
		
		jsonObject.put("beforeEdge", netBefore[0]);
		jsonObject.put("beforeNode", netBefore[1]);
		
		
		List<String> filterResult = null;
		filterResult = NetworkFilter.filterNetwork(fileContent);
				
		String path=request.getSession().getServletContext().getRealPath("")
		        + "/downloadFiles/";
			
		FileUtil.write2File( filterResult ,  path+"net.txt");
		
		int []netAfter=new int[2];//存储网络过滤前后的节点数和边数
		netAfter=NetUtil.getNodeEege4Net(filterResult);
		
		jsonObject.put("afterEdge", netAfter[0]);
		jsonObject.put("afterNode", netAfter[1]);
		
		
		return jsonObject;
				
	}
	
}
