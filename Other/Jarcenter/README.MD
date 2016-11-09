#发布library到jcenter

1. 创建好libiary
2. 到 https://bintray.com 上注册,最好使用github授权登录.ps:邮箱不支持QQ邮箱
3. 点击网页右上角的Your Profile

    ![Image](/Other/Jarcenter/_005.png)
4. 点击后,需要关注下面几个选项

    ![Image](/Other/Jarcenter/_006.png)

    * organizations是你加入的组织,与github一样
    * accounts是账号关联,假设不是通过github账号授权登录,这里需要关联github账号
    * apikey授权码,拿到一串授权码用于本地提交账号
5. 添加一个maven仓库

    ![Image](/Other/Jarcenter/_001.png)
6. 输入名称maven,类型选择maven类型

    ![Image](/Other/Jarcenter/_002.png)
7. 创建好后需要添加一个package,也就是你想发布的仓库的名字,点击add添加

    ![Image](/Other/Jarcenter/_004.png)
8. 假设library名字为inputview,输入viewput,可以添加一个额外信息,例如libray的网站,对应的github项目地址等等

    ![Image](/Other/Jarcenter/003.png)

    可以看到624104244/maven/inputview为整个library的路径,其中624104244为组织名称或者用户名,maven就是上面创建的仓库名
    inputview则为library名称
9. 在项目的build.gradle文件中添加插件
    ```
    dependencies {
        classpath 'com.novoda:bintray-release:0.3.4'
    }
    ```
10. 在你需要上传的module的build.gradle中添加
    ```
    apply plugin: 'com.novoda.bintray-release'
    publish {
        //项目是组织的则用组织名,是你个人的用用户名
        //第8步中的624104244/maven/inputview
        //第一个域名即是这个userOrg
        userOrg = '624104244'//组织名称 or 用户名

        groupId = 'com.github.siyehua'//jar包名称,自定义
        artifactId = 'inputview'//package
        publishVersion = '1.0.1'//version
        desc = 'input text with a view'//描述
        website = 'https://github.com/siyehua'//网站
    }
    ```
11. 配置好之后,在Terminal中输入
    ```
    gradlew clean build bintrayUpload  -PbintrayUser=用户名  -PbintrayKey=apikey -PdryRun=false
    ```
    其中用户名即账号的用户名,apkey通过第四步就可拿到
12. 上传完毕后,点击右边的add jcenter即可发布,不过需要等待审核通过
##参考资料
[ Android 快速发布开源项目到jcenter](http://blog.csdn.net/lmj623565791/article/details/51148825)

##License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
