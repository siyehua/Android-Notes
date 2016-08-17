##Android 资源文件夹介绍(译)

###问题
本次文章主要要探讨的内容是:Android 系统是如何引用布局里的资源?

要明白上提出的问题,并写好屏幕适配,我们需要明白了解以下几个问题.

* Android 的资源目录到底有什么,除了我们Android给我们自动建立的:drawable,layout,values.还有其他的什么目录?

* 这些目录 drawable-400*800,drawable-400x800,drawable-v3的后缀意义是什么?

* Android 系统查找资源文件/布局的规则(优先级)是什么?

###答案
其实这些问题都可以通过我们的SDK中找到.英文比较好的同学可以直接看SDk目录下的

/docs/guide/topics/resources/providing-resources.html

这个说明文档提供了比较详细的说明和介绍.想要了解更多的东西都可以通过这个文档.

###资源目录介绍

除了我们Android给我们自动建立的:drawable,layout,values,

在sdk文档说明中,可以识别的目录一共有以下几种:

####animator 存放的是property animations xml文件
####anim 动画xml文件.property animatons的xml也可以存放在anim这里.但animator的目录下的xml有较高的优先权.
####color 颜色xml文件. (注意如colors的区别,colors.xml是一个文件,存放在values目录下,是存放一些具体的颜色值.而这个color存放是的一些selector)

####drawable

Bitmap files (.png, .9.png, .jpg, .gif) or XML files that are compiled into the following drawable resource subtypes:

* Bitmap files .png格式图片
* Nine-Patches (re-sizable bitmaps ) .9.png格式图片
* State lists 状态列表的xml
* Shapes  shape的xml
* Animation drawables 动画图片,就是gif
* Other drawables 其他图片

####layout 布局

####menu 菜单(在Menu/ActionBar/ToolsBar中引用该布局)

####raw 二进制资源(在这里存放的资源会提供id,在assets的不会提供id)

####values

* arrays.xml for resource arrays (typed arrays). 数组
* colors.xml for color values 颜色值
* dimens.xml for dimension values. dp.sp
* strings.xml for string values. 字符串
* styles.xml for styles. 风格
* xml/ xml文件

###资源目录后缀规则
在android自己生成的目录中,我们可以看到以下几个目录
![Image](/_001.png)
![Image](/_002.png)

这些目录的的含义可以参考sdk文档的第二张表.分别有:
####MCC and MNC(大概意思是说通信方式.例如移动和联通的通信方式,3G,4G使用的技术也是不一样的)

####Language and region(地区和语言)
* en
* fr
* en-rUS
* fr-rFR
* fr-rCA

The language is defined by a two-letter ISO 639-1 language code, optionally followed by a two letter ISO 3166-1-alpha-2 region code (preceded by lowercase "r")
其中r的意思就是说使用的是另外一套语言标准.

####Layout Direction(Layout方向,是从左到右,还是从右到左,例如阿拉伯使用的是从从右到左的)
eg:
```
res/
    layout/   
        main.xml  (Default layout)
    layout-ar/  
        main.xml  (Specific layout for Arabic)
    layout-ldrtl/  
        main.xml  (Any "right-to-left" language, except
                  for Arabic, because the "ar" language qualifier
                  has a higher precedence.)
```
其中layout-ar专门指的是阿拉伯地区,layout-ldrtl除了阿拉伯以外的其他从右到左的阅读方式的地区,因为阿拉伯地区的ar标志拥有更高的优先权


####smallestWidth(最小屏幕宽度)
```
sw320dp
sw600dp
sw720dp
```
Ps:屏幕方向的改变不会影响它的最小宽度.

####Available width(可用宽度,宽度和高度可能会因为屏幕的旋转而变化,这里值的是当前状态下屏幕的宽度)
```
w720dp
w1024dp
```

####Available height(可用高度,同上)
```
h720dp
h1024dp
```

####Screen size(屏幕尺寸)分为以下几种:

* small: 320x426 dp,QVGA
* normal: 320x470 dp,HVGA
* large: 480x640 dp,VGA
* xlarge:  720x960 dp, HVGA

####Screen aspect (与上面一个类似) 分下面两种:

* long: Long screens, such as WQVGA, WVGA, FWVGA
* notlong: Not long screens, such as QVGA, HVGA, and VGA

####UI mode (UI 模式)就是说看你什么设备,是tv,还是可穿戴设备,还是车载,还是桌面系统等.
* car
* desk
* television
* appliance
* watch

####Night mode (夜晚模式)分白天一个布局,晚上一个布局
* night: Night time
* notnight: Day time

####Screen pixel density (dpi)(屏幕密度)
* ldpi 120dpi
* mdpi 160dpi
* hdpi 240dpi
* xhdpi 320dpi API8
* xxhdpi 480dpi API16
* xxxhdpi 640dpi API18
* nodpi 存放你不想放大/缩小的图片
* tvdpi 介于mdpi与hdpi之间.例如tv之类的设备

####Touchscreen type  (屏幕触摸类型)
* notouch:  没有触摸屏
* finger: 有触摸屏

####Keyboard availability  键盘可用性
* keysexposed 有硬件键盘
* keyshidden 有可隐藏的硬件键盘
* keyssoft 没有硬件键盘(软键盘)

####Primary text input method 主要的输入方法
* nokeys: 没有物理键盘
* qwerty: 有一个物理键盘,无论对于用户来说是否可见.
* 12key: 有一个12个键的键盘,无论对于用户来说是否可见.

####Navigation key availability 系统导航栏是否可用
* navexposed: 用户可以使用系统导航栏
* navhidden: 不可以使用

####Primary non-touch navigation method
* nonav: Device has no navigation facility other than using the touchscreen.
* dpad: Device has a directional-pad (d-pad) for navigation.
* trackball: Device has a trackball for navigation.
* wheel: Device has a directional wheel(s) for navigation (uncommon).

####Platform Version (API level) 平台版本,不能的API版本使用不同的布局.

例如5.0以上的系统可以使用卡片等主题.

* v3
* v4
* v7

####
![Image](/_001.png)
![Image](/_002.png)
现在再看这几个目录

* hdpi,ldpi分辨对应的是不同的dpi屏幕密度
* v11,v14对应的是不同的SDK版本,即不同的Android设备版本.
* w820dp,则指当前屏幕宽度大于820dp的时候引用该布局(例如用户旋转了屏幕,可能加载不同的布局)


###资源目录引用的优先级

例如有这么两个目录

values-en//英文

values-hdpi//高分辨率

假设现在有一台设置Anroid设备都符合这两个条件,那到底是引用了en目录下的资源.还是hdpi目录下的呢?

根据文档的后面例子介绍.有这么一个图:

![Image](/_003.png)

其中第二个步骤说:MCC first,then MNC,then language,and so on.

意思是MCC是第一的,然后接着是MNC,然后是language.

也就是是,资源引用的优先级是按照上面介绍的优先级.MCC排名第一,v平台排名最后.
所以假设一台设备统一满足英文,高分辨率这两个要求,那么会优先引用layout-en这个文件


###最后奉上分辨率表格

分辨率|密度|比率
---|---|---
1440 * 2560|xxxhdpi(640)|4
1080 * 1920|xxhdpi(480)|3
720 * 1280|xhdpi(320)|2
480 * 800|hdpi(240)|1.5
320 * 480|mdpi(160)|1
240 * 400|ldpi(120)|0.75