#
ConstraintLayout-进阶的RelativeLayout


近日伴随着Android Studio 2.2的正式推送,最大的亮点是布局的蓝图模式以及与之配套的ConstraintLayout关注度一下子高了起来.

ConstraintLayout,翻译过来,可以叫约束布局,其子view就是通过一个个属性的约束,来决定自己的位置,大小,而传统的RelativeLayout也类似,所以可以看成是RelativeLayout的一种进化版版本,属性布局用法相对RelativeLayout来说较为复杂,但是当你熟悉之后你会爱上它的.

众所周知,Android APP的布局复杂度会极大的影响程序的流畅度,传统的ViewGroup用的最多的就是RelativeLayout与LineaLayout.

一般能用RelativeLayout替换LineaLayout就替换,因为LinearLayout虽然简单,但是会加深层级.

####
而有时候却不得不使用LinearLayout,在于LinearLayout有一个layout_weight属性,可以设置LinearLayout的ChildView按照一定的比例布局,这是RelativeLayout做不到的.


####
ConstraintLayout的其他的属性和用法基本与RelativeLayout一致,如果对RelativeLayout比较熟悉的童鞋很容易上手,而ConstraintLayout最大的优点便是可以添加比例的控制.



##
准备工作

 * 下载Android Studio2.2
 * 新建一个项目,打开MainActivity布局,并切换到Design(左下角)设计视图
 * 选中layout根目录,右键,Convert转换,将layout转换为ConstraintLayout为根目录的layout
&nbsp;	![转换](/Android/ConstraintLayout/_001.jpg)
 * 初次转换会提示你没有	ConstraintLayout,问你是否下载,直接选择下载即可,等下载好了会自动转换
 * 也可以直接在Module的gralde配置里添加

 	```java
 	dependencies {
    	compile 'com.android.support.constraint:constraint-layout:1.0.0-alpha8'
		}
 	```

##
蓝图介绍

![转换](/Android/ConstraintLayout/_002.jpg)

 1. 普通视图
 2. 蓝图
 3. 普通视图和蓝图同在
 4. 关闭/打开布局的约束
 5. 关闭/打开自动为View添加约束
 6. 添加导航线
 7. 查看布局的警告/错误

##
基本约束属性

前面说了, ConstraintLayout是RelativeLayout的进化版,如果RelativeLayout的子view没有设置任何基本属性,则置于左上角,ConstraintLayout同理.

ConstraintLayout基本属性的值可以是某个控件的id,也可以是"parent",简单的概括就是top,bottom,left,right,baseline.

以bottm为例

![转换](/Android/ConstraintLayout/_003.jpg)

####
约束属性值为id时对照表

约束属性|RelativeLayout属性
---|---
layout_constraintBaseline_toBaselineOf|layout_alignBaseline
layout_constraintBottom_toBottomOf|layout_alignBottom
layout_constraintBottom_toTopOf|layout_above
layout_constraintEnd_toEndOf|layout_alignEnd
layout_constraintEnd_toStartOf|layout_toStartOf
layout_constraintLeft_toLeftOf|layout_alignLeft
layout_constraintLeft_toRightOf|layout_toRightOf
layout_constraintRight_toRightOf|layout_alignRight
layout_constraintRight_toLeftOf|layout_toLeftOf
layout_constraintStart_toStartOf|layout_alignStart
layout_constraintStart_toEndOf|layout_toStartOf
layout_constraintTop_toTopOf|layout_alignTop
layout_constraintTop_toBottomOf|layout_below

####
约束属性值等于parent

当基本属性值为parent时,必须成对出现才有意义,即top与bottom,left与right,start与end成对.
在使用RelativeLayout的时候,假设子View设置了属性,则子View会置于底部

```xml
layout_alignParentBottom="true"
```

而对约束布局的子Viuew设置,是没有任何效果的,因为parent属性必须成对出现

```xml
layout_constraintBottom_toBottomOf="parent"
```

如果设置成对属性,会发现控件在设置的方向上居中了,也达不到置于底部的效果

![转换](/Android/ConstraintLayout/_004.jpg)

这时候前面介绍的功能派上用场了

```
ConstraintLayout最大的优点便是可以添加比例的控制
```

##
比例属性闪亮登场

```xml
layout_constraintHorizontal_bias="0.4"
layout_constraintVertical_bias="0.6"
```
这两个属性接受浮点型,是一个比例,数值在0-1之间,如不写这属性,默认为0.5

点击左下角切换的Design,再点击Button选中这个控件,右边栏会出现约束属性图

![转换](/Android/ConstraintLayout/_005.jpg)

简单介绍一下这个属性图

1. 箭头向里 表示控件的宽度/高度是适应内容的,弹簧状 表示控件是宽度/高度是具体数值
2. 上下两个0表示控件的上下margin是0
3. 小球50这个数值表示在垂直方向上,上下的比例是0.5:0.5

鼠标拖动小球,上下移动会发现数值,控件垂直方向上的位置都跟着改变.拖动到20,切换到代码,会发现代码新增了一个属性,此时控件的上下比例是:0.8:0.2

```xml
layout_constraintVertical_bias="0.8"
```

![转换](/Android/ConstraintLayout/_006.jpg)

假设要达到layout_alignParentBottom="true"的效果,只需要加上top,bottom约束,并设置layout_constraintVertical_bias="1.0"即可

![转换](/Android/ConstraintLayout/_007.jpg)


##
控件大小比例属性

```xml
app:layout_constraintDimensionRatio="1:2"
```
除了上述介绍的控件相对于parent位置的比例外,子view还可以控制自身的宽高比

上述例子是比把控件的宽高比设置为1:2

```
这个属性生效需要以下条件：left，right，top，bottom 四条边都需要约束，其中bottom的约束可以用baseline代替。 
宽/高有且只有一个是0dp.
如果都是0dp的则会不会生效,因为控件如果上下都有约束,并把高度设置为0,控件的高度会充满父控件,宽度同理,从而导致控件铺满整个父控件
```

##
creator迷之属性

```xml
app:layout_constraintBaseline_creator="12"
app:layout_constraintTop_creator="12"
app:layout_constraintBottom_creator="12"
app:layout_constraintLeft_creator="12"
app:layout_constraintRight_creator="12"
```
creator接受整型属性值,但该属性在 1.0.0-alpha8 版本暂时未有任何作用,查看源码发现,ConstraintLayout只是对改属性值进行了接受,但是没有做任何处理,相信在后续版本会新增其功能

```java
else if(attr != styleable.ConstraintLayout_Layout_layout_constraintLeft_creator && attr != styleable.ConstraintLayout_Layout_layout_constraintTop_creator && attr != styleable.ConstraintLayout_Layout_layout_constraintRight_creator && attr != styleable.ConstraintLayout_Layout_layout_constraintBottom_creator && attr != styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator) {
                    Log.w("ConstraintLayout", "Unknown attribute 0x" + Integer.toHexString(attr));
                }
```


##
Guideline导航线

```xml
app:layout_constraintGuide_begin="50dp"
app:layout_constraintGuide_end="50dp"
app:layout_constraintGuide_percent="50"
```

介绍导航线之前,先想一下,根据上面对ConstraintLayout的介绍,要你布置一个菜单控件,菜单栏每一项均分屏幕宽度,按照以前使用LinearLayout,会把每一个子view的weight设置为1,则均分了屏幕宽度

```xml
<LinearLayout>
	<View/>
	<View/>
	<View/>
</LinearLayout>
```

而使用ConstraintLayout则会发现前面介绍的比例,是相对parent来说的,如果要均分屏幕宽度,必须借助透明的分割线来布局

```xml
<ConstraintLayout>
	<View,右边约束分割线1/>
	<分割线1,距离左边33%/>
	<View,左边约束分割线1,右边约束分割线2/>
	<分割线2,距离左边66%/>
	<View,左边约束分割线2/>
</ConstraintLayout>
```

而这个分割线其实谷歌已经帮我们写好了,就是Guideline.蓝图介绍中,6对应的就是添加导航线.切换到蓝图模式,点击6,就可以添加一个水平/垂直的导航线

![转换](/Android/ConstraintLayout/_008.jpg)

添加垂直导航线

![转换](/Android/ConstraintLayout/_009.jpg)

####
注意:图中的导航线有一个向左的箭头模式,除了这个模式还有向右,百分比模式.如果导航线是水平的,还会有上下箭头.


####
点击小球即可切换模式


![转换](/Android/ConstraintLayout/_010.jpg)&nbsp;&nbsp;&nbsp;&nbsp;![转换](/Android/ConstraintLayout/_011.jpg)

###
Guideline属性对照表

属性|箭头
---|---
layout_constraintGuide_begin|左/上
layout_constraintGuide_end|右/下
layout_constraintGuide_percent|百分比

###
Guideline属性值

Guideline本身对于用户来说是不可见的,所以其宽高的值没有任何意义,也不起作用.
```xml
<android.support.constraint.Guideline
	android:id="@+id/guideline"
	android:layout_width="wrap_content"//无意义
	android:layout_height="511dp"//无意义
	android:orientation="vertical"//决定这是一条水平导航线还是垂直导航线
	app:layout_constraintGuide_percent="0.333"//决定导航线的位置
/>
```

此时再添加一条比例为0.666的导航线,即可三等分屏幕(点击图片可查看详细代码)

[![转换](/Android/ConstraintLayout/_012.jpg)](/Android/ConstraintLayout/persent.xml)

##
总结

ConstraintLayout完美的结合了RelativeLayout与LinearLayout的特点,减少了布局的层级,展现了其强大的功能.

除了上述介绍到的功能之外,ConstraintLayout的子view被设置为GONE后,依赖这个view的约束会自动继承这个子view的约束,从而保证布局不会错乱.而且还可以单独设置控件隐藏/显示时的外边距.

蓝图模式让view与view之间的依赖关系更加的清晰明了,还可以快速设置属性值.

有人认为拖动控件必将成为主流,而博主实际体验,当控件非常复杂的时候,非常多的约束也会让人眼花缭乱.

其实最好的方式还是用蓝图与代码结合的方式,在创建,快速设置依赖关系,以及设置属性的时候,使用蓝图模式,在细微的调整的时候,使用代码模式.

