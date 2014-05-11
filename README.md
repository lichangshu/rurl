rurl server
====

Rurl 是一个基于URL的权限控制系统,通过拦截用户访问的url,查询当前登录的用户是否可以访问该url,来进行权限的控制,同时可以实现应用和权限的分离.
该应用依赖于已有的用户系统,用户的接入需要实现一个查询接口(java),这样的设计的目的是实现用户的统一管理,以及用户系统的独立.

程序分为两部分,rurl服务器端和应用客户端(一般为web应用).

rurl服务器端为普通的 java web应用, 客户端根据应用的不同可以选择不同语言的实现.暂时应用客户端只有 java 版本的实现, python 和 php 版本实现稍后添加!
客户端地址 https://github.com/lichangshu/rurl-cli 
其他语言因个人技术限制,暂时没计划.如有感兴趣的可以联系 E-mail: lchshu001@gmail.com .

安装
----------
1. 系统需求: JDK 1.5, servlet 2.5(Tomcat 6,以上即可), MYSQL 5(支持 InnoDB).
2. 执行 /db/table.sql SQL
3. 修改配置文件 class path 下的 /config/config.properties 修改数据库配置
4. rurl自己的权限控制也是通过rurl控制的.修改客户端的配置文件 classpath 下的 rconfig.properties.详细见客户配置

系统设计架构
----------

![image](https://github.com/lichangshu/rurl/raw/master/doc/sys.jpg)

###流程:
用户需要首先登录,登录后客户端程序会获取到该用户具有的所有权限,然后缓存下来,当用户访问某个配置了rurl客户端程序的应用的时候,该客户端程序会拦截用户请求的URL,根据规则,查询该用户是否有该URL的访问权限,以阻止或允许用户访问.

由于服务器压力问题,没有将该权限判断放置到RURL服务器端进行判断,而是放到了没个应用自己进行计算权限.所以权限依赖于客户端程序,服务器只负责用户权限的查询.

因此如果自己实现客户端程序,可以故意的错用或误用默认的规则,来实现自己的权限判断功能.

