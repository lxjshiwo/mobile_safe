/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;

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
public class AToolsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atool);
		//归属地查询方法
		initPhoneAddress();
	}

	private void initPhoneAddress() {
		TextView tv_phone_query_address = (TextView) findViewById(R.id.tv_query_phone_address);
		tv_phone_query_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getApplicationContext(),QueryAddressActivity.class));
				
			}
		});
		
	}
	
	
	

}
