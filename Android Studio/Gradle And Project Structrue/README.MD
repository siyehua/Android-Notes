启动Project Structure的时候可以Structure看到分两个区域
一个是项目的总体设置区域.还有一个是对Module的分别设置.

#一.项目总体设置
##SDK Location中可以设置SDK和JDK路径.
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_001.jpg)
##Project中可以设置Gradle与Android工具的信息
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_002.jpg)
Gradle本身必须有一个仓库源,否则无法运行
什么是仓库源:仓库源就是Gralde对Android支持的一个仓库,对Android进行相应的配置,处理等信息.具体可以参考下面链接
http://www.gradle.org/docs/2.2/userguide/dependency_management.html#sec:repositories
默认的仓库源是jcenter.以前是mavenCentral.也可以填写自定义的地址.本地地址等.具体可以参考以下链接
http://www.gradle.org/docs/2.2/userguide/artifact_dependencies_tutorial.html#N1062

相关的配置信息可以在项目空间的build.gradle文件查看.打开项目空间的build.gradle文件.可以看到这么一个代码段:
(如何找到这个文件请查看我的上一遍博客:http://blog.csdn.net/siyehuazhilian/article/details/42123563)
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_003.jpg)
这里就显示该项目空间的仓库源是jcenter.修改上面的仓库源,这个代码也会跟着变化.


#二.Modlue区域
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_004.jpg)
Module一共包含五个选项卡
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_005.jpg)
分别是:属性,签名,多渠道,构建类型,依赖包.

##Properties属性
 * Compile Sdk Version:Android的编译版本
 * Build Tools Version: 构建工具版本
 * Library Reposiory当前的Module的仓库源.
这里可以定义一个地址,一个本地路径,或者是jcenter()和mavenCentral().
填写任何一个仓库,AS会自动下载对应的仓库,如果你随便填写一个,会提示你无法连接到该仓库.我这这里填写了mavenCentral()
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_006.jpg)
在打开对应的.Gradle配置文件
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_007.jpg)
可以看到有被修改的标志了.点进去以后
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_008.jpg)
发觉这个文件自动添加了一个仓库源.名字就是mavenCentral(),然后最下面的状态栏显示正在下载mavenCentral()仓库.
注意:由于项目空间已经定义了仓库源,所以Module的build.gradle文件默认是没有仓库源的,通常情况下也不需要填写
 * Ignore Assets Pattern:打包时候要忽略的文件
这将影响所有使用aapt的task.
这里我填写一个siyehua.
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_009.jpg)
在对应的文件中可以看到多了以下代码:
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_010.jpg)
 * Incremental Dex:增长DEX
Android打包工具（aapt）将DEX文件，资源文件以及AndroidManifest.xml文件（二进制格式）组合成一个应用程序包（APK）。应用程序包可以被发布到手机上运行
如果将它设置为ture,可以看到对应的文件中增加了如下代码
```java
dexOptions {
incremental true
}
```
 * Source Compatibility:资源版本 Target Compatibility:目标版本
两个都选了1.7,可以看到增加了如下代码:
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_011.jpg)

##Signing签名
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_012.jpg)
它们之间的关系是:
一个keystroe就是一个签名文件.打开它需要一个密码,就是keystore密码.Keystrore中可以存放多个key Alias,每一个key alias有一个单独的密码.

##Flavors(多样性,多渠道)
我们的应用经常需要多渠道打包.这个可以翻译为多渠道打包.
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_013.jpg)
 * Name:Flaveor的名字
 * Min Sdk Version:向下兼容的SDK版本
 * Application Id:包名
 * Proguard File:混淆文件
混淆文件,去除无用文件,并优化代码
 * Signig Config:签名文件名.
签名的文件信息在前一个选项卡Signing中设置.在这里选择
 * Target Sdk Version:目标SDK版本
 * Test Instrumentation Runner:Test Runner
 * Test Application Id:测试时的ID
关于Test的介绍请点击:http://www.apihome.cn/api/android/Instrumentation.html
 * Version Code:Module版本
 * Version Name:Module版本名称
设置了以后可以看到这个渠道对应的信息
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_014.jpg)

##Build Types构建类型
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_015.jpg)
 * Name:Build Type名称
 * Debugable:是否可以调试
 * Jin Debuggable:是否可以调试JNI
 * Signing Config:签名信息
 * Renderscript Debuggale:是否使用RenderScript
 * Renderscript Optim Level:Rendescript版本.
RenderScript相关介绍http://www.cnblogs.com/TerryBlog/archive/2012/03/02/2377251.html
 * Minify Enabled:是否去除无用的资源
 * Pseudo Locales Enaled:是否支持本地化整理
关于本地化整理介绍http://blog.csdn.net/kieven2008/article/details/6873610
 * Proguard File:混淆,去重文件路径
 * Application Id Suffix:App id后缀
 * Version Name Suffix:版本名称后缀
 * Zip Align Enabled:是否支持Zip Align
关于ZipAlign介绍:http://www.cnblogs.com/xirihanlin/archive/2010/04/12/1710164.html
tesst_001设置好以后的对应属性:
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_016.jpg)

##Dependencies(依赖)
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_017.jpg)
在这里添加添加jar包,library等.
其中
 * 表示添加用系统的library,例如v7,v4包.
 * 添加jar包(注意这个jar包必须在当前的Module)
 * 添加一个Module(这个工程是AS A Libray)
添加以后,点击这个,会出现一下的选项.
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_018.jpg)
分表是编译,提供,导出APK时,测试编译时,调试,发布时.
这里选择release complie.在对应的build.gradle中显示
![Image](https://github.com/siyehua/Adnroid-Notes/blob/master/Android%20Studio/Gradle%20And%20Project%20Structrue/Img/_019.jpg)

#总结:
看完了上面的所有信息,我们再来看看项目空间的build.gradle和其中的一个module的build.gradle文件的信息
##项目空间build.gradle文件:
```java
// Top-level build file where you can add configuration options common to all sub-projects/modules.  
/** 
 * 项目空间的编译环境 
 */  
buildscript {  
    /** 
     * 当前仓库源 
     */  
    repositories {  
        /** 
         * 对应Android Plugin Repository 
         */  
        jcenter()  
    }  
    dependencies {  
        /** 
         * 对应Android Plugin Version 
         */  
        classpath 'com.android.tools.build:gradle:1.0.0'  
  
        // NOTE: Do not place your application dependencies here; they belong  
        // in the individual module build.gradle files  
    }  
}  
  
/** 
 * 所有的仓库源 
 */  
allprojects {  
    /** 
     * 默认仓库源 
     */  
    repositories {  
        /** 
         * 对应Default Plugin Repository 
         */  
        jcenter()  
    }  
}  
```

##Module的build.gradle文件:
```java
/** 
 * 声明这是一个android应用 
 */  
apply plugin: 'com.android.application'  
/** 
 * Android设置 
 */  
android {  
    /** 
     * 签名:对应Signing选项 
     */  
    signingConfigs {  
        /** 
         * 签名名称:对应Singing→Name 
         */  
        test1 {  
            /** 
             * Key名字:对应Singing→Key Alias 
             */  
            keyAlias 'siyehua'  
            /** 
             * Key密码:对应Singing→Key Password 
             */  
            keyPassword 'siyehua'  
            /** 
             * KeyStroe路径:对应Singing→Stroe File 
             */  
            storeFile file('C:/Users/Administrator/Desktop/work/其他/tour.keystore')  
            /** 
             * KeyStore密码:对应Singing→Store Password 
             */  
            storePassword 'siyehua'  
        }  
    }  
    /** 
     * 编译版本:对应Properties→Compile Sdk Version 
     */  
    compileSdkVersion 21  
    /** 
     * 构建版本:对应Properties→Build Tools Version 
     */  
    buildToolsVersion "21.1.2"  
    /** 
     * 默认配置:对应Flavors→Name(这个是默认渠道) 
     */  
    defaultConfig {  
        /** 
         * 包名:对应Flavors→Application Id 
         */  
        applicationId "com.siyehua.android_siyehua"  
        /** 
         * 向下兼容版本:对应Flavors→Min Sdk Version 
         */  
        minSdkVersion 15  
        /** 
         * 目标版本:对应Flavors→Target Sdk Version 
         */  
        targetSdkVersion 21  
        /** 
         * app版本号:对应Flavors→Version Code 
         */  
        versionCode 1  
        /** 
         * app版本名称:对应Flavors→Version Name 
         */  
        versionName "1.0"  
        /** 
         * 签名信息:对应Flavors→Signig Config 
         */  
        signingConfig signingConfigs.test1  
        /** 
         * 测试APP 的id:对应Flavors→Test Application Id 
         */  
        testApplicationId 'siyehua'  
        /** 
         * 测试Runner:对应Flavors→Test Instrumentation Runner 
         */  
        testInstrumentationRunner 'siyehua'  
    }  
    /** 
     * 构建类型:对应Build Types 
     */  
    buildTypes {  
        /** 
         * 发布版本:对应Build Types→左边栏名字为release的构建版本 
         */  
        release {  
            minifyEnabled false  
            /** 
             * release的Proguard默认为Module下的proguard-rules.pro文件. 
             */  
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'  
        }  
        /** 
         * test版本:对应Build Types→左边栏名字为testbuild_001的构建版本 
         */  
        testbuild_001 {  
            /** 
             * 是否支持调试:对应testbuild_001→Debugable 
             */  
            debuggable true  
            /** 
             * 是否支持jin调试:对应testbuild_001→Jin Debuggable 
             */  
            jniDebuggable true  
            /** 
             * 签名:对应testbuild_001→Signing Config 
             */  
            signingConfig signingConfigs.test1  
            /** 
             * 是否使用RenderScript:对应testbuild_001→Renderscript Debuggale 
             */  
            renderscriptDebuggable true  
            /** 
             * App id后缀:对应testbuild_001→Application Id Suffix 
             */  
            applicationIdSuffix '_10087'  
            /** 
             * 版本名称后缀:对应testbuild_001→Version Name Suffix 
             */  
            versionNameSuffix '_10088'  
            /** 
             * 是否去除无用的资源:对应testbuild_001→Minify Enabled 
             */  
            minifyEnabled true  
            /** 
             * 是否支持本地化整理:对应testbuild_001→Pseudo Locales Enaled 
             */  
            pseudoLocalesEnabled true  
            /** 
             * 是否支持Zip Align:Zip Align Enabled 
             */  
            zipAlignEnabled true  
        }  
        /** 
         * 对应调试版本 
         */  
        debug {  
        }  
    }  
    /** 
     * 编译选项 
     */  
    compileOptions {  
        /** 
         * 资源版本:对应Properties→Source Compatibility 
         */  
        sourceCompatibility JavaVersion.VERSION_1_7  
        /** 
         * 目标版本:对应Properties→Target Compatibility 
         */  
        targetCompatibility JavaVersion.VERSION_1_7  
    }  
    /** 
     *多渠道打包:多渠道打包使用这个设置不同的配置 
     */  
    productFlavors {  
        /** 
         * 对应Flavors→中名字为flavor12的配置 
         */  
        flavor12 {  
        }  
    }  
    /** 
     * 打包时候要忽略的文件:对应Properties→Ignore Assets Pattern 
     */  
    aaptOptions {  
    }  
    /** 
     * DEX选项 
     */  
    dexOptions {  
        /** 
         * 增长DEX:对应Properties→Incremental Dex 
         */  
        incremental false  
    }  
}  
/** 
 * 依赖包 
 */  
dependencies {  
    /** 
     * 对应Dependencies下的依赖包 
     */  
    compile fileTree(dir: 'libs', include: ['*.jar'])  
    compile 'com.android.support:appcompat-v7:21.0.2'  
    releaseCompile files('libs/xmn_lpush_1.3.jar')  
}  
/** 
 * 当前Module的仓库源:对应Properties→Library Reposiory(由于这个项目已经使用了一个仓库源 
 * 除非有特殊要求,这里不建议写仓库源) 
 */  
repositories {  
    123  
}  
```


看完上面的的相信大家对Gradle,相应的配置,以及要怎么配置,在哪里配置.都有了一个大概的了解.
注意:直接修改gradle会直接修改对应的设置.
更多Gralde信息.比如说Flavors与Build Types之间有什么关系,对项目的构建会产生什么影响.
如何使用命令行配置等等问题
可以参考以下链接(这些链接也是我写这博文的参考基础.):
 * Gradle翻译:在这里可以看到每个选项的作用是什么,翻译的非常好
http://blog.csdn.net/qinxiandiqi/article/category/2394347
 * Gradle基础,学习之路大神最新教程
http://stormzhang.com/devtools/2014/12/18/android-studio-tutorial4/
 * Gralde官网
http://www.gradle.org/
对应的部分使用翻译
http://somefuture.iteye.com/category/300603
 * Gralde翻译以及使用
http://blog.csdn.net/maosidiaoxian?viewmode=contents


如有任何错误与不足,欢迎留言讨论.谢谢
