/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.Md5Util;
import com.example.mobilesafe.utils.SpUtils;
import com.example.mobilesafe.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class HomeActivty extends Activity {
	private String[] mTitleStrs;
	private int[] mDrawableIds;
	private GridView gv_home;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
		//初始化数据的方法
		initData();
	}

	private void initData() {
		mTitleStrs = new String[]{
									"手机防盗","通信卫士","软件管理",
									"进程管理","流量统计","手机杀毒",
									"缓存清理","高级工具","设置中心"};
		mDrawableIds = new int[]{
				R.drawable.home_safe,
				R.drawable.home_callmsgsafe,
				R.drawable.home_apps,
				R.drawable.home_taskmanager,
				R.drawable.home_netmanager,
				R.drawable.home_trojan,
				R.drawable.home_sysoptimize,
				R.drawable.home_tools,
				R.drawable.home_settings
		};
		//九宫格设置数据适配器
		gv_home.setAdapter(new MyAdapter());
		
		//注册九宫格的单个条目的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			//position点中列表条目的索引
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					
					//开启对话框
					showDialog();
					break;
				case 7:
					//跳转到高级工具activity
					startActivity(new Intent(getApplicationContext(),AToolsActivity.class));
					break;
				case 8:
					Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
					startActivity(intent);
					break;

				}
				
			}

		});
	}
	private void showDialog() 
	{
		//判断本地是否有存储密码(sp 字符串)
		String psd = SpUtils.getString(this,ConstantValue.MOBILE_SAFE_PSD,"");
		if(TextUtils.isEmpty(psd))
		{
			//1.初始设置密码对话框
			showSetPsdDialog();
			
		}
		else
		{
			//2.确认密码对话框
			showConfirmPsdDialog();
			
		}
				
	}

	/**
	 *确认密码的对话框
	 */
	private void showConfirmPsdDialog() {
		//需自定义展示框样式,使用dialog.setView()
				//view由自身编写xml转换成为view对象
				Builder builder = new AlertDialog.Builder(this);
				final AlertDialog dialog = builder.create();
				final View view = View.inflate(this,R.layout.dialog_confirm_psd, null);
				//让对话框显示一个自定义对话框界面 
				dialog.setView(view);
				dialog.show();
				
				
				Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
				Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
				
				
				bt_submit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

						String confirmPsd = et_confirm_psd.getText().toString();
						
						
						
						
						if(!TextUtils.isEmpty(confirmPsd))
						{
							//夹存储的32位密码取出，然后将输入密码同样进行md5操作，与sp中密码比对
							String psd = SpUtils.getString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD,"");
							//进入应用程序的手机防盗模块
							if(psd.equals(Md5Util.encoder(confirmPsd))) 
							{
								//进入手机防盗模块,开启新的activity
//								Intent intent = new Intent(getApplicationContext(),TestActivity.class);
								Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
								startActivity(intent);
								//跳转界面后隐藏对话框
								dialog.dismiss();
								
								
							}
							else
							{
								ToastUtil.show(getApplicationContext(),"确认密码错误");
							}
							

						}
						else
						{
							//提示用户密码收入有为空现象
							ToastUtil.show(getApplicationContext(),"请输入密码");
						}
					}
				});
				
				bt_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
					}
				});
				
		
	}

	/**
	 *设置密码对话框
	 */
	private void showSetPsdDialog() 
	{
		//需自定义展示框样式,使用dialog.setView()
		//view由自身编写xml转换成为view对象
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(this,R.layout.dialog_set_psd, null);
		//让对话框显示一个自定义对话框界面 
//		dialog.setView(view);
		//为了兼容2.3版本，给对话框布局的时候，让其没有内边距
		dialog.setView(view,0, 0, 0, 0);
		dialog.show();
		
		
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		
		
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
				EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

				String psd = et_set_psd.getText().toString();
				String confirmPsd = et_confirm_psd.getText().toString();
				
				
				if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd))
				{
					//进入应用程序的手机防盗模块
					if(psd.equals(confirmPsd))
					{
						//进入手机防盗模块,开启新的activity
						Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
						startActivity(intent);
						//跳转界面后隐藏对话框
						dialog.dismiss();
						
						
						SpUtils.putString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD,Md5Util.encoder(confirmPsd));
					}
					else
					{
						ToastUtil.show(getApplicationContext(),"确认密码错误");
					}
					

				}
				else
				{
					//提示用户密码收入有为空现象
					ToastUtil.show(getApplicationContext(),"请输入密码");
				}
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});
		
		
	}

	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
		
	}
	
	
	class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			//条目总数 文字组数==图片张数 
			return mTitleStrs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mTitleStrs[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		//HOlder
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),R.layout.gridview_item,null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			
			tv_title.setText(mTitleStrs[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);

			return view;
		}
		
	}


	
}
