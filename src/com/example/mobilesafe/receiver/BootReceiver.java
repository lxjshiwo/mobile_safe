/**
 * 
 */
package com.example.mobilesafe.receiver;

import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.SpUtils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author Administrator
 *
 */
public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = BootReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.e(TAG,"重启手机成功，并监听到相应广播");
		//1.获取开机后手机的sim卡序列号
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,new Intent(), 0);
		String simSerialNumber = tm.getSimSerialNumber()+"xxx";
		//2.sp中存储的序列号
		String sim_number = SpUtils.getString(context,ConstantValue.SIM_NUMBER,"");
		//3.比对
		if(!simSerialNumber.equals(sim_number))
		{
			//4,发送短信给选中联系人号码
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("13172970375",null,"sim changed!!!",pendingIntent,null);
		}

	}

}
