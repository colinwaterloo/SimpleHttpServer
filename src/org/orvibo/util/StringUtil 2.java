package org.orvibo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StringUtil {
	private static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
}