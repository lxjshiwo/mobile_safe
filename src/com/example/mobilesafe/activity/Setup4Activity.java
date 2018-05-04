/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Administrator
 *
 */
public class Setup4Activity extends BaseSetupActivity {
	private CheckBox cb_box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		initUI();

	}
	
	
	private void initUI() 
	{
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		//1.是否选中状态的回显
		boolean open_security = SpUtils.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
		//2.根据状态修改checkbox后续的文字显示
		cb_box.setChecked(open_security);
		if(open_security)
		{
			cb_box.setText("安全设置已开启");
		}
		else
		{
			cb_box.setText("安全设置已关闭");
		}
//		cb_box.setChecked(!cb_box.isChecked());
		//3.点击过程中，checkbox状态切换,以及我们却换后状态的存储
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//isChecked点击后的状态
				//4.存储点击切换后的状态存储
				SpUtils.putBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,isChecked);
				//5.根据开启还是关闭的状态，去修改显示的文字
				if(isChecked)
				{
					cb_box.setText("安全设置已开启");
				}
				else
				{
					cb_box.setText("安全设置已关闭");
				}

				
			}
		});


	}



	@Override
	protected void showNextPage() {
		boolean open_security = SpUtils.getBoolean(this,ConstantValue.OPEN_SECURITY, false);
		if(open_security)
		{
			Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
			startActivity(intent);
			finish();
			SpUtils.putBoolean(this,ConstantValue.SETUP_OVER,true);
		}
		else
		{
			ToastUtil.show(this,"请开启防盗保护");
		}
		
	}


	@Override
	protected void showPrePage() 
	{
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		finish();
		
	}

}
