# coolq-java
基于websocket的java酷q插件

本插件依赖[coolq-http-api](https://richardchien.github.io/coolq-http-api/3.4/#/)插件

## 配置文件
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
## 使用
下载qqbot.jar(或下载源码并自己编译)，启动http-api插件的websocket功能，并填写config.json，执行java -jar qqbot.jar。
