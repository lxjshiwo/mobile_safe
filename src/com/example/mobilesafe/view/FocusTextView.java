/**
 * 
 */
package com.example.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * @author Administrator
 *能够获取焦点的textView
 */
public class FocusTextView extends TextView {

	/**
	 * 使用在通过java代码创建控件时候
	 * @param context
	 */
	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 系统调用情况下(带属性+上下文构造方法)
	 * 通过xml实现控件
	 * @param context
	 * @param attrs
	 */
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 由系统调用(带属性+上下文+定义的样式文件的构造方法)
	 * xml转换为java代码
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	
	//由系统调用
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

}
