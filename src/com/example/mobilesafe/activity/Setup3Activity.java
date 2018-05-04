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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Administrator
 *
 */
public class Setup3Activity extends BaseSetupActivity {
	
	private EditText phone_number;
	private Button btn_select_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		
		initUI();
	}

	private void initUI() {
		//显示电话号码输入框
		phone_number = (EditText) findViewById(R.id.et_phone_number);
		//获取联系人的电话号码回显
		String contact_phone = SpUtils.getString(this,ConstantValue.CONTACT_PHONE,"");
		phone_number.setText(contact_phone);
		//点击选择联系人的对话框
		btn_select_number = (Button) findViewById(R.id.bt_select_number);
		btn_select_number.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ContactListActivity.class);
				startActivityForResult(intent,0);
				
			}
		});

		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
		//返回到当前界面时候，接收结果
		String phone = data.getStringExtra("phone");
		//将特殊字符过滤(中划线转换为无)
		phone = phone.replace("-","").replace(" ", "").trim();
		phone_number.setText(phone);
		//存储联系人
		SpUtils.putString(getApplicationContext(),ConstantValue.CONTACT_PHONE,phone);
		}
		super.onActivityResult(requestCode, resultCode, data);

	}


	@Override
	protected void showNextPage() {
		//点击按钮以后，需要获取输入框中的联系人，再做下一步操作
				String phone = phone_number.getText().toString();
				
				//在sp存储的相关联系人以后 才可以 跳转到下一页面操作
//				String contact_phone = SpUtils.getString(getApplicationContext(),ConstantValue.CONTACT_PHONE,"");
				if(!TextUtils.isEmpty(phone))
				{
					Intent intent = new Intent(getApplicationContext(),Setup4Activity.class);
					startActivity(intent);
					finish();
					//如果现在输入电话号码，则需要保存
					SpUtils.putString(getApplicationContext(),ConstantValue.CONTACT_PHONE,phone);

					overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
				}
				else
				{
					ToastUtil.show(this, "请输入电话号码");
				}
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		finish();

		overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
		
	}
}
