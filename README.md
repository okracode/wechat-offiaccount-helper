# 微信订阅号后台
## 技术栈
## 如何运行
1. 部署
    * 本地调试
        * 本地启动程序
            * 配置ren.ashin.wechat.intfc.util.SignUtil类中的token值
            * 右键类 ren.ashin.wechat.intfc.WeChatServer 运行(或Debug)
            * 访问localhost:8080，看到页面显示Hello World表示本地运行成功
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
            * 打包：mvn clean package -Dmaven.test.skip=true -U
            * 拷贝target文件夹到服务器
            * 运行./wechat-intfc.sh
        * docker中运行
            * 打包：mvn clean package -Dmaven.test.skip=true -U
            * docker build -t nuptaxin/wechat-intfc:v1.0.0 .
            * 定义wechat-intfc.yaml
                ```yaml
                 apiVersion: apps/v1
                 kind: ReplicaSet
                 metadata:
                   name: wechat-intfc
                 spec:
                   replicas: 1
                   selector:
                     matchLabels:
                       app: wechat-intfc
                   template:
                     metadata:
                       labels:
                         app: wechat-intfc
                     spec:
                       containers:
                       - name: wechat-intfc
                         image: nuptaxin/wechat-intfc:v1.0.0
                ```
2. 日志
    * 生成的日志在logs目录
3. 订阅号服务器配置
    * 进入订阅号后台，找到基本配置目录，点击服务器配置->修改配置
        * URL：填写站点部署的url/wechat.do（本地调试填写临时子域名）
        * Token：与SignUtil类中的token值一致
        * EncodingAESKey：随机生成
        * 消息加解密方式：明文模式
                  
