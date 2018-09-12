/**
 * 
 */
package cn.edu.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.bean.MailSenderInfo;
import cn.edu.bean.SimpleMailSender;
import net.sf.json.JSONObject;

/**
 * @author JiashuaiZhang
 *
 */
public class SendMailAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private JSONObject sendResult;
	
	/**
	 * @return the sendResult
	 */
	public JSONObject getSendResult() {
		return sendResult;
	}

	/**
	 * @param sendResult the sendResult to set
	 */
	public void setSendResult(JSONObject sendResult) {
		this.sendResult = sendResult;
	}

	@Override
	public String execute() throws Exception {
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String name=request.getParameter("username");
		String email=request.getParameter("useremail");
		String subject=request.getParameter("usersubject");
		String content=request.getParameter("usercontent");
		
		content+="\n"+name+"\t"+email;
		
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setFromAddress("henu_zjs@163.com");// 自己邮箱
		mailInfo.setToAddress("1098605965@qq.com");// 目标邮箱
		mailInfo.setUserName("henu_zjs@163.com");// 自己邮箱
		// 需要开启此邮箱的POP3/SMTP/IMAP服务，具体设置进入邮箱以后在设置里进行开启
		mailInfo.setPassword("jiashuai19950412");// 自己邮箱密码

		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
	
		
		sendResult=new JSONObject();
		boolean isSend = SimpleMailSender.sendTextMail(mailInfo);

		if (isSend) {
			sendResult.put("ok", "email has been sent");
		}else{
			sendResult.put("ok", "sending email occurs a error");
		}
		
		return SUCCESS;
	}

}
