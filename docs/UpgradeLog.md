# 升级日志

### 基础功能实现
`v1.0.0`
- [x] 实现聊天机器人的基准聊天功能
- [x] 接入多个聊天机器人，支持调用失败时自动切换

### 优化版本升级机制
`v1.0.1`
- [x] 定义软件版本与数据版本对应关系
- [x] 软件版本升级命令定义
- [x] 软件升级前的release发布定义
- [x] 优化jar包依赖关系

### 接入wx-tools
`v1.1.0`
- [x] 接入微信公众号开源框架wx-tools

### 优化wx-tools使用
`v1.1.1`
- [x] wx-tools使用新版本v1.2.4-RELEASE-1.0.1，不再把所有依赖集成在jar包中
- [x] 优化wx-tools post请求的编码，使用内置的router

### 完善聊天日志
`v1.2.0`
- [x] 记录使用的聊天机器人API类型
- [x] 记录消息创建和更新时间

### 优化记录日志代码
`v1.3.0`
- [x] 使用spring的event记录日志（开启异步）
- [x] msg_time字段数据同步及creatime字段弃用

### 移除fastjson
`v1.4.0`
- [x] jackson替换fastjson
- [x] 移除wechat_msg表createtime字段
- [x] spring.datasource.url设置时区serverTimezone=Asia/Shanghai

### 单元测试
`v1.5.0`
- [x] 核心逻辑单元测试

### 单元测试
`v1.5.1`
- [x] 单元测试覆盖率达到50%+

### 应用重命名
`v1.6.0`
- [x] 修改应用名wx-subscription->wechat-offiaccount-helper

### 日志框架升级
`v1.7.0`
- [x] 日志框架log4j升级为log4j2

### sql脚本bug修复
`v1.7.1`
- [x] 修复sql脚本逗号确实导致无法初始化bug