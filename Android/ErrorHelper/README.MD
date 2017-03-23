�����ռ���
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
 * UncaughtException������,��������Uncaught�쳣��ʱ��<br>
 * �и������ӹܳ���,����¼���ʹ��󱨸�.
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	// ϵͳĬ�ϵ�UncaughtException������
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandlerʵ��
	private static CrashHandler INSTANCE;
	// �����Context����
	private Context mContext;
	// �����洢�豸��Ϣ���쳣��Ϣ
	private Map<String, String> infos = new HashMap<String, String>();
	// ���ڸ�ʽ������,��Ϊ��־�ļ�����һ����
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {
	}
	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public synchronized static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}
	/**
	 * ��ʼ��
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// ��ȡϵͳĬ�ϵ�UncaughtException������
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/**
	 * ��UncaughtException����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
			mDefaultHandler.uncaughtException(thread, ex);
		} else {// �˳�����Ӧ��
			SiAppLication.getApplicationInstance().exit();
		}
	}
	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 *
	 * @param ex
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		new Thread() {// ʹ��Toast����ʾ�쳣��Ϣ
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "�ܱ�Ǹ,��������쳣,�����˳�.", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
		collectDeviceInfo(mContext);// �ռ��豸������Ϣ
		String errorPath = saveCrashInfo2File(ex);// ������־�ļ�
		if (errorPath != null) {// ���������־��Ϊ��,�����ϴ�������־��������(��δ����)
		}
		return true;
	}
	/**
	 * �ռ��豸������Ϣ
	 *
	 * @param ctx
	 */
	private void collectDeviceInfo(Context ctx) {
		infos.put("time", formatter.format(new Date()));// BUG������ʱ��
		infos.put("className", ctx.getClass().getName() + "");// �õ���ǰ���������
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);// �õ��汾����
				infos.put("versionCode", versionCode);// �õ��汾��
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {// ���÷����ȡ�豸��Ϣ
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
	 * ���������Ϣ���ļ���
	 *
	 * @param ex
	 * @return �����ļ�����,���ڽ��ļ����͵�������
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {// ƴ�Ӵ����ַ���
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);// ��ӡ������Ϣ
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {// �Ѵ�����־���浽ָ����Ŀ¼
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

Application������
����������,�˳�����(ע�⵱��������ʱ,���͹㲥,��תIntent�ȷ�ʽ������Ч��)
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
				Log.e("���յ�������Ϣ", "");
				sendBroadcast(new Intent("siyehua"));//�˾���Ч,�޷����͹㲥,����������
			}
		});
	}
}
```


���һ��:��BaseActivity��Activity���뵽������
```java
SiApplication.getInstance().addActivity(this);
```
