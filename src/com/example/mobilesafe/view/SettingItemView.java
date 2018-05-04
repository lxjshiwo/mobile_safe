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
	 * 
	 */
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.mobilesafe";
	private CheckBox cb_box;
	private TextView tv_des;
	private TextView tv_title;
	private String mDestitle;
	private String mDeson;
	private String mDesoff;


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
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_des = (TextView) this.findViewById(R.id.tv_des);
		cb_box = (CheckBox) this.findViewById(R.id.cb_box);
		
		//获取自定义以及原生属性的操作，写在此处AtributesSet attr中获取
		initAttrs(attrs);
		
		//获取文件中的定义的字符串定义
		tv_title.setText(mDestitle);

	}

	
	/**
	 *attrs 构造方法中维护好的属性集合
	 *返回属性集合中自定义属性
	 */
	private void initAttrs(AttributeSet attrs) 
	{ 
		mDestitle = attrs.getAttributeValue(NAMESPACE,"destitle");
		mDeson = attrs.getAttributeValue(NAMESPACE,"deson");
		mDesoff = attrs.getAttributeValue(NAMESPACE,"desoff");
	}

	/**
	 *@return 返回当前SettingItemView是否被选中 true开启 false关闭
	 */
	public boolean isCheck() {
		//由checkBox选中结果决定当前条目是否开启  
		return cb_box.isChecked();
	}
	
	/**
	 *是否开启变量由外部点击过程中做传递
	 */
	public void setCheck(boolean isCheck)
	{
		//当前条目在选择过程中,cb_box选中状态也跟随转变
		cb_box.setChecked(isCheck);
		if(isCheck)
		{
			tv_des.setText("自动更新已开启");
		}
		else
		{
			tv_des.setText("自动更新已关闭");
		}
		
	}
	
	
	

}
