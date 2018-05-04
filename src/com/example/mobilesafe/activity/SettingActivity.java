/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
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
