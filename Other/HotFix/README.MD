###背景
当一个App发布之后，突然发现了一个严重bug需要进行紧急修复，这时候公司各方就会忙得焦头烂额：重新打包App、测试、向各个应用市场和渠道换包、提示用户升级、用户下载、覆盖安装。有时候仅仅是为了修改了一行代码，也要付出巨大的成本进行换包和重新发布。

这时候就提出一个问题：有没有办法以补丁的方式动态修复紧急Bug，不再需要重新发布App，不再需要用户重新下载，覆盖安装？

答案是：可以的

###方案
目前国内公布的方案有来自阿里巴巴[AndFix](https://github.com/alibaba/AndFix)与QQ空间的[HotFix](https://zhuanlan.zhihu.com/p/20308548)


###Android插件化
![Image](/Other/HotFix/_001.png)

插件化分热部署,动态加载资源,模块化开发.

####热部署
热部署就是通常说的热修复.当发现紧急问题,紧急BUG时,需要自动升级用户的版本修复问题.

####动态加载资源
资源一般是打包在APK时,有时候需要资源过大.不能全部打包进APK包里.例如动态换肤等功能.

####模块化开发
DroidPlugin 是360手机助手在Android系统上实现了一种新的插件机制:它可以在无需安装、修改的情况下运行APK文件,此机制对改进大型APP的架构，实现多团队协作开发具有一定的好处。

典型案例:360开源的[DroidPlugin](https://github.com/jqk6/DroidPlugin)

###原理
[QQ空间](https://zhuanlan.zhihu.com/p/20308548)
[Android 热修复](http://kymjs.com/code/2016/05/08/01)

在 Java 中，要加载一个类需要用到ClassLoader。

Android 中有三个 ClassLoader, 分别为URLClassLoader、PathClassLoader、DexClassLoader。

####URLClassLoader

只能用于加载jar文件，但是由于 dalvik 不能直接识别jar，所以在 Android 中无法使用这个加载器。

####PathClassLoader
它只能加载已经安装的apk。因为 PathClassLoader 只会去读取 /data/dalvik-cache 目录下的 dex 文件。
例如我们安装一个包名为com.hujiang.xxx的 apk,那么当 apk 安装过程中，就会在/data/dalvik-cache目录下生产一个名为data@app@com.hujiang.xxx-1.apk@classes.dex的 ODEX 文件。
在使用 PathClassLoader 加载 apk 时，它就会去这个文件夹中找相应的 ODEX 文件，如果 apk 没有安装，自然会报ClassNotFoundException。
####DexClassLoader

最理想的加载器。它的构造函数包含四个参数，分别为：
   * dexPath,指目标类所在的APK或jar文件的路径.类装载器将从该路径中寻找指定的目标类,该类必须是APK或jar的全路径.
   如果要包含多个路径,路径之间必须使用特定的分割符分隔,特定的分割符可以使用System.getProperty(“path.separtor”)获得.
   * dexOutputDir,由于dex文件被包含在APK或者Jar文件中,因此在装载目标类之前需要先从APK或Jar文件中解压出dex文件,该参数就是制定解压出的dex 文件存放的路径.
   在Android系统中,一个应用程序一般对应一个Linux用户id,应用程序仅对属于自己的数据目录路径有写的权限,因此,该参数可以使用该程序的数据路径.
   * libPath,指目标类中所使用的C/C++库存放的路径
   * classload,是指该装载器的父装载器,一般为当前执行类的装载器

###Android端代码

Android端主要需要时间的功能,就是改变dexpath路径.让类装载器优先取出来的jar包不是APK中的jar包
而是我们需要修复jar包.


###热修复开源项目
项目|问题
---|---
[Nuwa](https://github.com/jasonross/Nuwa)|Gradle1.40 里Transform API无法打包
[HotFix](https://github.com/dodola/HotFix)|自动化不足,只能修复简单的类,且操作复杂
[RocooFix](https://github.com/dodola/RocooFix)|正在开发,尚未完善

###步骤
 1. 客户端依赖上述中任意一个项目
 2. 服务器开发对应的热修复接口
 3. 客户端增加对应的热修复代码
 4. 把需要修复的java文件编译成class文件
 5. 将class文件打入一个jar包中 jar cvf path.jar *
 6. 将jar包转换成dex的jar包 dx --dex --output=path_dex.jar path.jar
 7. 将最终jar发给服务器,配置到对应的地方
 8. 客户端下载好对应的文件.
 9. APP重启修复

###总结

 * 热修复目前来说比较适合修复紧急BUG,改动较小,一个或仅仅少数几个类的情况下,而且开发时间和成本比较高.
 * 如遇到重大改动可使用强制升级方式
 * 热修复还是会产生新的问题:增加包的大小,降低运行速度,产生新的兼容性问题
 * 热修复等于避过应用市场的普通升级方式,谷歌会逐步修复该功能.
