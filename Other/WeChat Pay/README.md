##微信APP支付

微信支付有许多中,这里主要讲解Android客户端如果调起微信APP支付.

###步骤
1. 在[微信开发平台](https://open.weixin.qq.com/)注册账号.

 注册需要输入一个签名,该签名可以通过微信的签名APK获取.反编译了其源码,大概是这样子

 <a name = "app sign"/>
 ```java
   /**
       * 获取应用签名信息
       *
       * @param paramContext Context
       * @param paramString  packget name
       * @return
       */
      private String getRawSignature(Context paramContext, String paramString) {
          if ((paramString == null) || (paramString.length() == 0)) {
              return null;
          }
          PackageManager localPackageManager = paramContext.getPackageManager();
          PackageInfo localPackageInfo;
          try {
              localPackageInfo = localPackageManager.getPackageInfo(paramString, 64);
              if (localPackageInfo == null) {
                  return null;
              }
          } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
              return null;
          }
          Signature[] arrayOfSignature = localPackageInfo.signatures;
          if (arrayOfSignature == null || arrayOfSignature.length == 0) return null;
          String contentStr = "";
          int i = arrayOfSignature.length;
          for (int j = 0; j < i; j++)
              contentStr += MD5.getMessageDigest(arrayOfSignature[j].toByteArray());
          return contentStr;
      }
  ```
  其中MD5方法
  ```java
   public static String getMessageDigest(byte[] paramArrayOfByte) {
          char[] arrayOfChar1 = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
          try {
              MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
              localMessageDigest.update(paramArrayOfByte);
              byte[] arrayOfByte = localMessageDigest.digest();
              int i = arrayOfByte.length;
              char[] arrayOfChar2 = new char[i * 2];
              int j = 0;
              int k = 0;
              while (true) {
                  if (j >= i) return new String(arrayOfChar2);
                  int m = arrayOfByte[j];
                  int n = k + 1;
                  arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
                  k = n + 1;
                  arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
                  j++;
              }
          } catch (Exception localException) {
          }
          return null;
      }
  ```

2. 通过审核后,提交资料,开通微信支付.成功后会把商户号发送到账号的邮箱
3. 拿到商户号的账号密码,打开[微信支付](https://pay.weixin.qq.com/index.php/home/login?return_url=%2F)登录.
4. 查看APP支付的[详细介绍](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1)

 微信支付大概流程:

 ![Image](/Other/WeChat Pay/_001.png)

 具体请参考[官方详细过程](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3)

5. 服务端搭建

   商户系统和微信支付系统主要交互说明：

   步骤1：用户在商户APP中选择商品，提交订单，选择微信支付。

   步骤2：商户后台收到用户支付单，调用微信支付统一下单接口。参见【统一下单API】。

   步骤3：统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay

   步骤4：商户APP调起微信支付。api参见本章节【app端开发步骤说明】

   步骤5：商户后台接收支付通知。api参见【支付结果通知API】

   步骤6：商户后台查询支付结果。，api参见【查询订单API】


6. Android端下载[SDK](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=11_1)

  * SDK解压后复制jar包到自己的项目
  * 新建一个叫WXPayEntryActivity的Activity,并在在AndroidManifest.xml中注册.该Activity的路径必须为 包名.wxapi.WXPayEntryActivity.
  * 新建一个支付的Activity,类如叫WeChatPayActivity,并在在AndroidManifest.xml中注册.(路径没有要求)

  AndroidManifest的注册格式:
  ```java
  <activity
              android:name=".WeChatPayActivity"
              android:launchMode="singleTop">
              <intent-filter>
                  <action android:name="android.intent.action.VIEW"/>
                  <category android:name="android.intent.category.DEFAULT"/>
                  <data android:scheme="你的微信APPID"/>
              </intent-filter>
  </activity>
  ```

7. 客户端PPP请求服务端→服务端向微信服务器请求产生预付单→服务端返回相应的信息给客户端(需要返回的[字段](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2))
  →APP代码中调起APP支付.

  详细调起支付代码:

  ```java
  private IWXAPI api;
  private void startPay(){
  	api = WXAPIFactory.createWXAPI(this, "你的微信APPID");
  	api.registerApp("你的微信APPID");
  	String content ="从服务器得到的数据";
  	JSONObject json = new JSONObject(content);
  	PayReq req = new PayReq();
    req.appId			= json.getString("appid");
    req.partnerId		= json.getString("partnerid");
    req.prepayId		= json.getString("prepayid");
    req.nonceStr		= json.getString("noncestr");
    req.timeStamp		= json.getString("timestamp");
    req.packageValue	= json.getString("package");
    req.sign			= json.getString("sign");//签名
    req.extData			= "app data"; // optional
    api.sendReq(req);
  }
  ```

8. 调起微信支付,微信支付成功后会把结果返回到WXPayEntryActivity.在onResp()方法中处理支付结果.
9. 得到支付结果,不管成功失败,都向服务器请求真正的结果.
10. 服务器告诉APP真正的结果
11. APP刷新充值数据

 ###问题

 微信无法调其支付的原因有很多,总结大概有下面几个.
  * 签名不正确.
    * 使用Ecplise请使用应用市场版本,不要直接调试的版本.
    * 使用Android Studio请直接在Project Struture中配置好签名.
    * 签名都正确,可能微信服务器没有反应过来,等一个小时左右
    * 微信客户端没反应过来,切换账号或者直接删除微信重装一个.
    * 如有分享,可尝试分享,能分享签名就是没有问题的

  * WXPayEntryActivity路径不正确
  * WeChatPayActivity的intent-filter不正确.
  * 支付签名不正确.
    这个支付签名,和Android的签名不是同一个概念.微信支付需要一个sign的签名字段.

    [签名算法](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3)

    [签名验证](https://pay.weixin.qq.com/wiki/tools/signverify/)

    * 这个签名有两个,一个是服务端向微信请求预付单之时,需要生成一个签名.
    * 服务端得到结果,把得到的结果重新签名并返回,APP需要的是这个签名
    * 参与签名的[key](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2),全部都是小写,并不是步骤5中的大小写混搭的.请注意

