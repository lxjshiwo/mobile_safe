/**
 * 
 */
package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
					
					break;
				case 8:
					Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
					startActivity(intent);
					break;

				}
				
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
