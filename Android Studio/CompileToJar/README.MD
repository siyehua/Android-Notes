伴随着GitHub以及Android Studio的普及,ADT已经越来越少开发者使用了.我们经常Android开发一些非常优秀的
控件,插件,第三方库,使用方式都是
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
````
这个对于使用Android Studio的开发者非常方便的.但是由于历史原因,有部分公司/项目还在使用这ADT这个
开发工具,这个时候就会出现一些求jar包的人,小部分开发第三方工具的人,在提供远程仓库的同时,也会使用
提供jar包下载.但可惜的是,大部分开发者并没有提供jar的下载地址.
其实compile这句话写在gradle中,已经把对应的jar包下载到了本地.我们只需要知道它即可.
下面来具体说说如果把github中优秀的第三方但是只提供compile依赖的对应jar包下载下来.

①先需要一个AS,这个就不多说了
②找到对应的第三方项目,这里以这个项目为例
[Socket.IO](https://github.com/nkzawa/engine.io-client.java)
其中作者写到这个一句话:
Gradle

Add it as a gradle dependency for Android Studio, in build.gradle:
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
```
在Gradle是一种只需要填写这句话就好了,大部分的第三方都是这么提供的.

③打开As,创建一个Android项目,在项目配置的Gradle中输入
![Image](/Android Studio/CompileToJar/001.png)
这个时候Gradle最上面会告诉你的项目添加了一个远程依赖,问你是否同步,点击最右边的同步即可
![Image](/Android Studio/CompileToJar/002.png)


④头部过程中,AS的最下方会显出进度,进度消失后就同步完成了,这个时候其实就已经把对应的jar包下载到本地了.我们只要找到对应的jar包即可

⑤Fiel→Settings→输入Gradle.确定→可查看到Gradle的路径.复制路径,在我的电脑中打开
默认会有四个目录.可能Gradle的办法不一致会有所不同.找到Caches目录,这个就是存放缓存的目录
gradle目录→caches→modules-2→files-2.1(不能版本gradle后面的2.1可能不一样)→进入到这个可以看房到很多包名
这个就很熟悉啦,我们前面依赖的远程仓库是:
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
```
找到这个com.github.nkzawa,点进去会看到一个engine.io-client目录,这个目录,再进去会看到一个0.6.0,这个是jar包的版本
点进去一般有三个文件夹,其中一个存放pom.xml.另外两个目录,其中一个存放源代码,另外一个存放jar包
到此,我们就找到了我们需要的jar包.只要把jar复制出来放到Ecplise中就可以使用了
如果需要修改jar包,还可以把源码的jar复制出来
