## spring cloud+RocketMQ实现发送邮件服务

### 起手指南  
开发工具idea，数据库MySQL
* 在本地MySQL数据库中导入sql文件下的emailfile.sql文件
* 运行eureka模块，必须先运行该模块，否则其他模块运行失败
* 运行feign-client模块
* 运行file-service模块
* 运行mail-service模块
* 运行mail-consumer模块

### 模块描述
1. eureka模块，即注册中心 
2. feign-client模块，接受客户端发送的信息，向注册中心申请服务地址并将客户端发送的信息发送至该服务  
3. file-service模块，提供上传文件以及文件下载的接口
4. mail-service模块，提供发送邮件接收邮件信息接口，并将信息发送至RockMQ队列中
5. mail-consumer模块，对消息队列RocketMQ进行监听，监听到信息时，根据信息判断是否从file-service模块下载文件，并将其添加至邮件附件中一并发送

### 测试
* 接口路径：http://localhost:8804/mail
* 参数
* file：发送的附件，文件类型
* sendTo：接受者，字符串
* titel：邮件主题，字符串
* content：邮件内容，字符串
* isHtml：是否Html，布尔类型，true为是，false为否
