package org.orvibo.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
	public static void close(Closeable c){
		if(c != null){
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String readAsString(InputStream is, String charset) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len;
		byte[] buff = new byte[512];
		while((len = is.read(buff)) != -1){
			bos.write(buff, 0, len);
		}
		
		return new String(bos.toByteArray(), charset);
	}
}
