package com.example.mobilesafe.activity;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mobilesafe.R;
import com.example.mobilesafe.R.id;
import com.example.mobilesafe.R.layout;
import com.example.mobilesafe.R.menu;
import com.example.mobilesafe.utils.StreamUtil;
import com.example.mobilesafe.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SplashActivity extends ActionBarActivity {

    protected static final String tag = "SplashActivity";

    /*
     * 更新新版本的状态码 
     */
	protected static final int UPDATE_VERSION = 100;

    /*
     * 进入应用程序界面 
     */
	protected static final int ENTER_HOME = 101;

	/*
	 * URl地址出错
	 * */
	protected static final int URL_ERROR = 102;
	protected static final int IO_ERROR = 103;
	protected static final int JSON_ERROR = 104;

	private TextView tv_version_name;
	private int mLocalVersionCode;
	protected String mVersionDes;
	private String mDownloadUrl;

	private Handler mHandler = new Handler()
	{
		//自定义方法
		//alt+ctrl+箭头下 向下拷贝
		public void handleMessage(android.os.Message msg) 
		{
			Log.e(tag,msg.what+"");
			switch (msg.what) {
			case UPDATE_VERSION:
				//弹出对话框，提示用户更新
				showUpdateDialog();
				break;
			case ENTER_HOME:
				//进入程序的主界面
				enterHome();
				break;
			case URL_ERROR:
				ToastUtil.show(getApplicationContext(),"Url异常");
				enterHome();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(),"读取 异常");
				enterHome();
				break;
			case JSON_ERROR:
				//弹出对话框，提示用户更新
				ToastUtil.show(getApplicationContext(),"json解析异常");
				enterHome();
				break;

			default:
				enterHome();
				break;
			}
			
		};
	};

	private RelativeLayout rl_root;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除当前头部的方式1
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //初始化UI
        initUI();
        //初始化数据 
        initData();
        //设置动画
        initAnimation();
    }


    /**
	 * 弹出对话框提示用户更新
	 */
	protected void showUpdateDialog() {
		//对话框依赖于activity存在
		Builder builder = new AlertDialog.Builder(this);
		//左上角图标号
		builder.setIcon(R.drawable.ic_launcher);
		//设置标题
		builder.setTitle("版本更新");
		//设置描述内容
		builder.setMessage(mVersionDes);
		builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//下载apk,
				dowloadApk();
				
			}
		});
		builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//取消对话框，进入主界面
				enterHome();
			}
		});
		
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				//点击取消也进入主要界面
				enterHome();
				dialog.dismiss();
				
			}
		});
		builder.show();
	}


	/**
	 * 
	 */
	protected void dowloadApk() {
		//apk下载连接地址,防止apk所在目的地址
		//1.判断是否挂载sd卡
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			//2.获取sd卡路径
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"mobilesafe.apk";
			//3.发送请求获取apk
			HttpUtils httpUtils = new HttpUtils();
			//4.发送请求，传递参数(下载地址，下载应用所在位置)
			//httpUtils.send(method,url,callBack)
			httpUtils.download(mDownloadUrl,path, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					//下载成功,在sd卡中的apk
					Log.i(tag,"下载成功");
					File file = arg0.result;
					//提示用户安装
					installApk(file);
				}
				

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					//下载失败
					
				}
				
				//刚刚开始下载的方法
				@Override
				public void onStart() {
					Log.i(tag, "开始下载");
					super.onStart();
				}

				//下载过程中的方法(下载apk总大小，当前下载位置，是否下载完成)
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag,"下载中");
					super.onLoading(total, current, isUploading);
				}
				


			});

			
		}



		
	}

	/**
	 *安装对应apk
	 */
	private void installApk(File file) 
	{
		//系统应用界面
		Intent intent = new Intent("action.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		//文件作为数据源
//		intent.setData(Uri.fromFile(file));
		//设置安装类型
//		intent.setType("application/vnd.android.package-archive");
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//		startActivity(intent);
		startActivityForResult(intent, 0);




	
	}

	//开启一个activity后返回结果调用
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		enterHome();
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * 
	 */
	protected void enterHome() {
		//进入程序的主界面
		Intent intent = new Intent(this,HomeActivty.class);
		startActivity(intent);

		//在开启一个新的界面后将导航界面关闭，导航界面仅见一次
		finish();
	}


	/**
	 * 获取数据方法
	 */
	private void initData() {
		//1.获取应用版本
		tv_version_name.setText("版本名称"+getVersionName());
		//检测(本地版本号和服务器版本号)是否有更新,如果有更新,提示用户下载
		//2.获取本地版本号
		mLocalVersionCode = getVersionCode();
		//3.获取服务器版本号(客户端发请求，服务端给请求(json,xml))
		//更新版本的版本名称
		//版本描述信息
		//服务器版本号
		//apk版本下载地址
		checkVersion();
//		enterHome();


	}


	/**
	 * 
	 */
	private void checkVersion() {
		new Thread(){


			public void run() {
				//发送请求 获取数据, 参数为请求json的连接
				//测试阶段 不是最优
				//模拟器访问电脑tomcat 10.0.0.2
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					//封装url地址
					URL url = new URL("http://119.28.25.120:8080/update.json");
					//2.开启一个链接
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					//3.设置常用请求参数(请求头)
					//请求超时
					connection.setConnectTimeout(2000);
					//读取超时
					connection.setReadTimeout(2000);
					//设置请求方式默认get
//					connection.setRequestMethod("POST");
					//4.获取响应码
					Log.e(tag,connection.getResponseCode()+"");
					if(connection.getResponseCode() == 200)
					{
						
						//5,以流的形式将数据保存下来
						InputStream is = connection.getInputStream();
						//6.将流转换为字符串(工具类)
						String json = StreamUtil.streamToString(is);
						Log.i(tag, json);
						//7.json解析
						JSONObject jsonObject = new JSONObject(json);
						String versionName = jsonObject.getString("versionName");
						mVersionDes = jsonObject.getString("versionDes");
						String versionCode = jsonObject.getString("versionCode");
						mDownloadUrl = jsonObject.getString("dowloadUrl");
						
						//debug

						//比对版本号(服务器版本号 )
						if(mLocalVersionCode < Integer.parseInt(versionCode))
						{
							//提示用户更新
							msg.what = UPDATE_VERSION;
						}
						else
						{
							//进入应用程序
							msg.what = ENTER_HOME;
						}

						

					}


					
				} catch (MalformedURLException e) {

					e.printStackTrace();
					msg.what = URL_ERROR;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = IO_ERROR;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = JSON_ERROR;
				}
				finally
				{
					//指定睡眠时间，请求网络时间超过4秒则不做处理
					//请求网络的时长小于4秒，强制让其睡眠4秒钟
					long endTime = System.currentTimeMillis();
					if(endTime - startTime < 4000)
					{
						
						try {
							Thread.sleep(4000 - (endTime - startTime));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
				}
			}
		}.start();
		/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
	}


	/**
	 * 
	 */
	/**
	 * 返回版本号,非0为正确
	 * @return
	 */
	private int getVersionCode() {
		//1.包管理者对象
		PackageManager pm = getPackageManager();

		//2.从包管理者对象中获取包的基本信息（版本名称，版本号）传0代表基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);

			return packageInfo.versionCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return 0;
	}


	/**
	 * 获取版本名称
	 * @return	应用版本名称,返回null代表有异常
	 */
	private String getVersionName() {
		//1.包管理者对象
		PackageManager pm = getPackageManager();

		//2.从包管理者对象中获取包的基本信息（版本名称，版本号）传0代表基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);

			return packageInfo.versionName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


	/**
	 * 初始化UI alt+shift+j
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
	}

	/**
	 *增加滚入的动画效果
	 *
	 */
	private void initAnimation() {

		AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
		alphaAnimation.setDuration(3000);
		rl_root.startAnimation(alphaAnimation);
	}



}
