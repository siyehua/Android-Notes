������GitHub�Լ�Android Studio���ռ�,ADT�Ѿ�Խ��Խ�ٿ�����ʹ����.���Ǿ���Android����һЩ�ǳ������
�ؼ�,���,��������,ʹ�÷�ʽ����
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
````
�������ʹ��Android Studio�Ŀ����߷ǳ������.����������ʷԭ��,�в��ֹ�˾/��Ŀ����ʹ����ADT���
��������,���ʱ��ͻ����һЩ��jar������,С���ֿ������������ߵ���,���ṩԶ�ֿ̲��ͬʱ,Ҳ��ʹ��
�ṩjar������.����ϧ����,�󲿷ֿ����߲�û���ṩjar�����ص�ַ.
��ʵcompile��仰д��gradle��,�Ѿ��Ѷ�Ӧ��jar�����ص��˱���.����ֻ��Ҫ֪��������.
����������˵˵�����github������ĵ���������ֻ�ṩcompile�����Ķ�Ӧjar����������.

������Ҫһ��AS,����Ͳ���˵��
���ҵ���Ӧ�ĵ�������Ŀ,�����������ĿΪ��
[Socket.IO](https://github.com/nkzawa/engine.io-client.java)
��������д�����һ�仰:
Gradle

Add it as a gradle dependency for Android Studio, in build.gradle:
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
```
��Gradle��һ��ֻ��Ҫ��д��仰�ͺ���,�󲿷ֵĵ�����������ô�ṩ��.

�۴�As,����һ��Android��Ŀ,����Ŀ���õ�Gradle������
![Image](/Android&nbsp;Studio/CompileToJar/001.png)
���ʱ��Gradle���������������Ŀ�����һ��Զ������,�����Ƿ�ͬ��,������ұߵ�ͬ������
![Image](/Android&nbsp;Studio/CompileToJar/002.png)


��ͷ��������,AS�����·����Գ�����,������ʧ���ͬ�������,���ʱ����ʵ���Ѿ��Ѷ�Ӧ��jar�����ص�������.����ֻҪ�ҵ���Ӧ��jar������

��Fiel��Settings������Gradle.ȷ�����ɲ鿴��Gradle��·��.����·��,���ҵĵ����д�
Ĭ�ϻ����ĸ�Ŀ¼.����Gradle�İ취��һ�»�������ͬ.�ҵ�CachesĿ¼,������Ǵ�Ż����Ŀ¼
gradleĿ¼��caches��modules-2��files-2.1(���ܰ汾gradle�����2.1���ܲ�һ��)�����뵽������Կ������ܶ����
����ͺ���Ϥ��,����ǰ��������Զ�ֿ̲���:
```java
compile 'com.github.nkzawa:engine.io-client:0.6.0'
```
�ҵ����com.github.nkzawa,���ȥ�ῴ��һ��engine.io-clientĿ¼,���Ŀ¼,�ٽ�ȥ�ῴ��һ��0.6.0,�����jar���İ汾
���ȥһ���������ļ���,����һ�����pom.xml.��������Ŀ¼,����һ�����Դ����,����һ�����jar��
����,���Ǿ��ҵ���������Ҫ��jar��.ֻҪ��jar���Ƴ����ŵ�Ecplise�оͿ���ʹ����
�����Ҫ�޸�jar��,�����԰�Դ���jar���Ƴ���
