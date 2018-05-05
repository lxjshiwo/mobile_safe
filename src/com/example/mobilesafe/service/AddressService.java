/**
 * 
 */
package com.example.mobilesafe.service;

import com.example.mobilesafe.R;
import com.example.mobilesafe.toast.MiExToast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class AddressService extends Service {
	
	
	
	public static final String TAG = AddressService.class.getSimpleName();
	private TelephonyManager mTM;
	private MyPhoneStateListener mPhoneStateListener;
	
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	private WindowManager mWM;

	@Override
	public void onCreate() {
		//第一次开启服务后，就需要去管理toast
		//电话状态的监听
		//服务开启时监听，关闭时监听不需要

		//1.电话管理者对象
		mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//2.监听电话状态
		mPhoneStateListener = new MyPhoneStateListener();
		mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		//获取窗体对象
		mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		//销毁toast
		//开启服务时监听电话的对象，取消事件监听
		if(mTM!= null && mPhoneStateListener !=null)
		{
			mTM.listen(mPhoneStateListener,PhoneStateListener.LISTEN_NONE);
		}

		super.onDestroy();
	}
	
	
	
	class MyPhoneStateListener extends PhoneStateListener
	{
		private View mViewToast;

		//3.手动重写电话状态发生改变触发方法
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				//电话状态空闲(移除toast)
				Log.i(TAG,"挂断电话了,空闲了......");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				//电话状态摘机
				break;

			case TelephonyManager.CALL_STATE_RINGING:
				//电话状态响铃(展示toast)
				Log.i(TAG,"响铃了......");
				showToast(incomingNumber);
				break;


			}
			super.onCallStateChanged(state, incomingNumber);
		}

		private void showToast(String incomingNumber) {
			Toast.makeText(getApplicationContext(),incomingNumber,Toast.LENGTH_SHORT).show();

			final WindowManager.LayoutParams params = mParams;
//			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
////					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  默认能够被触摸
//					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//			params.format = PixelFormat.TRANSLUCENT;
//			//在响铃的时候显示toast,和电话类型一致
//			params.type = WindowManager.LayoutParams.TYPE_PHONE;
//			params.setTitle("Toast");
//			//指定toast的所在位置,为左上角位置
//			params.gravity = Gravity.LEFT + Gravity.TOP;
//			//toast显示效果(toast布局文件)xml--> view(toast),将土司挂在到windowManager窗体上
//			mViewToast = View.inflate(getApplicationContext(),R.layout.toast_view,null);
//			//在窗体上挂载一个view
//			mWM.addView(mViewToast,mParams);
//			Log.i(TAG,"add a toast");
			MiExToast miToast = new MiExToast(getApplicationContext());
			miToast.setDuration(MiExToast.LENGTH_ALWAYS);
//			miToast.setAnimations(R.style.anim_view);
			miToast.show();

		}
		
	}
	

}
