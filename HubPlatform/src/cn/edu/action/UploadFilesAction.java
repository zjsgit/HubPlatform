/**
 * 
 */
package cn.edu.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.bean.MultiDegree;
import cn.edu.degree.ComputeCentrality;

/**
 * 对提交的三个文件进行读取并保存，以便后续的处理
 * @author JiashuaiZhang
 *
 */
public class UploadFilesAction extends ActionSupport {

	private static final long serialVersionUID=1L;
	
	private File[] upload;// 封装上传文件属性
    private String[] uploadContentType;// 封装上传文件的类型
    private String[] uploadFileName; // 封装上传文件名称
    private List<MultiDegree> topNodes;
    
	public String execute() throws Exception {
    	
    	HttpServletRequest request=ServletActionContext.getRequest();
    	
    	String centralityDegree=request.getParameter("centralitymethod");

    	String [] readContent=new String[upload.length];
    	
        byte[] buffer = new byte[1024];
        for (int i = 0; i < upload.length; i++) {
        	
            FileInputStream fis = new FileInputStream(getUpload()[i]);
            
            int len = -1;  
			StringBuffer sb = new StringBuffer();  
			while ((len = fis.read(buffer)) != -1) {  
			    sb.append(new String(buffer, 0, len));  
			}  
			
			readContent[i]= sb.toString();  

			fis.close();

        }       
        

		//获取项目路径
		String path=request.getSession().getServletContext().getRealPath("");
        List<MultiDegree> multiDegrees = ComputeCentrality.computeMultiCentrality(centralityDegree, readContent,path);
        
		topNodes= ComputeCentrality.getTopProteins(multiDegrees, 10);
		        
        return SUCCESS;
    }
    
    
	/**
	 * @return the upload
	 */
	public File[] getUpload() {
		return upload;
	}
	/**
	 * @param upload the upload to set
	 */
	public void setUpload(File[] upload) {
		this.upload = upload;
	}
	/**
	 * @return the uploadContentType
	 */
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	/**
	 * @param uploadContentType the uploadContentType to set
	 */
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	/**
	 * @return the uploadFileName
	 */
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	/**
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	/**
	 * @return the topNodes
	 */
	public List<MultiDegree> getTopNodes() {
		return topNodes;
	}


	/**
	 * @param topNodes the topNodes to set
	 */
	public void setTopNodes(List<MultiDegree> topNodes) {
		this.topNodes = topNodes;
	}

	
	
}
