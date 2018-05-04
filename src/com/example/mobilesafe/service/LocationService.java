/**
 * 
 */
package com.example.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * @author Administrator
 *
 */
public class LocationService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//获取手机的经纬度坐标
		//1.获取位置管理者对象 
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		//2.以最优的方式获取经纬度坐标
		Criteria criteria = new Criteria();
		//允许花费
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//指定读取经纬度的精确度
		String provider = lm.getBestProvider(criteria, true);
		//3.在一定时间间隔,移动一定距离后获取经纬度坐标
		MyLocationListener myLocationListener = new MyLocationListener();
		lm.requestLocationUpdates(provider, 0,0, myLocationListener);

	}
	
	
	class MyLocationListener implements LocationListener
	{
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			//gps状态发生切换的事件监听
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			//GPS开启时候的事件监听
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			//GPS关闭时候的事件监听
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			//经度
			double longitude = location.getLongitude();
			//纬度
			double latitude = location.getLatitude();
			
			//获取经纬度需要添加权限
			//发送 短信 (添加权限)
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("13172970375",null,"longtitude = "+longitude + "latitude = "+latitude,  null, null);

			
		}
		
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
