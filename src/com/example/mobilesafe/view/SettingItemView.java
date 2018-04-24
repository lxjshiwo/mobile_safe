/**
 * 
 */
package com.example.mobilesafe.view;

import com.example.mobilesafe.R;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class SettingItemView extends RelativeLayout {

	/**
	 * @param context
	 */
	public SettingItemView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//xml --> View 将设置界面条目转换为view对象 
		View.inflate(context,R.layout.setting_item_view,this);
//		View view = View.inflate(context,R.layout.setting_item_view, null);
//		this.addView(view);
		//自定义组合控件中的标题描述
		TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
		TextView tv_des = (TextView) this.findViewById(R.id.tv_des);
		CheckBox cb_box = (CheckBox) this.findViewById(R.id.cb_box);

	}

}
