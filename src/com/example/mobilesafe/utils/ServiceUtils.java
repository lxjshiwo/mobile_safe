/**
 * 
 */
package com.example.mobilesafe.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * @author Administrator
 *
 */
public class ServiceUtils {
	
	/**
	 * @param context 上下文
	 * @param serviceName 判断是否正在运行的服务
	 * @return true 正在运行 false没有运行
	 */
	public static boolean isRunning(Context context,String serviceName){
		//1.获取activityManager管理对象，可以去获取当前收集正在运行的所有服务
		ActivityManager mAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//2.获取手机正在运行的服务集合(多少个服务)
		List<RunningServiceInfo> runningServices = mAM.getRunningServices(1000);
		//3.遍历获取所有的服务的集合，拿到每一个服务的类的名称，和传递进来的类的名称做对比，如果一致，说明服务正在运行
		for(RunningServiceInfo runningServiceInfo:runningServices)
		{
			//4.获取每一个正在运行的服务的名称
			if(serviceName.equals(runningServiceInfo.service.getClassName()))
			{
				return true;
			}

		}
		return false;
	}
}
