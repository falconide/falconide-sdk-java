package com.netcore.falconide;

import com.netcore.falconide.AttachmentsSizeExceededException;
import com.netcore.falconide.MetaData;
import com.netcore.falconide.Settings;
import com.netcore.falconide.Tables;
import com.netcore.falconide.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okio.BufferedSource;
import okio.Okio;

/**
 * @author bhaswanthg
 *
 */
public class Email implements java.io.Serializable {
	private String api_key;
	private EmailDetails email_details;
	private Settings settings;
	private ArrayList<String> recipients;
	private HashMap<String, ArrayList<String>> attributes;
	private HashMap<String, String> files;
	private transient HashMap<String, File> filesRaw;
	private transient ArrayList<MetaData> recipientsMetaData;
	private ArrayList<String> recipients_cc;
	private transient ArrayList<String> recipients_cc_xapi;
	private Tables trigdata;
	private transient boolean isFinalised;
	private transient ArrayList<String> XAPIHeaders;

	public String getApiKey() {
		return api_key;
	}

	public void setApiKey(String api_key) {
		this.api_key = api_key;
	}

	public EmailDetails getEmailDetails() {
		if (email_details == null) {
			email_details = new EmailDetails();
		}
		return email_details;
	}

	public void setFromName(String fromName) {
		getEmailDetails().setFromname(fromName);
	}

	public void clearFromName() {
		getEmailDetails().setFromname(null);
	}

	public void setSubject(String subject) {
		getEmailDetails().setSubject(subject);
	}

	public void clearSubject() {
		getEmailDetails().setSubject(null);
	}

	public void setFrom(String fromEmailId) {
		getEmailDetails().setFrom(fromEmailId);
	}

	public void clearFrom() {
		getEmailDetails().setFrom(null);
	}

	public void setReplyToId(String replyToEmailId) {
		getEmailDetails().setReplytoid(replyToEmailId);
	}

	public void clearReplyToEmailId() {
		getEmailDetails().setReplytoid(null);
	}

	public void setEmailContent(String content) {
		getEmailDetails().setContent(content);
	}

	public void clearEmailContent() {
		getEmailDetails().setContent(null);
	}

	public ArrayList<String> getRecipients() {
		return recipients;
	}

	public void addRecipient(String toEmailId, MetaData data) {
		if (this.recipients == null) {
			recipients = new ArrayList<String>();
			recipientsMetaData = new ArrayList<MetaData>();
		}
		recipients.add(toEmailId);
		if (data != null)
			recipientsMetaData.add(data);
	}

	public void clearAllRecipents() {
		recipients = null;
	}

	void validate() throws ValidationException {
		// optional :: settings, attributes, files
		if (email_details.getContent() == null) {
			throw new ValidationException();
		}
		if (email_details == null) {
			throw new ValidationException();
		}
		if (email_details.getFrom() == null
				|| email_details.getSubject() == null
				|| email_details.getContent() == null) {
			throw new ValidationException();
		}
		if (recipients == null) {
			throw new ValidationException();
		} else {
			if (recipients.size() == 0) {
				throw new ValidationException();
			}
		}
	}

	public void addAttachment(File file) throws IOException,
			ValidationException {
		if (files == null) {
			files = new HashMap<>();
			filesRaw = new HashMap<>();
		}

		long fileSizeInBytes = file.length();
		long fileSizeInKB = fileSizeInBytes / 1024;
		long fileSizeInMB = fileSizeInKB / 1024;

		long totalSizeInMB = 0;
		for (File f : filesRaw.values()) {

			long fileSizeInBytesInner = f.length();
			long fileSizeInKBInner = fileSizeInBytesInner / 1024;
			long fileSizeInMBInner = fileSizeInKBInner / 1024;
			totalSizeInMB = totalSizeInMB + fileSizeInMBInner;
		}

		if (totalSizeInMB + fileSizeInMB > 40) {
			throw new AttachmentsSizeExceededException();
		}
		BufferedSource pngSource = Okio.buffer(Okio.source(file));

		String encodedfile = org.apache.commons.codec.binary.Base64
				.encodeBase64String(pngSource.readByteArray());
		files.put(file.getName(), encodedfile);
		filesRaw.put(file.getName(), file);

	}

	public void clearAllAttachments() {
		if (files != null) {
			files.clear();
			filesRaw.clear();
			files = null;
			filesRaw = null;
		}
	}

	public boolean isAnyAttachmentAvailable() {
		if (files != null && files.size() > 0) {
			return true;
		}
		return false;
	}

	public void setTemplateId(String id) {
		getSettings().setTemplate(id);
	}

	public Settings getSettings() {
		if (settings == null) {
			settings = new Settings();
		}
		return settings;
	}

	public void clearTemplates() {
		getSettings().setTemplate(null);
	}

	public void addFooter() {
		getSettings().setFooter("1");
	}

	public void removeFooter() {
		getSettings().setFooter("0");
	}

	public void enableClickTrack() {
		getSettings().setClicktrack("1");
	}

	public void disableClickTrack() {
		getSettings().setClicktrack("0");
	}

	public void enableOpenTrack() {
		getSettings().setOpentrack("1");

	}

	public void disableOpenTrack() {
		getSettings().setOpentrack("0");
	}

	public void setBcc(String bcc) {
		getSettings().setBcc(bcc);
	}

	public void clearBcc() {
		getSettings().setBcc(null);
	}

	public void enableUnsubscribe() {
		getSettings().setUnsubscribe("1");
	}

	public void disableUnsubscribe() {
		getSettings().setUnsubscribe("0");
	}

	public void setAttachmentsIds(String idsCommaSeparated) {
		getSettings().setAttachmentid(idsCommaSeparated);
	}

	public void clearAttachmentsIds() {
		getSettings().setAttachmentid(null);
	}

	public void addAttachmentId(String id) {
		getSettings().setAttachmentid(
				(getSettings().getAttachmentid() == null) ? ("" + id)
						: (getSettings().getAttachmentid() + "," + id));
	}

	public static void main(String[] args) {
		// Email email = new Email();
		// email.setFrom("bhaswanth88@gmail.com");
		// Table table = email.createTable("rooms");
		// Row row = new Row();
		// row.addColumnData("id", "1");
		// row.addColumnData("name", "Room 1");
		// row.addColumnData("image_1",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_02.jpg");
		// row.addColumnData("image_2",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_04.jpg");
		// row.addColumnData("image_3",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_06.jpg");
		// row.addColumnData("image_4",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_08.jpg");
		// row.addColumnData("shade_1", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_2", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_3", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_4", "- Nilaya wallpaper (SA9157)");
		// // row.addColumnData
		//
		// table.addRow(row);
		//
		// row = new Row();
		// row.addColumnData("id", "2");
		// row.addColumnData("name", "Room 1");
		// row.addColumnData("image_1",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_02.jpg");
		// row.addColumnData("image_2",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_04.jpg");
		// row.addColumnData("image_3",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_06.jpg");
		// row.addColumnData("image_4",
		// "http://www.falconide.com/img/asianpaints/thumb-pink_08.jpg");
		// row.addColumnData("shade_1", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_2", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_3", "- Nilaya wallpaper (SA9157)");
		// row.addColumnData("shade_4", "- Nilaya wallpaper (SA9157)");
		//
		// table.addRow(row);
		//
		// System.out.println(GsonUtils.toJson(email));
	}

	public boolean isFinalised() {
		return isFinalised;
	}

	public void finalize() {
		for (int i = 0; i < recipients.size(); i++) {
			MetaData data = recipientsMetaData.get(i);
			if (attributes == null) {
				attributes = new HashMap<String, ArrayList<String>>();
			}
			if (data.getSubstitutes() != null) {
				for (String key : data.getSubstitutes().keySet()) {
					if (attributes.get(key) != null) {
						attributes.get(key).add(data.getSubstitutes().get(key));
					} else {
						ArrayList<String> vals = new ArrayList<String>();
						vals.add(data.getSubstitutes().get(key));
						attributes.put(key, vals);
					}
				}
			}
			if (XAPIHeaders == null) {
				XAPIHeaders = new ArrayList<String>();
			}
			if (data.getXAPIHeader() != null) {
				XAPIHeaders.add(data.getXAPIHeader());
			} else {
				XAPIHeaders.add("");
			}

			if (recipients_cc == null) {
				recipients_cc = new ArrayList<String>();
			}
			if (data.getRecipientCcs() != null) {
				String str = "";
				for (String recip : data.getRecipientCcs()) {
					str = str + recip + ",";
				}
				str = str.substring(0, str.length() - 1);
				recipients_cc.add(str);
			}

			if (recipients_cc_xapi == null) {
				recipients_cc_xapi = new ArrayList<String>();
			}
			if (data.getRecipientCCsXapis() != null) {
				String str = "";
				for (String recip : data.getRecipientCCsXapis()) {
					str = str + recip + ",";
				}
				str = str.substring(0, str.length() - 1);
				recipients_cc_xapi.add(str);
			}
			if (trigdata == null) {
				trigdata = new Tables();
			}
			if (data.getTrigData() != null) {
				trigdata.add(data.getTrigData());
			}

		}
		this.isFinalised = true;
	}

	public void openEdit() {
		this.isFinalised = false;
	}

	public ArrayList<String> getXAPIHeaders() {
		return XAPIHeaders;
	}

	public ArrayList<String> getRecipientsCCXapi() {
		return recipients_cc_xapi;
	}
}
