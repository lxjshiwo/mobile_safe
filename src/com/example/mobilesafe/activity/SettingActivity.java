/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.service.AddressService;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.ServiceUtils;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Administrator
 *
 */
public class SettingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		
		initUpdate();
		initAddress();
	}

	/**
	 *是否显示电话号码归属地的方法
	 */
	private void initAddress() {
		final SettingItemView siv_address = (SettingItemView) findViewById(R.id.siv_address);
		//对服务是否开启的状态显示
		boolean isRunning = ServiceUtils.isRunning(this,"com.example.mobilesafe.service.AddressService");
		siv_address.setCheck(isRunning);
		//点击过程中，状态(是否开启电话号码归属地)的切换
		siv_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isCheck = siv_address.isCheck();
				siv_address.setCheck(!isCheck);
				if(!isCheck)
				{
					//开启服务管理toast
					startService(new Intent(getApplication(),AddressService.class));
				}
				else
				{
					//关闭toast
					stopService(new Intent(getApplication(),AddressService.class));
				}
				
			}
		});
		
	}

	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
		//获取已有的开关状态，用作显示
		boolean open_update = SpUtils.getBoolean(this,ConstantValue.OPEN_UPDATE,false);
		//是否选中根据上一次的结果去做决定
		siv_update.setCheck(open_update);
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//如果之前选中点击过后点击后未选中
				//如果之前未选中点击过后点击后选中
				
				//获取之前状态
				boolean check = siv_update.isCheck();
				//将原有状态取反
				siv_update.setCheck(!check);
				//将取反后的状态存储到相应sp中
				SpUtils.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!check);
			}
		});
		
	}

	
	
	

}
