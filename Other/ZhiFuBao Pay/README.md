##支付宝APP支付

支付宝的SDK基本上是行业内几乎最复杂的了,可能是因为它要求的安全性最高吧.

以前最集成趣拍的时候,就需要用到阿里百川里的一些东西,看了两天的开发文档,硬是没看懂.

最后通过趣拍技术团队自己发出了一个DEMO才调通的.

###接入步骤

支付宝支付详情过程可以看这个[集成过程](https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.1O9SpG&treeId=44&articleId=103921&docType=1)

支付的的接入步骤其实大概就分四个步骤,这个接入指南因为是通用的,所以很多步骤APP并不需要.

主要步骤如下:
1. 接入:注册支付宝账户,提交相应的资料并通过审核,入驻基本结束了.
2. 创建[自动型应用](https://openhome.alipay.com/platform/createApp.htm?enctraceid=_xtIFIWCn10r8klM38IZIFMDOi3W5jACHL5jm8OtjcU,)入驻了并不能马上使用,前面仅仅是提交的个人/企业 账号通过了,还需要创建移动应用.

   提交应用会涉及到一个叫应用签名的东西,这个签名是通用的,具体获得签名代码与之前一篇微信开发中的签名一直,具体[签名](/Other/WeChat Pay/README.md#app sign)


支付宝移动APP支付[文档](https://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103563&docType=1)

1. 注册支付宝账户,提高相应的资料并通过审核.
2.