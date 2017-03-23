##
支付宝APP支付


支付宝的SDK基本上是行业内几乎最复杂的了,可能是因为它要求的安全性最高吧.

开发说明文档的目录结构特别不清晰,很多东西明明记得看过,想去重新找了,又找不到在哪.

以前最集成趣拍的时候,就需要用到阿里百川里的一些东西,看了两天的开发文档,硬是没看懂.

最后通过趣拍技术团队自己发出了一个DEMO才调通的.

###
接入步骤


支付宝支付详情过程可以看这个[集成过程](https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.1O9SpG&treeId=44&articleId=103921&docType=1)

支付的的接入步骤其实大概就分四个步骤,这个接入指南因为是通用的,所以很多步骤APP并不需要.

主要步骤如下:

1. 接入:注册支付宝账户,提交相应的资料并通过审核,入驻基本结束了.
2. 创建[自用型应用](https://openhome.alipay.com/platform/createApp.htm?enctraceid=_xtIFIWCn10r8klM38IZIFMDOi3W5jACHL5jm8OtjcU,).入驻了并不能马上使用,前面仅仅是提交的个人/企业 账号通过了,还需要创建移动应用.

   提交应用会涉及到一个叫本身的应用签名,这个签名的算法是通用的,具体获得签名代码与之前一篇微信开发中的签名一直,具体[签名](/Other/WeChat Pay/README.md#app sign)

3. 设RAS(SHA1)公钥.

   具体设置公钥[文档](https://doc.open.alipay.com/doc2/detail?treeId=58&articleId=103242&docType=1).
   注意:推荐方式一的Windows方式无效,查看其脚本缺少了对应的文件的,这SDK做的,也是醉了.

   可以用MAC OSX或者生成方式二.

   文档后面说什么java,非java,说得很玄乎,根本就没理清楚他们之间的关系.在这里解释一下.
    * 每次生成三个文件,分别是public,private,private_pkcs8这个三个文件
    * public就是应用的公钥,就是直接设置到支付宝中的.填写的时候就填写这个.private就是密钥,企业自己保留
    * 在设置的时候,左下角会提示你验证自己的公钥正不正确.如果自己不会验证,有对应的工具下载
    * 自主验证时,如果用的语言是非java,则用普通的private文件的密钥验证即可
    * 使用支付宝提供的工具是使用java编写的,验证的时候需要输入private_pkcs8的密钥
    * 所有的公钥,密钥,开头和结尾有-----BEGIN RSA PRIVATE KEY-----,需要把开头和结尾字符串,以及换行去掉.

   **需要填写的地方:(注意这个非常重要,如果没有填完全,直接导致你无法调用成功)**
    * 登录后,跳转到[管理中心](https://openhome.alipay.com/platform/manageApp.htm)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![管理中心图片](/Other/ZhiFuBao&nbsp;Pay/_003.png)

      我的应用中,包含两个应用,一个是基础应用,签约的时候自动创建的,且不可删除.

      一个是上述步骤中自主创建的应用.
    * 点击签约应用的查看→选择应用环境→设置图中红框部分

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![签约应用](/Other/ZhiFuBao&nbsp;Pay/_004.png)

    * 同样的方法设置自己创建的应用的RSA公钥.
    * 设置合作伙伴RAS公钥.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![合作伙伴](/Other/ZhiFuBao&nbsp;Pay/_005.png)
    * 设置无线产品RAS公钥.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![无线产品](/Other/ZhiFuBao&nbsp;Pay/_006.png)

   **一共有四处需要设置.**如没有设置成功会报如下错误.

&nbsp;&nbsp;&nbsp;![RSA不正确错误提示](/Other/ZhiFuBao&nbsp;Pay/_007.png)

   这个错误网上一搜一大堆,但是都是告诉你应用的公钥和你填写的私钥不正确,其实并不是这样.因为支付宝现在(2016-06-07)填写
   公钥的时候,可以提供工具自主验证,上述步骤中有讲到.如果你的验证通过了,那你的公钥和私钥肯定是可以匹配的.**问题就是出现在有些地方没有
   设置RSA**

   其中第一个是为了Demo能成功调用.第二个设置是为了自己的应用能成功调用.第三第四个不明原因,但是一定要设置,如果不设置就无法调用.

   开发文档中说合作伙伴和无线产品是其他东西并不需要设置,其实并不是,如果不设置一定无法调通.之前调试Demo的时候一直很奇怪,
   为何支付宝的Demo不需要任何应用签名(即不验证Android应用的包名,签名就可以测试付款.)现在想来应该是签约应用对应的包名固定为Demo的包名.

4. 开通移动支付

    管理中心→点击对应的应用→选择左边栏中的'功能信息'→移动支付这一行,点击申请开通,就完成了.


到此,已经完成了所有的设置,下一步则开始移动支付的具体流程


###
移动支付


支付宝移动APP支付[文档](https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103563&docType=1)
这个文档写的清晰明了详细,给个赞.和上面的几个文档形成极其鲜明的对比.

大概的交互过程文档中已经给出:

####
功能流程


![无线产品](/Other/ZhiFuBao%20Pay/_001.png)

####
数据交互


![无线产品](/Other/ZhiFuBao%20Pay/_002.png)


###
Android详细代码


1. [下载SDK](https://doc.open.alipay.com/doc2/detail.htm?treeId=54&articleId=104509&docType=1),解压得到jar包.导入jar包到项目中.
   注意,假如编译出现jar包冲突,可能是用了同类型的阿里产品.我的有一个项目中使用了友盟推送,其中有一个淘宝验证与这个支付jar包冲突,
   此时去友盟推送那边下载一个去掉那个验证的jar包即可.

2. 修改AndroidManifest.xml
   ```xml
   <activity
               android:name="com.alipay.sdk.app.H5PayActivity"
               android:configChanges="orientation|keyboardHidden|navigation"
               android:exported="false"
               android:screenOrientation="behind" >
   </activity>
   <activity
               android:name="com.alipay.sdk.auth.AuthActivity"
               android:configChanges="orientation|keyboardHidden|navigation"
               android:exported="false"
               android:screenOrientation="behind" >
    </activity>
   ```
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
   <uses-permission android:name="android.permission.READ_PHONE_STATE" />
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   ```

3. 添加混淆配置
   ```java
   -libraryjars libs/alipaySDK-20150602.jar

   -keep class com.alipay.android.app.IAlixPay{*;}
   -keep class com.alipay.android.app.IAlixPay$Stub{*;}
   -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
   -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
   -keep class com.alipay.sdk.app.PayTask{ public *;}
   -keep class com.alipay.sdk.app.AuthTask{ public *;}
   ```

4. 定义变量设置对应的商户PID(注意这个不是应用ID).

   定义变量设置商户收款号,即账号.

   无需设置密钥(密钥的RSA加密处理是在服务端完成的,所以不要在自己的应用中设置密钥)

5. 拼接参数,调用支付.
   ```java
    /**
         * call alipay sdk pay. 调用SDK支付
         */
        public void pay(View v) {
            String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

            /**
             * sign()方法在服务端完成.具体如何生成sign,请参考Demo
             */
            String sign = sign(orderInfo);
            try {
                /**
                 * 仅需对sign 做URL编码
                 */
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            /**
             * 完整的符合支付宝参数规范的订单信息
             */
            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";

            //因为支付宝的调用必须在子线程完成,所以需要重新开启一个线程
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(PayDemoActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo, true);
                    Log.e("payresult:", result);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }

   ```
   getOrderInfo()方法
   ```java
   /**
        * create the order info. 创建订单信息
        */
       private String getOrderInfo(String subject, String body, String price) {

           // 签约合作者身份ID
           String orderInfo = "partner=" + "\"" + PARTNER + "\"";

           // 签约卖家支付宝账号
           orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

           // 商户网站唯一订单号
           orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

           // 商品名称
           orderInfo += "&subject=" + "\"" + subject + "\"";

           // 商品详情
           orderInfo += "&body=" + "\"" + body + "\"";

           // 商品金额
           orderInfo += "&total_fee=" + "\"" + price + "\"";

           // 服务器异步通知页面路径
           orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

           // 服务接口名称， 固定值
           orderInfo += "&service=\"mobile.securitypay.pay\"";

           // 支付类型， 固定值
           orderInfo += "&payment_type=\"1\"";

           // 参数编码， 固定值
           orderInfo += "&_input_charset=\"utf-8\"";

           // 设置未付款交易的超时时间
           // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
           // 取值范围：1m～15d。
           // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
           // 该参数数值不接受小数点，如1.5h，可转换为90m。
           orderInfo += "&it_b_pay=\"30m\"";

           // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
           // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

           // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
           orderInfo += "&return_url=\"m.alipay.com\"";

           // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
           // orderInfo += "&paymethod=\"expressGateway\"";

           return orderInfo;
       }
   ```

7. 添加回调方法.(支付成功,失败等等.)
   ```java
   @SuppressLint("HandlerLeak")
       private Handler mHandler = new Handler() {
           @SuppressWarnings("unused")
           public void handleMessage(Message msg) {
               switch (msg.what) {
                   case SDK_PAY_FLAG: {
                       PayResult payResult = new PayResult((String) msg.obj);
                       /**
                        * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                        * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                        * docType=1) 建议商户依赖异步通知
                        * 上述步骤的意思的,不管支付结果成功不成功,都需要向服务端请求真正的结果.假如服务端得到了
                        * 支付宝服务端异步回调结果,就直接通知客户端结果.如果没有,则根据客户端请求的签名的验证信息
                        * 与公钥进行匹配,成功则使用客户端的结果并通知客户端
                        */
                       String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                       String resultStatus = payResult.getResultStatus();
                       // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                       if (TextUtils.equals(resultStatus, "9000")) {
                           //支付成功,调用服务端接口
                       } else {
                          //支付失败,调用服务端接口
                       }
                       break;
                   }
                   default:
                       break;
               }
           }

           ;
       };
   ```

