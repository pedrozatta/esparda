package br.com.esparda.commons;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonUtil {
	public static String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	// public static Object fromJson(String json, String className) {
	// try {
	// Class<?> clazz = Class.forName(className);
	// return fromJson(json, clazz);
	// } catch (ClassNotFoundException e) {
	// return null;
	// }
	// }
	//
	// public static <T> T fromJson(String json, Class<T> clazz) {
	// Gson gson = new Gson();
	// if (json.startsWith("[")) {
	// return gson.fromJson(json, Array.newInstance(clazz, 0).getClass());
	// }
	// T retorno = gson.fromJson(json, clazz);
	// return retorno;
	// }

	public static <T> List<T> listFromJson(String json, Class<T> clazz) {
		Gson gson = new Gson();
		Object retorno = null;
		if (json.startsWith("[")) {
			retorno = gson.fromJson(json, Array.newInstance(clazz, 2).getClass());
		} else {
			retorno = gson.fromJson(json, clazz);
		}
		if (!(retorno instanceof List<?>)) {
			retorno = (List<T>) Arrays.asList(retorno);
		}
		return (List<T>) retorno;
	}

	public static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
		return gson;
	}

}
