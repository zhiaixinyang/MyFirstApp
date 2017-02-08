关于
===

![](./test.mp4)
        本项目是我的第一个比较完善的项目。（内容如有侵权，通知必删）
        数据来源，Jsoup抓取；存储：LeanCloud平台
        整体：Retrofit+RxJava项目Gradle配置如下：
       
```Java
buildscript { 
    repositories { 
        jcenter()
        //这里是 LeanCloud 的包仓库
        maven {
            url "http://mvn.leancloud.cn/nexus/content/repositories/releases" 
        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.1'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'   
        }
}
task wrapper(type: Wrapper) {
    gradleVersion = "2.14.1"
}
allprojects {
    repositories {
        jcenter()
        //这里是 LeanCloud 的包仓库
        maven {
            url "http://mvn.leancloud.cn/nexus/content/repositories/releases"
        }
        maven { url "https://jitpack.io" } 
    }
}
```

*水平比较有限，见谅见谅...[我的个人博客](http://blog.csdn.net/wjzj000 )

*参考开源项目：https://github.com/codeestX/GeekNews 

![](./main.gif)
