/**
 * 
 */
package com.example.mobilesafe.receiver;

import com.example.mobilesafe.R;
import com.example.mobilesafe.service.LocationService;
import com.example.mobilesafe.utils.ConstantValue;
import com.example.mobilesafe.utils.ToastUtil;

import com.example.mobilesafe.utils.SpUtils;

import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Messenger;
import android.provider.Telephony.Sms;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;

/**
 * @author Administrator
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//1.判断是否开启防盗保护
		boolean open_security = SpUtils.getBoolean(context,ConstantValue.OPEN_SECURITY,false);
		if(open_security)
		{
			//上下文环境(广播接收者对应的上下文字节码)
			ComponentName mDeviceAdminSample = new ComponentName(context,DeviceAdmin.class);
			DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			//2.获取短信内容
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			//3.循环遍历短信过程
			for(Object object:objects)
			{
				//4.获取短信对象
				SmsMessage sms = SmsMessage.createFromPdu((byte[])object);
				//5.获取短信对象的信息
				String  originatingAddress= sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				//6.判断是否包含播放音乐的关键字
				if(messageBody.contains("#*alarm*#"))
				{
					//7.播放音乐(准备音乐，MediaPlayer)
					MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.ylzs);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
					
				}
				
				if(messageBody.contains("#*location*#"))
				{
					//8.开启服务获取位置
					context.startService(new Intent(context,LocationService.class));
				}
				
				
				if(messageBody.contains("#*lockscreen*#"))
				{
					if(mDPM.isAdminActive(mDeviceAdminSample))
					{
						mDPM.lockNow();
//						mDPM.resetPassword(password, flags);
					}
					else
					{
						ToastUtil.show(context,"请先激活");
					}
				}

				if(messageBody.contains("#*wipedata*#"))
				{

					if(mDPM.isAdminActive(mDeviceAdminSample))
					{
						mDPM.wipeData(0);//手机资源
						mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//外部资源 
					}
					else
					{
						ToastUtil.show(context,"请先激活");
					}
				}
				
			}
		}

	}

}
