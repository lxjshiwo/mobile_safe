/**
 * 
 */
package com.example.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class TestActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView view = new TextView(this);
		view.setText("TestActivity");
		setContentView(view);
	}

}
