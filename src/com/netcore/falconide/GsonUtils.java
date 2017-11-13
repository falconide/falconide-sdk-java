package com.netcore.falconide;

import java.io.Serializable;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * @author bhaswanthg
 *
 */
public class GsonUtils {
	static Gson gson = new Gson();
	static JsonParser parse = new JsonParser();

	public static String toJson(Serializable object) {
		return gson.toJson(object);
	}

	public static Gson getInstance() {
		
		return gson;
	}

	public static String getDataFrom(String inputJson, String param) {
		// parse.parse(arg0);
		// BufferedReader read= new
		try {

			JsonElement element = parse.parse(inputJson);
			// JsonReader.setLenient(true);
			JsonElement value = element.getAsJsonObject().get(param);
			return value.getAsString();
		} catch (Exception e) {
			JsonReader reader = new JsonReader(new StringReader(inputJson));
			reader.setLenient(true);
			JsonParser newParser = new JsonParser();
			JsonElement element = newParser.parse(reader);

			JsonElement value = element.getAsJsonObject().get(param);
			return value.getAsString();
		}

	}
	

	public static JsonParser getParser() {
		return parse;
	}
}
