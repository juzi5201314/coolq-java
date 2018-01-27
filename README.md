# coolq-java
基于websocket的java酷q插件

本插件依赖[coolq-http-api](https://github.com/richardchien/coolq-http-api)插件，[获取并使用此插件](https://richardchien.github.io/coolq-http-api/3.4/#/)

[![Build Status](https://travis-ci.org/juzi5201314/coolq-java.svg?branch=master)](https://travis-ci.org/juzi5201314/coolq-java)

适用于：
* 酷q与本程序运行在同一台机器上的或酷q机器有公网ip的（运行程序的机器可有可无）
* 不懂java但是想使用本程序实现的模块的功能的
* 想用java写机器人功能但是懒得自己写基础代码的（当成sdk用2333）

# 运行环境
* java jdk 1.8  
* 在windows 7 与centos6.9上测试通过

# 配置文件
格式根据config.json文件，配置项说明:
* websocket_clinet - 开启websocket客户端功能(默认:true)
* websocket_listen - websocket监听url，接收事件上报使用的
* websocket_api - websocket api url，调用api使用的
* headers - 连接websocket时的请求头，例如: 

…   

"headers":{  
"token":"Token xxxxx",  
"request":"xxx1,xxx2"  
},  

…  

* debug - 开启后会在后台输出一下调试信息，并且开启后会把一些不致命的报错输出。
* model - 模块，如果你会java，那么你可以自己编写一个或者使用其他开发者或者我提供的一些功能模块。格式如:  

…  

"model":{  
"模块名":"模块主类",
"TestModel":"net.soeur.test.Main"
},  

…
  
# 使用
* 请确保机器安装了java 8
 1，在酷q下载并启动http-api插件。
 2，启用http-api插件的websocket功能并填写url。
 3，在[这里](https://github.com/juzi5201314/coolq-java/releases)下载最新版的qqbot.jar并放入随便一个文件夹（或直接用wget下载）
 4，配置config.json
 4，命令行cd到存放qqbot.jar的目录
 5，执行 java -jar qqbot.jar

# 编译jar
