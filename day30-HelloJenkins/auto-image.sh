#!/usr/bin/env bash
group_name="devops"
#app_name必须全小写
app_name='hello-jenkins'
app_version='1.0-SNAPSHOT'
profile_active='dev'

echo '>>>>>>> copy jar >>>>>>>'
docker stop ${app_name}
echo '>>>>>>> stop container >>>>>>>'
docker rm ${app_name}
echo '>>>>>>> remove container >>>>>>>'
docker rmi ${group_name}/${app_name}:${app_version}
echo '>>>>>>> remove image >>>>>>>'

# 打包编译docker镜像，‘.’ 表示使用当前目录下的Dockerfile
docker build -t ${group_name}/${app_name}:${app_version} .
echo '>>>>>>> build image >>>>>>>'

#
docker run -p 8081:8081 --name ${app_name} \
-e 'spring.profiles.active'=${profile_active} \
--link mysql8.0:db \
-e TZ="Asia/Shanghai" \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/${app_name}/logs:/var/logs \
-d ${group_name}/${app_name}:${app_version}
echo '>>>>>>> start container >>>>>>>'

# 1.#!/bin/sh 必须放shell脚本第一行，指定shell执行的bash内核,以#!开头
# 2.docker run命令的一些参数说明，如下：
:<<BLOCK
-a stdin: 指定标准输入输出内容类型，可选 STDIN/STDOUT/STDERR 三项；
-d: 后台运行容器，并返回容器ID；
-i: 以交互模式运行容器，通常与 -t 同时使用；
-P: 随机端口映射，容器内部端口随机映射到主机的高端口
-p: 指定端口映射，格式为：主机(宿主)端口:容器端口
-t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；
--name="nginx-lb": 为容器指定一个名称；
--dns 8.8.8.8: 指定容器使用的DNS服务器，默认和宿主一致；
--dns-search example.com: 指定容器DNS搜索域名，默认和宿主一致；
-h "mars": 指定容器的hostname；
-e username="ritchie": 设置环境变量；
--env-file=[]: 从指定文件读入环境变量；
--cpuset="0-2" or --cpuset="0,1,2": 绑定容器到指定CPU运行；
-m :设置容器使用内存最大值；
--net="bridge": 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型；
--link=[]: 添加链接到另一个容器，需使用其他容器如，DB；
--expose=[]: 开放一个端口或一组端口；
--volume , -v:    绑定一个卷，即容器与主机的文件目录映射；
BLOCK