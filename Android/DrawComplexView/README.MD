错误收集类
```java
package com.wbai.qqsd.view;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.wbai.qqsd.common.Constants;
import com.wbai.qqsd.common.SiAppLication;
/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候<br>
 * 有该类来接管程序,并记录发送错误报告.
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE;
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}
	/** 获取CrashHandler实例 ,单例模式 */
	public synchronized static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}
	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {// 退出整个应用
			SiAppLication.getApplicationInstance().exit();
		}
	}
	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		new Thread() {// 使用Toast来显示异常信息
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
		collectDeviceInfo(mContext);// 收集设备参数信息
		String errorPath = saveCrashInfo2File(ex);// 保存日志文件
		if (errorPath != null) {// 如果错误日志不为空,可以上传错误日志到服务器(暂未处理)
		}
		return true;
	}
	/**
	 * 收集设备参数信息
	 *
	 * @param ctx
	 */
	private void collectDeviceInfo(Context ctx) {
		infos.put("time", formatter.format(new Date()));// BUG发生的时间
		infos.put("className", ctx.getClass().getName() + "");// 得到当前的类的名称
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);// 得到版本名字
				infos.put("versionCode", versionCode);// 得到版本号
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {// 利用反射获取设备信息
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}
	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {// 拼接错误字符串
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);// 打印错误信息
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {// 把错误日志保存到指定的目录
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash-" + time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = Constants.CRASH_DIR + "/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}
```

Application类设置
监听到错误,退出程序(注意当发生错误时,发送广播,跳转Intent等方式都是无效的)
```java
package com.siyehua.testanimation;
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.siyehua.testanimation.CrashHandler.ErrorListener;
public class SiApplication extends Application {
	private static SiApplication instance;
	private List<Activity> mList = new LinkedList<Activity>();
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}
	public synchronized static SiApplication getInstance() {
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CrashHandler a = CrashHandler.getInstance();
		a.init(this);
		a.setOnErrorListener(new ErrorListener() {
			@Override
			public void error() {
				Log.e("接收到错误消息", "");
				sendBroadcast(new Intent("siyehua"));//此句无效,无法发送广播,或启动服务
			}
		});
	}
}
```


最后一步:在BaseActivity中Activity加入到集合中
```java
SiApplication.getInstance().addActivity(this);
```