package com.netcore.falconide;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonElement;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.netcore.falconide.FalconideException;
import com.netcore.falconide.GsonUtils;
import com.netcore.falconide.InvalidApiKeyException;
import com.netcore.falconide.ValidationException;

/**
 * @author bhaswanthg
 *
 */
public class Falconide {
	private static final String VERSION = "0.0.1";
	public static final MediaType EMAIL_MEDIA_TYPE = MediaType
			.parse("application/x-www-form-urlencoded");
	private String url;
	private String endpoint;
	private OkHttpClient client;
	private String apiKey;
	private boolean isConsoleLoggingEnabled;

	/**
	 * Constructor for using an API key
	 *
	 * @param apiKey
	 *            Falconide api key
	 */
	public Falconide(String apiKey) {
		this.apiKey = apiKey;
		this.url = "http://api.falconide.com";
		this.endpoint = "/falconapi/web.send.json";
		client = new OkHttpClient();
		client.setConnectTimeout(1000, TimeUnit.SECONDS);
		System.setProperty("jsse.enableSNIExtension", "false");

	}

	public Falconide setUrl(String url) {
		this.url = url;
		return this;
	}

	public Falconide setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public String getVersion() {
		return VERSION;
	}

	public Falconide.Response send(Email email) throws FalconideException,
			ValidationException {
		if (email == null) {
			throw new ValidationException();
		}
		if (apiKey == null) {
			throw new InvalidApiKeyException();
		}
		email.setApiKey(apiKey);
		if (isConsoleLoggingEnabled) {
			System.out.println("Finalising response to send::" + (new Date())
					+ ":");
		}
		email.finalize();

		if (isConsoleLoggingEnabled) {
			System.out.println("Validating response to send::" + (new Date())
					+ ":");
		}
		email.validate();

		JsonElement jsonObject = GsonUtils.getInstance().toJsonTree(email);
		if (email.getXAPIHeaders() != null && email.getXAPIHeaders().size() > 0) {
			jsonObject
					.getAsJsonObject()
					.get("email_details")
					.getAsJsonObject()
					.add("X-APIHEADER",
							GsonUtils.getInstance().toJsonTree(
									email.getXAPIHeaders()));
		}

		if (email.getRecipientsCCXapi() != null
				&& email.getRecipientsCCXapi().size() > 0) {
			jsonObject.getAsJsonObject().add(
					"X-APIHEADER_CC",
					GsonUtils.getInstance().toJsonTree(
							email.getRecipientsCCXapi()));
		}

		if (email.isAnyAttachmentAvailable()) {

			jsonObject.getAsJsonObject().add("encoding_type",
					GsonUtils.getInstance().toJsonTree("base64"));
		}
		String jsonEmail = jsonObject.toString();
		if (isConsoleLoggingEnabled) {
			System.out.println("Calling with following content::"
					+ (new Date()) + ":");

			System.out.println(jsonEmail);

		}

		// System.out.println(jsonEmail);
		RequestBody body = RequestBody.create(EMAIL_MEDIA_TYPE, "data="
				+ jsonEmail);

		Request request = new Request.Builder().url(this.url + this.endpoint)
				.post(body).build();
		com.squareup.okhttp.Response response;
		try {
			response = client.newCall(request).execute();
			String responseBody = response.body().string();
			if (isConsoleLoggingEnabled) {
				System.out.println("Received Response with body::"
						+ responseBody);
			}
			Response resp = new Response(response.code(), responseBody);
			return resp;
		} catch (IOException e) {
			e.printStackTrace();
			throw new FalconideException(e);
		}

	}

	public static class Response {
		private int code;
		private boolean success;
		private String message;

		public Response(int code, String msg) {
			this.code = code;
			this.success = code == 200;
			this.message = msg;
		}

		public int getCode() {
			return this.code;
		}

		public boolean getStatus() {
			return this.success;
		}

		public String getMessage() {
			return this.message;
		}
	}

	public void enableConsoleLogging() {
		isConsoleLoggingEnabled = true;
	}

	public void disableConsoleLogging() {
		isConsoleLoggingEnabled = false;

	}
	public void setConnectTimeout(long time, TimeUnit unit){
		client.setConnectTimeout(time, unit);
	}	
	public void setReadTimeout(long time, TimeUnit unit){
		client.setReadTimeout(time, unit);
	}
         public void setWriteTimeout(long time, TimeUnit unit){
            client.setWriteTimeout(time, unit);
        }
}
