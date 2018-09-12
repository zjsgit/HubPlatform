/**
 * 
 */
package cn.edu.bean;

import java.util.Properties;

/**
 * @author JiashuaiZhang
 *
 */
public class MailSenderInfo {

	// �����ʼ�������IP
	private String mailServerHost;
	// �����ʼ��������˿�
	private String mailServerPort = "25";
	// �ʼ����͵�ַ
	private String fromAddress;
	// �ʼ������ߵ�ַ
	private String toAddress;
	// �����ʼ��������ĵ�¼�û���
	private String userName;
	// �����ʼ��������ĵ�¼����
	private String password;
	// �Ƿ���Ҫ�����֤
	private boolean validate = false;
	// �ʼ�����
	private String subject;
	// �ʼ����ı�����
	private String content;
	// �ʼ��������ļ���
	private String[] attachFileNames;

	// �����˵��ʼ���Ϣ,�ڴ���Session��ʹ��
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

}
