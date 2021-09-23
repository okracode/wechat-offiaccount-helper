# 微信订阅号后台

## 技术栈

* mysql5.6.5+(mariadb10.1+)
* java
* springboot
* docker
* k8s
* maven

## 参考文档
* wx-tools
    * [源码](https://github.com/nuptaxin/wx-tools)
    * [教程](https://www.w3cschool.cn/wxtools/)
* 微信公众号
    * [教程](https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Overview.html)
* localtunnel
    * [客户端源码](https://github.com/localtunnel/localtunnel)
    * [服务端源码](https://github.com/localtunnel/server)

## 如何部署

1. 本地调试
    * 本地启动程序
        * 导入脚本
            * 首次运行：scripts/sql/init.sql
            * 升级：scripts/sql/vx.x.x~vx.x.x
        * 修改数据库连接：application.properties spring.datasource.xxx的值
        * 配置wx.properties中的wx.token值
        * 右键类 com.okracode.wx.subscription.web.WxSubscriptionWebApplication 运行(或Debug)
        * 访问localhost:8080，看到Hello world!页面表示本地运行成功
    * 使用Ngrok做本地远程域名映射[目前微信已经封了此域名]
        * 下载安装[Ngrok](https://ngrok.com/download)
        * 解压：unzip ngrok.zip
        * 关联账户（获取token：https://dashboard.ngrok.com/get-started/setup）：./ngrok authtoken tokenxxx
        * 运行：./ngrok http 8080
        * 获取临时子域名：http://1c7bb669c550.ngrok.io -> http://localhost:8080
    * 使用localtunnel做本地远程域名映射[替代Ngrok，如果被封需要自己安装localtunnel的server]
        * 安装nodejs
        * npx localtunnel --port 8080
        * 获取临时子域名：your url is: https://wise-penguin-80.loca.lt
2. 服务器运行
    * 普通方式
        * 导入脚本
            * 首次运行：scripts/sql/init.sql
            * 升级：scripts/sql/vx.x.x~vx.x.x
        * 打包：mvn clean package -U
        * 拷贝wechat-offiaccount-helper-web/target/wechat-offiaccount-helper.jar, wechat-offiaccount-helper-web/target/config文件夹到服务器（config文件夹必须和jar在同一目录）
        * 修改config文件中的数据库连接：application.properties spring.datasource.xxx的值
        * 进入jar和config所在目录：cd wechat-offiaccount-helper-web/target，运行java -jar wechat-offiaccount-helper.jar
    * docker中运行
        * 导入脚本
            * 首次运行：scripts/sql/init.sql
            * 升级：scripts/sql/vx.x.x~vx.x.x
        * 打包：mvn clean package -U
        * 拷贝wechat-offiaccount-helper-web/target/wechat-offiaccount-helper.jar, wechat-offiaccount-helper-web/target/config文件夹到服务器（config文件夹必须和jar在同一目录）
        * 修改config文件中的数据库连接：application.properties spring.datasource.xxx的值
        * 进入jar和config所在目录：cd wechat-offiaccount-helper-web/target, 构建docker镜像：docker build -t nuptaxin/wechat-offiaccount-helper:v1.5.1 .
        * 定义wechat-offiaccount-helper-rs.yaml
            ```yaml
             apiVersion: apps/v1
             kind: ReplicaSet
             metadata:
               name: wechat-offiaccount-helper-rs
             spec:
               replicas: 1
               selector:
                 matchLabels:
                   app: wechat-offiaccount-helper
               template:
                 metadata:
                   labels:
                     app: wechat-offiaccount-helper
                 spec:
                   containers:
                   - name: wechat-offiaccount-helper
                     image: nuptaxin/wechat-offiaccount-helper:v1.5.1
            ```
        * kubectl apply -f wechat-offiaccount-helper-rs.yaml
        * 查看pod使用的image版本号：kubectl describe po wechat-offiaccount-helper-rs-xxxxx
        * 测试访问
            * 端口映射临时访问（需要开放对应targetPort的防火墙）：kubectl port-forward rs/wechat-offiaccount-helper-rs 8080:8080 --address 0.0.0.0
            * 访问站点：http://49.\*.\*.155:8080
        * 定义wechat-offiaccount-helper-svc.yaml
            ```yaml
                apiVersion: v1
                kind: Service
                metadata:
                  name: wechat-offiaccount-helper-svc
                spec:
                  ports:
                  - port: 80
                    targetPort: 8080
                  selector:
                    app: wechat-offiaccount-helper
            ```
        * 运行kubectl create -f wechat-offiaccount-helper-svc.yaml
        * ingress添加url映射
            ```yaml
            apiVersion: networking.k8s.io/v1
            kind: Ingress
            metadata:
              name: okracode-ing
            spec:
              rules:
              - host: home.okracode.com
                http:
                  paths:
                    - path: /
                      pathType: Prefix
                      backend:
                        service:
                          name: okra-home-svc
                          port:
                            number: 80
              - http:
                  paths:
                  - path: /
                    pathType: Prefix
                    backend:
                      service:
                        name: wechat-offiaccount-helper-svc
                        port:
                          number: 80
            ```
            * 更新ingress
              `kubectl apply -f okra-code-ing.yaml`
            * 使用ip或域名/wechat.do访问测试
## 日志查看
* 生成的日志在logs目录
* docker中查看日志
    * kubectl exec -it wechat-offiaccount-helper-rs-xxx sh
    * cd /logs
    * tail -f wechat-offiaccount-helper.log
## 订阅号配置
* 进入订阅号后台，找到基本配置目录，点击服务器配置->修改配置
    * URL：填写站点部署的url/wechat.do（本地调试填写临时子域名）
    * Token：与SignUtil类中的token值一致
    * EncodingAESKey：随机生成
    * 消息加解密方式：明文模式
## 版本号升级
* 使用mvn命令进行升级
    * 升级版本号
      > mvn versions:set -DgenerateBackupPoms=false -DnewVersion=1.5.1
* 初始化sql版本升级
    * 如果是第一次安装使用，导入[init.sql](scripts/sql/init.sql)即可
    * 如果是从已有版本升级到最新版本[注：不支持跨版本平滑升级]，由版本号从小到大依次执行[upgrade.sql](scripts/sql)
    * 回滚升级，由版本号从大到小依次执行[rollback.sql](scripts/sql)
* 版本号升级逻辑遵循
    > https://semver.org/lang/zh-CN/
* 更新RoadMap和UpgradeLog
* 发布当前版本后，在github上Draft a new release
    * tag名称为UpgradeLog版本号
    * 标题为UpgradeLog中的三级标题
    * 描述为UpgradeLog三级标题下的列表
* 同步gitee
    > https://gitee.com/nuptaxin/wechat-offiaccount-helper
* 修改docker命令与k8s脚本版本号
                  
