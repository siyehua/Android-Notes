##支付宝APP支付

支付宝的SDK基本上是行业内几乎最复杂的了,可能是因为它要求的安全性最高吧.

开发说明文档的目录结构特别不清晰,很多东西明明记得看过,想去重新找了,又找不到在哪.

以前最集成趣拍的时候,就需要用到阿里百川里的一些东西,看了两天的开发文档,硬是没看懂.

最后通过趣拍技术团队自己发出了一个DEMO才调通的.

###接入步骤

支付宝支付详情过程可以看这个[集成过程](https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.1O9SpG&treeId=44&articleId=103921&docType=1)

支付的的接入步骤其实大概就分四个步骤,这个接入指南因为是通用的,所以很多步骤APP并不需要.

主要步骤如下:

1. 接入:注册支付宝账户,提交相应的资料并通过审核,入驻基本结束了.
2. 创建[自动型应用](https://openhome.alipay.com/platform/createApp.htm?enctraceid=_xtIFIWCn10r8klM38IZIFMDOi3W5jACHL5jm8OtjcU,)入驻了并不能马上使用,前面仅仅是提交的个人/企业 账号通过了,还需要创建移动应用.

   提交应用会涉及到一个叫应用签名的东西,这个签名是通用的,具体获得签名代码与之前一篇微信开发中的签名一直,具体[签名](/Other/WeChat Pay/README.md#app sign)

3. 设RAS(SHA1)公钥.

   具体设置公钥[文档](https://doc.open.alipay.com/doc2/detail?treeId=58&articleId=103242&docType=1).
   注意:推荐方式一的Windows方式无效,查看其脚本缺少了对应的文件的,这SDK做的,也是醉了.

   可以用MAC OSX或者生成方式二.

   文档后面说什么java,非java,说得很玄乎,根本就没理清楚他们之间的关系.在这里解释一下.(注意所有的公钥,密钥,开头和结尾有-----BEGIN RSA PRIVATE KEY-----,需要把开头和结尾字符串去掉.)
    * 每次生成三个文件,分别是public,private,private_pkcs8这个三个文件
    * public就是应用的公钥,就是直接设置到支付宝中的.填写的时候就填写这个.private就是密钥,企业自己保留
    * 在设置的时候,左下角会提示你验证自己的公钥正不正确.如果自己不会验证,有对应的工具下载
    * 自主验证时,如果用的语言是非java,则用普通的private文件的密钥验证即可
    * 使用支付宝提供的工具是使用java编写的,验证的时候需要输入private_pkcs8的密钥

   <font color=#FF00FF>需要填写的地方:(注意这个非常重要,如果没有填完全,直接导致你无法调用成功)</font>
    * 登录后,跳转到[管理中心](https://openhome.alipay.com/platform/manageApp.htm)

      ![管理中心图片](/Ohter/ZhiFuBao Pay/_003.png)

      我的应用中,包含两个应用,一个是基础应用,签约的时候自动创建的,且不可删除.
      一个是上述步骤中自主创建的应用.
    * 点击签约应用→选择应用环境→设置图中红框部分

      ![签约应用](/Ohter/ZhiFuBao Pay/_004.png)

    * 同样的方法设置自己创建的应用的RSA公钥.
    * 设置合作伙伴RAS公钥.

      ![合作伙伴](/Ohter/ZhiFuBao Pay/_004.png)
    * 设置无线产品RAS公钥.

      ![无线产品](/Ohter/ZhiFuBao Pay/_004.png)


4. 开通移动支付

    管理中心→点击对应的应用→选择左边栏中的'功能信息'→接口加密方式→移动支付这一行,点击申请开通,就完成了.



到此,已经完成了所有的设置,下一步则开始移动支付的具体流程

###移动支付

支付宝移动APP支付[文档](https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103563&docType=1)
这个文档写的清晰明了详细,给个赞.和上面的几个文档形成极其鲜明的对比.

