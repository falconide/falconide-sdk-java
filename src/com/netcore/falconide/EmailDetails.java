package com.netcore.falconide;


public class EmailDetails implements java.io.Serializable{
	private String fromname;
	private String subject;
	private String from;
	private String replytoid;
	private String content;
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getReplytoid() {
		return replytoid;
	}
	public void setReplytoid(String replytoid) {
		this.replytoid = replytoid;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
//	private ArrayList<String> X-APIHEADER;
	

}
