# 微信订阅号后台
## 技术栈
* mysql
* java
* springboot
* docker
* k8s
* maven
## 如何运行
1. 部署
    * 本地调试
        * 本地启动程序
            * 导入脚本：scripts/sql/init.sql
            * 修改数据库连接：application.properties spring.datasource.xxx的值
            * 配置com.okracode.wx.subscription.web.util.SignUtil类中的token值
            * 右键类 com.okracode.wx.subscription.web.WxSubscriptionWebApplication 运行(或Debug)
            * 访问localhost:8080，看到页面表示本地运行成功
        * 使用Ngrok做本地远程域名映射[目前微信已经封了此域名]
            * 下载安装[Ngrok](https://ngrok.com/download)
            * 解压：unzip ngrok.zip
            * 关联账户（获取token：https://dashboard.ngrok.com/get-started/setup）：./ngrok authtoken tokenxxx
            * 运行：./ngrok http 8080
            * 获取临时子域名：http://1c7bb669c550.ngrok.io -> http://localhost:8080
        * 使用localtunnel做本地远程域名映射[替代Ngrok，如果被封需要自己安装localtunnel的server]
            * npx localtunnel --port 8080
            * 获取临时子域名：your url is: https://wise-penguin-80.loca.lt
    * 服务器运行
        * 普通方式
            * 导入脚本：scripts/sql/init.sql
            * 修改数据库连接：application.properties spring.datasource.xxx的值
            * 打包：mvn clean package -Dmaven.test.skip=true -U
            * 拷贝wx-subscription-web/target/wx-subscription-web-1.0.0.jar到服务器
            * 运行java -jar wx-subscription-web/target/wx-subscription-web-1.0.0.jar
        * docker中运行
            * 导入脚本：scripts/sql/init.sql
            * 修改数据库连接：application.properties spring.datasource.xxx的值
            * 打包：mvn clean package spring-boot:repackage -Dmaven.test.skip=true -U
            * docker build -t nuptaxin/wx-subscription:v1.0.0 .
            * 定义wx-subscription-rs.yaml
                ```yaml
                 apiVersion: apps/v1
                 kind: ReplicaSet
                 metadata:
                   name: wx-subscription-rs
                 spec:
                   replicas: 1
                   selector:
                     matchLabels:
                       app: wx-subscription
                   template:
                     metadata:
                       labels:
                         app: wx-subscription
                     spec:
                       containers:
                       - name: wx-subscription
                         image: nuptaxin/wx-subscription:v1.0.0
                ```
            * 运行kubectl create -f wx-subscription-rs.yaml
            * 测试访问
                * 端口映射临时访问（需要开放对应targetPort的防火墙）：kubectl port-forward rs/wx-subscription-rs 8080:8080 --address 0.0.0.0
                * 访问站点：http://49.\*.\*.155:8080
            * 定义wx-subscription-svc.yaml
                ```yaml
                    apiVersion: v1
                    kind: Service
                    metadata:
                      name: wx-subscription-svc
                    spec:
                      ports:
                      - port: 80
                        targetPort: 8080
                      selector:
                        app: wx-subscription
                ```
            * 运行kubectl create -f wx-subscription-svc.yaml
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
                            name: wx-subscription-svc
                            port:
                              number: 80
                ```
                * 更新ingress
                   `kubectl apply -f okra-code-ing.yaml`
                * 使用ip或域名/wechat.do访问测试
2. 日志
    * 生成的日志在logs目录
    * docker中查看日志
        * kubectl exec -it wx-subscription-xxx sh
        * cd /dist/logs
        * tail -f wx-subscription.log
3. 订阅号服务器配置
    * 进入订阅号后台，找到基本配置目录，点击服务器配置->修改配置
        * URL：填写站点部署的url/wechat.do（本地调试填写临时子域名）
        * Token：与SignUtil类中的token值一致
        * EncodingAESKey：随机生成
        * 消息加解密方式：明文模式
                  
