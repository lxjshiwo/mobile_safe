package com.example.mobilesafe.utils;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

public class Md5Util {


	/**
	 * 给指定字符串md5加密算法类型
	 *psd 需要加密的密码 加盐处理
	 */
	public static String encoder(String psd) {
		try {
			//加盐处理
			psd = psd + "mobliesafe";
			//1.指定加密算法类型
			MessageDigest digest = MessageDigest.getInstance("MD5");
			//2.将需要加密的字符串转换成需要加密的数组，然后进行随机hash过程
			byte[] bs = digest.digest(psd.getBytes());
			System.out.println(bs.length);
			//3.循环遍历bs,然后让其生成32位的字符串，固定写法
			StringBuffer stringBuffer = new StringBuffer();
			for(byte b : bs)
			{
				int i = b & 0xff;
				//int类型的i需要转换成16进制的字符
				String hexString = Integer.toHexString(i);
//				System.out.println(hexString);
				//补上0拼接为32位结果
				if(hexString.length() < 2)
				{
					hexString = "0" + hexString;
				}
				stringBuffer.append(hexString);

			}
			return stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}

}
