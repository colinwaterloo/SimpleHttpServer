package org.orvibo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 */
	public static final String MD5(byte []b) throws NoSuchAlgorithmException{
		return MD5(b, 0, b.length);
	}
	
	/**
	 * MD5加密
	 * @param b 需要加密的数据
	 * @param offset 数据的起始位置
	 * @param len 数据的长度
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 */
	public static final String MD5(byte []b, int offset, int len) throws NoSuchAlgorithmException{
		char hexDigits[] = { '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(b, offset, len);
		byte[] md = mdInst.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
		   byte byte0 = md[i];
		   str[k++] = hexDigits[byte0 >>> 4 & 0xf];
		   str[k++] = hexDigits[byte0 & 0xf];
		}
		
		return new String(str);
	}
	
	/** 
	 * MD5加密
	 * @param s 需要加密的数据
	 * @return 加密后的数据
	 * @throws NoSuchAlgorithmException 
	 */
	public static final String MD5(String s) throws NoSuchAlgorithmException {
		return MD5(s.getBytes());
	}
	
	public static final String MD5(String s, String charset) throws IOException, NoSuchAlgorithmException {
		return MD5(s.getBytes(charset));
	}
	
	public static byte[] getHmacSHA1(String src, String key)  
	        throws NoSuchAlgorithmException, UnsupportedEncodingException,  
	        InvalidKeyException {  
	    Mac mac = Mac.getInstance("HmacSHA1");  
	    SecretKeySpec secret = new SecretKeySpec(  
	    		key.getBytes("UTF-8"), mac.getAlgorithm());  
	    mac.init(secret);
	    return mac.doFinal(src.getBytes());  
	}
	
	public static byte[] getHmacSHA256(String src, String key)  
	        throws NoSuchAlgorithmException, UnsupportedEncodingException,  
	        InvalidKeyException {  
	    Mac mac = Mac.getInstance("HmacSHA256");  
	    SecretKeySpec secret = new SecretKeySpec(  
	    		key.getBytes("UTF-8"), mac.getAlgorithm());  
	    mac.init(secret);
	    return mac.doFinal(src.getBytes());  
	}
	
	public static void main(String[] args) throws Exception {
		String src = "GET&%2Fv3%2Fuser%2Fget_info&appid%3D123456%26format%3Djson%26openid%3D11111111111111111%26openkey%3D2222222222222222%26pf%3Dqzone%26userip%3D112.90.139.30";
		String key = "228bf094169a40a3bd188ba37ebe8723&";
		byte[] bs = getHmacSHA1(src, key);
		
		String base64 = Base64.encode(bs, false);
		System.out.println(base64);
	}
}