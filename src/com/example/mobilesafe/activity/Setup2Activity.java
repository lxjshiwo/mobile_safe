/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.utils.ToastUtil;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Administrator
 *
 */
public class Setup2Activity extends BaseSetupActivity {
	private SettingItemView siv_sim_bound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		
		
		initUI();
	}
	
	
	private void initUI() {
		siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
		//回显(读取已有的绑定状态,用作显示,sp中是否存储sim卡序列号)
		String sim_number = SpUtils.getString(this,ConstantValue.SIM_NUMBER,"");
		//判断是否序列卡号为空
		if(TextUtils.isEmpty(sim_number))
		{
			siv_sim_bound.setCheck(false);
		}
		else
		{
			siv_sim_bound.setCheck(true);
		}
		
		siv_sim_bound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//获取原有的状态
				boolean isCheck = siv_sim_bound.isCheck();
				//将原有的状态取反，存储，设置给当前条目,存储(序列卡号)
				siv_sim_bound.setCheck(!isCheck);
				if(!isCheck)
				{
					//存储(序列卡号)
						//获取sim卡序列号TelephoneManager
						TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						//获取sim卡的序列卡号
						String simSerialNumber = manager.getSimSerialNumber();
						//存储
						SpUtils.putString(getApplicationContext(),ConstantValue.SIM_NUMBER,simSerialNumber);
				}
				else
				{
					//将存储序列卡号的结点，从sp中删除掉
					SpUtils.remove(getApplicationContext(),ConstantValue.SIM_NUMBER);
					
				}

				
			}
		});

		
	}


	@Override
	protected void showNextPage() {
		String serialNumber = SpUtils.getString(this,ConstantValue.SIM_NUMBER,"");
		if(!TextUtils.isEmpty(serialNumber))
		{
			Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
			startActivity(intent);
			finish();
		
			//开启平移动画
			overridePendingTransition(R.anim.next_in_anim,R.anim.pre_out_anim);
		}
		else
		{
			ToastUtil.show(this,"请绑定sim卡");
		}
		
	}


	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
		startActivity(intent);
		finish();

		//开启平移动画
		overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);

		
	}



}
