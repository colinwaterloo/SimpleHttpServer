package org.orvibo.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.orvibo.https.MySecureProtocolSocketFactory;

public class Tools {
	public static String calSign(String uri, String method, Map<String, String> params, String appKey) throws Exception{
		String uri_encode = URLEncoder.encode(uri, Constants.CHAR_ENCODING);
		List<String> keys = new ArrayList<String>();
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(!"sig".equals(key)){
				keys.add(key);
			}
		}
		
		Collections.sort(keys);
		StringBuilder paramString = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String keysi = keys.get(i);
			paramString.append(keysi).append("=").append(params.get(keysi)).append("&");
		}
		
		if(paramString.length() > 0){
			paramString.deleteCharAt(paramString.length() - 1);
		}
		
		String paramString_encode = URLEncoder.encode(paramString.toString(), Constants.CHAR_ENCODING);
		
		StringBuilder source = new StringBuilder();
		source.append(method.toUpperCase()).append("&");
		source.append(uri_encode).append("&");
		source.append(paramString_encode);
		
		String signKey = appKey + "&";
		byte[] enc = EncryptUtil.getHmacSHA1(source.toString(), signKey);
		String sign = Base64.encode(enc, false);
		return sign;
	}
	
	// ��ȡUUID
	public static String getUUID() {
		String key = UUID.randomUUID().toString();
		String[] keys = key.split("-");
		StringBuffer sb = new StringBuffer();
		for (String k : keys) {
			sb.append(k);
		}
		
		return sb.toString();
	}
	
	public static String paramsToString(Map<String, String> paramsMap) {
		StringBuilder url = new StringBuilder();
		Iterator<Entry<String, String>> it = paramsMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		if(url.length() > 0){
			url.deleteCharAt(url.length() - 1);
		}
		
		return url.toString();
	}
	
	public static String doPost(String uri, String requst) throws IOException {
		String restult =null;
		ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
		Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
		HttpClient client = new HttpClient();
		// ʹ��POST����
		PostMethod method = new PostMethod(uri);
		try {
			RequestEntity entity = new StringRequestEntity(requst, "application/json", "UTF-8");
			method.setRequestEntity(entity);

			client.executeMethod(method);
			System.out.println(method.getURI());

			InputStream inputStream = method.getResponseBodyAsStream();
			restult = IOUtils.toString(inputStream);
			return restult;
		}finally {
			// �ͷ�����
			method.releaseConnection();
		}
	}
}