以前看有这么一段程序来判断应用是否被切换到了后台....

```java
/**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
```

今天在阅读Google官方的Training的时候,注意到这个一段文字
.....
To be notified when the user exits your UI, implement the onTrimMemory() callback in your Activity classes. You should use this method to listen for the TRIM_MEMORY_UI_HIDDEN level, which indicates your UI is now hidden from view and you should free resources that only your UI uses.
Notice that your app receives the onTrimMemory() callback with TRIM_MEMORY_UI_HIDDEN only when all the UI components of your app process become hidden from the user. .....
文中说到当用户退出你的UI,onTrimMemory() 就会被调用.同时会发送TRIM_MEMORY_UI_HIDDEN level这么一个东西叫我们监听.下面的notice是:只有用户隐藏你的应用UI组件才会有这个情况.

测试了一下,当你在任意一个Activity中这么写:
```java
@Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(level == TRIM_MEMORY_UI_HIDDEN){
            Log.e("siyehua", "background...");
        }
    }
```
不管是按Home键,还是有来电,还是你点击了状态栏的通知进入了其他的应用,都会调用这个方法,能正常的监听到应用被切换到了后台.完美监听应用是否切换到了后台\(^o^)/~
Notice:只需要重写一个Activity的该方法即可.切换到其它Activity并不会触发该方法