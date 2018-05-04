/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class SetupOverActivity extends Activity {
	
	
	private TextView tv_phone;
	private TextView tv_reset_setup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		boolean setup_over = SpUtils.getBoolean(this,ConstantValue.SETUP_OVER,false);
		if(setup_over)
		{
			//密码输入成功,并且4个导航界面设置完成--->设置完成功能列表
			setContentView(R.layout.activity_setup_over);
			initUI();
		}
		else
		{
			//密码输入成功,并且4个导航界面没有设置完成--->导航界面第一个
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
			//开启新界面后关闭功能列表
			finish();
		}


	}

	private void initUI() {
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		//设置联系人号码
		String phone = SpUtils.getString(this,ConstantValue.CONTACT_PHONE,"");
		tv_phone.setText(phone);
		
		
		//重新设置条目被点击的事件
		tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
		tv_reset_setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
				startActivity(intent);
				finish();
			}
		});


		
	}
	

}
