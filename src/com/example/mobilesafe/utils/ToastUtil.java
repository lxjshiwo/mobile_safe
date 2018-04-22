/**
 * 
 */
package com.example.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class ToastUtil {
	//打印toast
	/**
	 * @param context 上下文环境
	 * @param msg toast的信息
	 */
	public static void show(Context context,String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

}
