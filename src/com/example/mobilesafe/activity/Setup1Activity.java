/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Administrator
 *
 */
public class Setup1Activity extends BaseSetupActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		
	}
	
	
	
	
	


	@Override
	protected void showNextPage() {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		finish();
		
		//开启平移动画
		overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
		
	}


	@Override
	protected void showPrePage() {
		//空实现
		
	}


}
