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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Administrator
 *
 */
public abstract class BaseSetupActivity extends Activity {
	
	
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		//2.创建手势管理的对象,用作管理onTouchEvent(event)传递过来的手势动作
		gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
					
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						//监控手势移动
						if(e1.getX() - e2.getX() > 0)
						{
							//由右向左调用子类方法移动到下一页，抽象方法
						
							showNextPage();
							
						}


						if(e1.getX() - e2.getX() < 0)
						{

							//由左向右调用子类方法移动到上一页
							showPrePage();
							
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}

					
					
					
				});
	}
	
//	public abstract void nextPage(View view){};
//	public abstract void prePage(View view){};
	
	

	//1.监听屏幕上响应的事件类型(按下(1次)、移动(多次)、抬起(1次))
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//3.通过手势处理类,接收多种类型的事件，用作处理的方法
		gestureDetector.onTouchEvent(event);

		return super.onTouchEvent(event);
	}
	
	
	//下一页的抽象方法，由子类具体确定跳转到那个界面
	protected abstract void showNextPage();

	//上一页的抽象方法，由子类具体确定跳转到那个界面
	protected abstract void showPrePage();
	
	
	
	//点击 下一页按钮跳转根据子类showNextPage方法响应
	public void nextPage(View v)
	{
		showNextPage();
		
	}

	
	//点击 下一页按钮跳转根据子类showNextPage方法响应
	public void prePage(View v)
	{
		showPrePage();
	}


}
