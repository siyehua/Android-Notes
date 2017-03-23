关于AS的Project与Android视图的不同,可以看我的博文

[Gradle与Project%20Structure详解](/Android%20Studio/Gradle%20And%20Project%20Structrue)

Android视图就是隐藏了部分不需要查看的视图,合并了部分文件
但是有些目录并不是真实的.而且在创建目录和文件时,无法定位真实的路径.
这个对于习惯用真实路径看的人来说确实不方便.

在Project视图中,可以隐藏不关心的文件或目录,来达到与Android视图类似的效果,更佳符合我们的视图习惯
隐藏没用到的文件，比如 IDEA的项目配置文件 （*.iml和*.idea）可以达到我们需要的效果
打开 Settings-File Types，加入需要隐藏的文件后缀。
在最后有这么一句话:Ignore files and floders(隐藏文件和目录)我们只要在后面添加想要隐藏的文件和目录就可以了

![Image](/Android%20Studio/Make%20Project%20View/Img/_001.png)

这里推荐这么添加:其实就是上一遍博文中白色标注的目录和文件(注意是在后面追加)
```java
.idea;*.iml;gradle;gradlew;gradlew.bat;gradle.properties;local.properties;
```
看效果图

![Image](/Android%20Studio/Make%20Project%20View/Img/_002.png)

和Ecplise视图差不多啦

###
更新

修复隐藏目录后R文件找不到情况.隐藏的目录不要包括Build目录即可
