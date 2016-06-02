最近在使用NetWorkImageView的使用,发现如果图片的地址只要不正确,就会报空指针,情况如下图 
错误日志
![Image](/Android/NetWorkImagView_Pointer/_001.png)



这不是坑人的么,图片的地址不是空的,只是一个错误的地址,就报这个错误 
按照报错的提示,找到Volley框架中的Request类中的129行(使用jd-gui查看Volley的jar包) 
Volley错误地方
![Image](/Android/NetWorkImagView_Pointer/_002.png)


首先这个Url肯定不是空的,所以这个三目运算符执行的应该是后面那个
```JAVA
Uri.parse(url).getHost().hashCode()
```

这几个方法是哪个报空指针的呢,测试一下

```JAVA
Log.e("siyehua1", Uri.parse("2312").toString() + "");
Log.e("siyehua2", Uri.parse("2312").getHost() + "");
Log.e("siyehua3", Uri.parse("2312").getHost().hashCode() + "");
```

妥妥的运行到第二个就空指针了,原来问题出现在这里

![Image](/Android/NetWorkImagView_Pointer/_003.png)

确定报错的方法.去API里看了一下getHost()这个方法

```JAVA
getHost()
public abstract String getHost()Gets the encoded host from the authority for this URI. For example, if the authority is "bob@google.com", this method will return "google.com". 
return：
the host for this URI or null if not present//如果不存在就返回空
```

至此,找到了罪魁祸首. 
分析:当给出一个错误的地址,NetWorkImageView应该加载一个错误的图片(当我们有设置错误的图片时) 
而不是报错. 
解决方法:判断能否得到这个URI的host,如果不能得到,就说不能加载图片,这个时候我们要调用加载失败方法
```JAVA
if (Uri.parse(url).getHost() == null) {// 如果不能得到图片链接对应的host
    netImageView.setDefaultImageResId(R.drawable.error);//设置图片位加载失败的图片
    return;
}
//////////////////////////////下面是正常使用NetWrok加载图片////////////////////////////
if (imageLoader == null)
    imageLoader = new ImageLoader(requestQueue, new BitmapCache());
netImageView.setDefaultImageResId(R.drawable.ic_launcher);// 设置加载中的图片
netImageView.setErrorImageResId(R.drawable.error);// 设置加载失败的图片
netImageView.setImageUrl(url, imageLoader);// 设置图片
```