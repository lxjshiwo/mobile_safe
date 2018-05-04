/**
 * 
 */
package com.example.mobilesafe.activity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class ContactListActivity extends Activity {
	
	protected static final String TAG = ContactListActivity.class.getSimpleName();
	private ListView lv_contact;
	private MyAdapter adapter;
	private List<HashMap<String,String>> contactList = new ArrayList<HashMap<String,String>>();
	private Handler mHandler = new Handler(){
		

		public void handleMessage(android.os.Message msg) {
			adapter = new MyAdapter();
			lv_contact.setAdapter(adapter);

			
		};
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		
		initUI();
		initData();

	}

	/**
	 *获取系统联系人
	 */
	private void initData() {
		//读取系统联系可能为耗时操作
		new Thread(){
			public void run() {
				//1.获取内容解析器对象
				ContentResolver contentResolver = getContentResolver();
				//2.做查询过程联系人数据库表过程(读取联系人权限)
				Cursor cursor = contentResolver.query(
						Uri.parse("content://com.android.contacts/raw_contacts"),
						new String[]{"contact_id"}, 
						null,
						null, 
						null);
				
				contactList.clear();
				//3.循环游标直到没有数据为止
				while(cursor.moveToNext())
				{
					String id = cursor.getString(0);
//					Log.i(TAG,"id"+id);
					//4.根据用户唯一性id查询data表和mimetype表生成视图，获取data以及mimetype字段
					Cursor indexCursor = contentResolver.query(
							Uri.parse("content://com.android.contacts/data"),
							new String[]{"data1","mimetype"}, 
							"raw_contact_id = ?", 
							new String[]{id}, 
							null);
					//5.循环获取每一个联系人电话号码和姓名、数据类型
					HashMap<String, String> hashMap = new HashMap<String,String>();
					while(indexCursor.moveToNext())
					{
						String data = indexCursor.getString(0);
						String type = indexCursor.getString(1);

						//6.区分类型填充hashMap数据
						if(type.equals("vnd.android.cursor.item/phone_v2"))
						{
							//数据非空
							if(!TextUtils.isEmpty(data))
							{
								hashMap.put("phone",data);
							}
						}
						else
						{
							if(!TextUtils.isEmpty(data))
							{
								hashMap.put("name", data);
							}
						}
					}
					indexCursor.close();
					contactList.add(hashMap);
				}
				cursor.close();
				//7.消息机制,发送给主线程告诉可以使用填充好的相应数据
				mHandler.sendEmptyMessage(0);


			};
			
		}.start();
		
		
	}

	private void initUI() {
		lv_contact = (ListView) findViewById(R.id.lv_contact);
		lv_contact.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//1.获取点中条目索引指向集合中的对象
				if(adapter!=null)
				{
					//获取当前条目指向集合对应的电话号码
					HashMap<String, String> hashMap = adapter.getItem(position);
					String phone = hashMap.get("phone");
					//需要被第三个导航界面使用
					//在结束次见面时，需要将数据返回过去
					Intent intent = new Intent();
					intent.putExtra("phone", phone);
					setResult(0,intent);
					finish();
				}



				
			}
		
		});
		
	}
	
	
	class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactList.size();
		}

		@Override
		public HashMap<String,String> getItem(int position) {
			return contactList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),R.layout.listiew_contact_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
			
			tv_name.setText(getItem(position).get("name"));
			tv_phone.setText(getItem(position).get("phone"));
			
			return view;
		}
		
	}

}
