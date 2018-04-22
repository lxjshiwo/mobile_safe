/**
 * 
 */
package com.example.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 *
 */
public class StreamUtil {

	/*
	 * @param is 流对象
	 * @return 流对象的字符串， null为错误
	 */
	public static String streamToString(InputStream is) {
		//1.在读取过程中,将读取的内容存储在缓存,一次性转换成字符串返回
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//2.读流的过程
		byte[] buffer = new byte[1024];
		//3.记录读取内容的临时变量
		int temp = -1;
		try {
			while((temp = is.read(buffer)) != -1)
			{
				bos.write(buffer,0,temp);
			}
			//返回读取数据
			return bos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
