/**
 * 
 */
package com.example.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Administrator
 *
 */
public class SpUtils {
	
	private static SharedPreferences sp;

	//写
	/**
	 *context上下文环境
	 *
	 */
	public static void putBoolean(Context context,String key,boolean value)
	{
		//name 存储结点文件的名称 ，mode 读写方式
		if(sp == null)
		{
			sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
		
		
		
	}
	//读
		/**
		 *defValue 没有结点时为默认返回值结果 
		 */
		public static boolean getBoolean(Context context,String key,boolean defValue)
		{
			if(sp == null)
			{
				sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
			}
			
			return sp.getBoolean(key, defValue);

		}


	
	
	
		//字符串写
		/**
		 *context上下文环境
		 *
		 */
		public static void putString(Context context,String key,String value)
		{
			//name 存储结点文件的名称 ，mode 读写方式
			if(sp == null)
			{
				sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
			}
			sp.edit().putString(key, value).commit();
			
			
			
		}
		
		//字符串读
		/**
		 *defValue 没有结点时为默认返回值结果 
		 */
		public static String getString(Context context,String key,String defValue)
		{
			if(sp == null)
			{
				sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
			}
			
			return sp.getString(key, defValue);

		}
		
		
		
		/**
		 * 从sp中移除指定结点
		 *@param ctx 上下文环境
		 *@param key 需要移除节点的名称
		 *
		 */
		public static void remove(Context ctx, String key) {
			
			if(sp == null)
			{
				sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
			}
			sp.edit().remove(key).commit();
		}
		


}
