/**
 * 
 */
package cn.edu.action;

import java.io.InputStream;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author JiashuaiZhang
 *
 */
public class DownloadFileAction extends ActionSupport {

	private static final long serialVersionUID=1L;
	
	private String path;
	private String fileName;
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	
	public InputStream getDownloadFile() throws Exception{
		
		Map<String ,Object> map =ActionContext.getContext().getParameters();
		String [] fileNames = (String[]) map.get("fileName");
        //遍历数组获取对应的value值（本文的值只有一个）
        fileName = fileNames[0];
        
		InputStream in = ServletActionContext.getServletContext().
				getResourceAsStream(getPath()+fileName);
		
		return in;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
				
		
		
		return SUCCESS;
	}
	
	
}
