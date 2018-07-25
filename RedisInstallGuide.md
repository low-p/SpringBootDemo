# Redis的本地安装使用(windows版本可供本地调试使用)   
下载Windows版本Redis地址: https://github.com/MicrosoftArchive/redis/releases   
#解压版本安装测试步骤  
1、添加Redis解压路径到系统环境变量Path(不配置环境变量一下命令前加.\)   
2、启动服务  
命令：redis-server.exe redis.windows.conf  
不要关闭当前窗口,打开新命令窗口继续   
3、安装服务项以便启动  
命令：redis-server.exe --service-install redis.windows-service.conf --loglevel verbose  
在服务窗口就可以找到Redis服务   
4、Redis服务启动  
命令：redis-server --service-run/redis-server --service-start      
具体使用那条命令可以参照Redis服务属性的可执行文件路径说明   
5、测试Redis服务连接   
命令：redis-cli.exe -h 127.0.0.1 -p 6379   
连接成功后可以进行set、get测试   
6、补充命令   
卸载服务命令：redis-server --service-uninstall   
停止服务命令：redis-server --service-stop   

