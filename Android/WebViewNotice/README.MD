#Android WebView使用总结

##基本设置
```java
getSettings().setPluginState(WebSettings.PluginState.ON);//支持插件
/**
 * 输入法控制
 */
getSettings().setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
getSettings().setSupportMultipleWindows(true);//设置支持多窗口
/**
 * 手机如果开启了缩放字体,网页显示的比例会失调
 */
getSettings().setTextZoom(100);//禁止系统缩放字体大小
/**
 * 以下三项禁止设置,否则退出应用后会因为缩放组件没有及时取消广播而闪退
 */
//getSettings().setSupportZoom(true);//设置支持缩放
//getSettings().setBuiltInZoomControls(true);//设置支持缩放
//getSettings().setUseWideViewPort(true);//用户可以可以网页比例
getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
getSettings().setJavaScriptEnabled(true);// 可以使用javaScriptEnalsed
getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//js可以自动打开窗口
getSettings().setAllowFileAccess(true);//支持引用文件
getSettings().setAppCacheEnabled(true);//设置支持本地存储
getSettings().setDomStorageEnabled(true);//可以手动开启DOM Storage
if (Build.VERSION.SDK_INT >= 17) {// 不需要请求控制直接播放媒体文件
    //即可以自动播放音乐
    getSettings().setMediaPlaybackRequiresUserGesture(false);
}

setWebChromeClient(new MyWebChromeClient());// 设置浏览器可弹窗
```

##客户端设置

setWebViewClient有好处,典型用处是屏蔽广告,加载本地js,以及加载本地资源

###屏蔽广告

APP的网页经常会被运营商劫持,加载一些它推送的广告出现在页面的底部,这这个广告其实不是自己的APP的,是被用户所在的
运营商强行加上去的,遇到这个问题是在恼火,可以通过拦截域名的方式屏蔽广告

```java
 setWebViewClient(new WebViewClient() {
            @Override
            @Deprecated
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                try {// API21之前屏蔽广告
                    String hostName = new URL(url).getHost();
                    if (hostName != null) {
                        if (checkHostName(hostName)) {//checkHostName:检查是不是自己APP的域名,不是则不允许加载
                            return super.shouldInterceptRequest(view, url);
                        } else {
                            Log.e("siwebview", "no mine host!" + hostName);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //不是自己的APP域名,返回一个null,则屏蔽了广告
                return new WebResourceResponse(null, null, null);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest
                    request) {//高版本屏蔽广告同理
                String url = request.getUrl().toString();
                if (Build.VERSION.SDK_INT >= 21) {// API21后,屏蔽广告
                    String hostName = request.getUrl().getHost();
                    if (hostName != null) {
                        if (checkHostName(hostName)) {
                        } else {
                            return new WebResourceResponse(null, null, null);
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            /**
             * 覆写此方法是为了让webview仅加载自己的页面
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 页面加载开始
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 页面加载完成(注意部分手机并不会执行该方法)
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
```

###加载本地JS
有的APP需要在网页里注入js,而这个js的注入是由客户端完成的,因为js里面包含的一些用户的信息等,无法通过网页结果获取.

可以在页面加载开始的时候注入,或页面加载完毕的时候注入

```java
 setWebViewClient(new WebViewClient() {

            /**
             * 页面加载开始
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                try {
                    InputStream inputStream = getContext().getAssets().open("android_qqsd.js");
                    String a = convertStreamToString(inputStream);
                    loadUrl("javascript:" + a + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 页面加载完成(注意部分手机并不会执行该方法)
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完毕注入同理
            }

        });
```

但是仅仅在页面加载开始和完成就注入js其实并不能保证一定注入成功.

开始不能注入成功,是因为网页才刚刚开始加载,功能没有设置好,并不能马上的接受js的注入

完成不能注入成功,是因为部分手机并不会执行完成方法.

故js的注入还需要设置setWebChromeClient(),以及通过js交互的方式来完成注入,在后文中会有讲解


###加载本地资源

有时候网页的素材过大,需要将部分资源提前放置到本地APP,加载的时候则不需要请求网络,这样可以极大提高
网页的打开速度,此时就需要拦截网页的url的请求,加载本地的资源并返回

注意:所有的url请求并必须是相同的域名,否则会产生跨域行为.


```java
 setWebViewClient(new WebViewClient() {
            @Override
            @Deprecated
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return interceptRes(url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest
                    request) {
                String url = request.getUrl().toString();
                return interceptRes(url);
            }
        });


        /**
         * url请求,本地加载
         *
         * @param url url请求
         * @return 拦截结果
         */
        private WebResourceResponse interceptRes(String url) {
            if (url.contains("/res/") ) {
            //假设请求url包含res资源目录,则表明是去想服务器请求资源,而本地
            //已经包含了这个资源,则拦截这个请求,返回本地的资源
            //注意返回一个WebResourceResponse则表示拦截,如果返回null则表示不拦截,正常请求服务器
                WebResourceResponse webResourceResponse = null;
                try {
                    int dianIndex = url.lastIndexOf(".");
                    if (dianIndex != -1) {//获取资源文件的格式,例如这是一个.png,还是.js,还是.mp3
                        String mime = url.substring(dianIndex + 1);
                        if (mime.equals("js")) {//MimeTypeMap没有js格式需要特殊处理
                            mime = "application/x-javascript";
                        } else if (mime.equals("ttf")) {//MimeTypeMap没有字体格式需要特殊处理
                            mime = "application/octet-stream";
                        } else{//其他格式可以MimeTypeMap获取其mime类型
                            mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mime);
                        }
                        if (mime == null) {//假设没有获取到正常的mime类型,则设置为通用类型
                            mime = "*/*";
                        }
                        Log.e("siyehua-new", " mime: " + mime);
                        File file = new File(url);//获取本地的资源
                        if (!file.exists()) {
                            //假设本地没有这个资源,即开始没有下载好,则返回null
                            //表示不拦截,去请求服务器的资源
                            Log.e("siyehua", "file no found");
                            return null;
                        }
                        //一切正常,创建webResourceResponse
                        webResourceResponse = new WebResourceResponse(mime, "utf-8", new
                                FileInputStream(url));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return webResourceResponse;
            }
            return null;
        }
```


##设置ChromeClient

setWebChromeClient除了让浏览器可弹窗外,还可以监听网页的进度变化等.

```java
    setWebChromeClient(new MyWebChromeClient());// 设置浏览器可弹窗

    /**
     * 浏览器可弹窗
     *
     * @author Administrator
     */
    final class MyWebChromeClient extends WebChromeClient {
        /**
         * js的注入,除了前面的方法,还可以听过监听该方法来注入
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress > 80) {//80%是一个大概的数字,实际上该注入方法有可能失败
                try {
                    InputStream inputStream = getContext().getAssets().open("android_qqsd.js");
                    String a = convertStreamToString(inputStream);
                    loadUrl("javascript:" + a + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 设置该方法网页的js可以弹出确认/取消对话框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult
                result) {
            new AlertDialog.Builder(getContext()).setTitle("App Titler").setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).create().show();
            return true;
        }

        //获得网页的标题，作为应用程序的标题进行显示
        public void onReceivedTitle(WebView view, String title) {
           MainActivity.this.setTitle(title);
        }
    }
```

##JS交互
js交互可是说是WebView最重要的手段,默认是禁止js行为的,因为js会带来安全问题
```java
getSettings().setJavaScriptEnabled(true);// 可以使用javaScriptEnalsed

//jsObjectStr是在网页中调用的对象
addJavascriptInterface(new JsObject(), "jsObjectStr");

class JsObject {
        // @JavascriptInterface是为了支4.2及以上的js交互
        @JavascriptInterface
        public void app_method() {
            //对应的逻辑操作
        }
    }

//对应的网页代码:
jsObjectStr.app_method();

```

其他自定义方法同理,js交互需要注意以下几个问题
 * js是在网页线程,不是主线程,不能操作view
 * 传参数时,只能传入基本类型以及String,不能传入json对象等其他复杂对象类型
 * [js安全问题](https://github.com/pedant/safe-java-js-webview-bridge)


##其他问题
 * Activity销毁时需要调用webView.destroy()才能销毁webview
 * Activity返回时,需要重新返回方法,来判断网页是否能返回
    ```
    @Override
    public void onBackPressed() {
        if (siWebView.canGoBack()) {
            siWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
    ```
