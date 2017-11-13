package com.netcore.falconide;


/**
 * @author bhaswanthg
 *
 */
public class Settings implements java.io.Serializable {
	private String footer;
	private String clicktrack;
	private String opentrack;
	private String unsubscribe;
	private String bcc;
	private String attachmentid;
	private String template;

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getClicktrack() {
		return clicktrack;
	}

	public void setClicktrack(String clicktrack) {
		this.clicktrack = clicktrack;
	}

	public String getOpentrack() {
		return opentrack;
	}

	public void setOpentrack(String opentrack) {
		this.opentrack = opentrack;
	}

	public String getUnsubscribe() {
		return unsubscribe;
	}

	public void setUnsubscribe(String unsubscribe) {
		this.unsubscribe = unsubscribe;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getAttachmentid() {
		return attachmentid;
	}

	public void setAttachmentid(String attachmentid) {
		this.attachmentid = attachmentid;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
