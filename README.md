# 微信订阅号后台
## 技术栈
## 如何运行
1. 部署
    * 本地调试
        * 使用Ngrok做本地远程域名映射
        * 下载安装[Ngrok](https://ngrok.com/download)
        * 解压：unzip ngrok.zip
        * 关联账户（获取token：https://dashboard.ngrok.com/get-started/setup）：./ngrok authtoken tokenxxx
        * 运行：./ngrok http 8080
        * 获取临时子域名：http://1c7bb669c550.ngrok.io -> http://localhost:8080
        * 配置ren.ashin.wechat.intfc.util.SignUtil类中的token值
        * 右键类 ren.ashin.wechat.intfc.WeChatServer 运行(或Debug)
        * 访问localhost:8080，看到页面显示Hello World表示本地运行成功
    * 服务器运行
        * 打包：mvn clean package -Dmaven.test.skip=true -U
        * 拷贝target文件夹到服务器
        * 运行./wechat-intfc.sh
2. 日志
    * 生成的日志在logs目录
3. 订阅号服务器配置
    * 进入订阅号后台，找到基本配置目录，点击服务器配置->修改配置
        * URL：填写站点部署的url/wechat.do（本地调试填写临时子域名）
        * Token：与SignUtil类中的token值一致
        * EncodingAESKey：随机生成
        * 消息加解密方式：明文模式
    * 如果提交时一直报参数错误，可能是域名被微信屏蔽（可以把url/wechat.do在微信中打开确认是否被屏蔽）
        * 申请自己的云服务器（有独立IP）
        * 使用k8s的ExternalService将到独立IP的请求转发到url/wechat.do（也可以使用Nginx）（以下请参考https://github.com/nuptaxin/okra-home）
            1. 云服务器定义external-svc-ngrok.yaml
                ```yaml
                apiVersion: v1
                kind: Service
                metadata:
                  name: external-svc-ngrok
                spec:
                  type: ExternalName
                  externalName: 3fbacb6b6a08.ngrok.io
                  ports:
                  - port: 80
                ```
            2. 创建服务：
                `kubectl create -f external-svc-ngrok.yaml`
            3. ingress添加url映射
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
                  - host: ngrok.okracode.com
                    http:
                      paths:
                      - path: /
                        pathType: Prefix
                        backend:
                          service:
                            name: external-svc-ngrok
                            port:
                              number: 80
                ```
               4. 更新ingress 
                  `kubectl apply -f okra-code-ing.yaml`
               5. 使用ngrok.okracode.com/wechat.do访问测试
                  
