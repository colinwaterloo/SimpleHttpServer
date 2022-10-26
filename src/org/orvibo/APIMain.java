package org.orvibo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.orvibo.util.Tools;

public class APIMain {
	private static String appId = "yuanjing";		//�滻���Լ���appId
	private static String appKey = "2e16b328c5fd4867ad43511e6f5a024c";	//�滻���Լ���appKey
	private static String userName = "colinwaterloo@hotmail.com";
	private static String password = "5e0ec0d6c01f885c84b949c1df72ce5a".toUpperCase();
	private static String host = "https://open.orvibo.com";
	private static String uid = "accf2396f526";
	private static String deviceId = "cec9d20df88040a88217ed7830472e91";
	
	public static void main(String[] args) {
		try {
			
			control(uid, deviceId,Status.on);
			System.in.read();
			control(uid, deviceId, Status.off);
			String result = getStatus(uid, deviceId);
			System.out.println("result:" + result);
			// System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getDeviceList() throws Exception {
		String uri = "/api/getDeviceListNoScene";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", appId);
		paramsMap.put("time", String.valueOf(System.currentTimeMillis()));						//�����
		String sn = UUID.randomUUID().toString().replaceAll("-", "");
		paramsMap.put("sn", sn);																//�����
		
		// paramsMap.put("phone", "13912345001");													//�ֻ�����(�ֻ�������������һ��)	[ѡ��]
		// paramsMap.put("email", "13912345001@139.com");											//����					[ѡ��]
		paramsMap.put("password", password);			//md5('12345678')		�����
		paramsMap.put("userName", userName);														//�ǳ�					[ѡ��]		
		

		String sign = Tools.calSign(uri, "post", paramsMap, appKey);
		paramsMap.put("sig", sign);																//�����
		
		String request = Tools.paramsToString(paramsMap);
		System.out.println(request);
		return Tools.doPost(host + uri, request);
	}
	
	
	public static String getStatus(String uid, String deviceId) throws Exception {
		String uri = "/api/deviceStatus";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", appId);
		paramsMap.put("time", String.valueOf(System.currentTimeMillis()));						//�����
		String sn = UUID.randomUUID().toString().replaceAll("-", "");
		paramsMap.put("sn", sn);																//�����
		
		// paramsMap.put("phone", "13912345001");													//�ֻ�����(�ֻ�������������һ��)	[ѡ��]
		// paramsMap.put("email", "13912345001@139.com");											//����					[ѡ��]
		paramsMap.put("password", password);			//md5('12345678')		�����
		paramsMap.put("userName", userName);														//�ǳ�					[ѡ��]		
		paramsMap.put("uid", uid);	
		paramsMap.put("deviceId", deviceId);
		paramsMap.put("order", "");
		paramsMap.put("value1", "0");
		paramsMap.put("value2", "0");
		paramsMap.put("value3", "0");
		paramsMap.put("value4", "0");
		paramsMap.put("delayTime", "0");
		paramsMap.put("type", "1");
		paramsMap.put("id", "");

		String sign = Tools.calSign(uri, "post", paramsMap, appKey);
		paramsMap.put("sig", sign);																//�����
		
		String request = Tools.paramsToString(paramsMap);
		System.out.println(request);
		return Tools.doPost(host + uri, request);
	}
	
	/**
	public static String getIRS() {
		String uri = "/getIRS";
		String timestamp = "" + (System.currentTimeMillis() / 1000);
		String nonce = "1234567";
		
	}
	**/
	
	public static String control(String uid, String deviceId, Status status) throws Exception {
		String uri = "/api/control";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", appId);
		paramsMap.put("userName", userName);
		paramsMap.put("password", password);	
		paramsMap.put("uid", uid);
		paramsMap.put("deviceId", deviceId);	
		switch (status){
		case on:
			paramsMap.put("order", "on");
			break;
		case off:
			paramsMap.put("order", "off");
			break;
		}
		paramsMap.put("value1", "0");
		paramsMap.put("value2", "0");
		paramsMap.put("value3", "0");
		paramsMap.put("value4", "0");
		paramsMap.put("delayTime", "1000");
		// paramsMap.put("type", "2");
		// paramsMap.put("freq", "38150");
		// String pluseData="3963,3985,491,1990,491,1992,491,1992,491,1992,491,1005,491,1005,491,1992,491,1005,491,1992,491,1005,491,1992,491,1005,491,1005,491,1005,491,1005,491,1005,491,1992,491,1992,492,1004,491,1992,491,1005,491,1992,491,1005,491,1992,491,8333,3963,3985,492,1990,491,1992,491,1992,491,1993,491,1005,491,1005,491,1992,491,1005,491,1992,491,1005,491,1992,491,1006,491,1005,491,1005,491,1005,491,1005,491,1992,491,1993,491,1005,491,1991,491,1005,491,1991,491,1004,491,1991,491,8330,3962,3985,491,1991,491,1992,491,1992,491,1992,491,1005,491,1005,491,1992,491,1005,491,1992,491,1005,491,1992,491,1006,490,1005,491,1005,491,1005,491,1005,491,1992,491,1992,491,1005,491,1992,491,1005,491,1992,491,1005,491,1992,491,8282";
		// paramsMap.put("pluseNum",pluseData.split(",").length+"");
		// paramsMap.put("pluseData", pluseData);
//		paramsMap.put("qualityOfService", "0");
//		paramsMap.put("defaultResponse", "0");
	
		paramsMap.put("time", String.valueOf(System.currentTimeMillis()));
		String sn = UUID.randomUUID().toString().replaceAll("-", "");
		paramsMap.put("sn", sn);																
		String sign = Tools.calSign(uri, "post", paramsMap, appKey);		
		paramsMap.put("sig", sign);
		
		String request = Tools.paramsToString(paramsMap);
		System.out.println(request);
		String result = Tools.doPost(host + uri, request);
		System.out.printf(result);
	
	    return result;
	}
	
	/**
	 * ע��
	 */
	public static String testRegist() throws Exception {
		String uri = "/api/regist";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", appId);
		paramsMap.put("time", String.valueOf(System.currentTimeMillis()));						//�����
		String sn = UUID.randomUUID().toString().replaceAll("-", "");
		paramsMap.put("sn", sn);																//�����
		
		paramsMap.put("phone", "13912345001");													//�ֻ�����(�ֻ�������������һ��)	[ѡ��]
		paramsMap.put("email", "13912345001@139.com");											//����					[ѡ��]
		paramsMap.put("password", "25d55ad283aa400af464c76d713c07ad".toUpperCase());			//md5('12345678')		�����
		paramsMap.put("name", "nickname1");														//�ǳ�					[ѡ��]		
		
		String sign = Tools.calSign(uri, "post", paramsMap, appKey);
		paramsMap.put("sig", sign);																//�����
		
		String requst = Tools.paramsToString(paramsMap);
		return Tools.doPost(host + uri, requst);
	}
	
	/**
	 * ��ѯphone�Ƿ���ע��
	 */
	public static String testCheckRegPhone() throws Exception{
		String uri = "/api/checkRegPhone";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appId", appId);										//�����			
		paramsMap.put("time", String.valueOf(System.currentTimeMillis()));	//�����
		String sn = UUID.randomUUID().toString().replaceAll("-", "");
		paramsMap.put("sn", sn);											//�����
		
		paramsMap.put("phone", "13912345001");								//�����	

		String sign = Tools.calSign(uri, "post", paramsMap, appKey);		
		paramsMap.put("sig", sign);											//�����
		
		String requst = Tools.paramsToString(paramsMap);
		return Tools.doPost(host + uri, requst);
	}
}
