rurl server
====

Rurl 是一个基于URL的权限控制系统,可以实现应用和权限的分离.
该应用依赖于已有的用户系统,用户的接入需要实现一个查询接口(java),这样的设计的目的是实现用户的统一管理,以及用户系统的独立

程序分为两部分,rurl服务器端和应用客户端(一般为web应用).

该服务器端使用java实现,应用客户端根据应用的不同可以选择不同语言的实现.暂时应用客户端只有 java 版本的实现, python 和 php 版本实现稍后添加! https://github.com/lichangshu/rurl-cli
其他语言因个人技术限制,暂时没计划.如有感兴趣的可以联系 E-mail: lchshu001@gmail.com .

概述
----------

大部分公司都会有许多的(web)应用,如果每个应用独立的权限控制,将导致重复的开发成本,和分散的用户以及权限管理.该系统可以实现多应用权限的统一管理,同时实现用户跟应用的分离.

系统设计架构
----------

![image](https://github.com/lichangshu/rurl/raw/master/doc/sys.jpg)

###流程:
用户需要首先登录,登录后客户端程序会获取到该用户具有的所有权限,然后缓存下来,当用户访问某个配置了rurl客户端程序的应用的时候,该客户端程序会拦截用户请求的URL,根据规则,查询该用户是否有该URL的访问权限,以阻止或允许用户访问.

由于服务器压力问题,没有将该权限判断放置到RURL服务器端进行判断,而是放到了没个应用自己进行计算权限.所以权限依赖于客户端程序,服务器只负责用户权限的查询.

因此如果自己实现客户端程序,可以故意的错用或误用默认的规则,来实现自己的权限判断功能.

基本概念
-----------
###1. 员工/用户
用户管理系统中的一个用户,即需要配置权限的的一个用户(一般为员工)

###2. 组 (用户组,权限组)
不区分用户组和权限组. 一个组表示一些人和这些人具有的权限, 因为用户不在权限系统中维护,所有不提供 类似部门部门的概念. 可以使用一个特定的组来表现部门.

###3. 应用(web)
该系统是对多个应用进行权限控制,而每个应用必须有一个唯一的 appName (为配置方便 要求为英文 且不可修改).

###4. 登录组
每个应用一般会有一个特殊的组,只有该组中的用户可以登录该应用,该组称为"登录组",一个用户有权限,前提是用登录权限.如果用户没有登录权限,即使配置其他权限,他任然没有使用该应用的权限.

###5. 权限
即应用中的一个URL,该URL一般是不带有get请求时的query参数的.是否需要query参数,需要根据应用的需要,配合客户端实现来做判断.

关键点说明
--------------------

1.每个应用可以配置一个登录组,该组可以标识是可登录用户,或不可登录用户, 是通过是否允许所有人登录进行控制的, 如果选择所有人可以登录,那么该组中的用户将不可以登录该应用, 否则之用该组中的用户可以登录该应用.

(或许可以改成有权限就可以登录,因为设计时有排除的概念,所有没有做这个选择)

2.每个应用配置的URL可以是手动填入的,或者是程序自动插入的(客户端提供了一个简单的收集URL的工具),总有写URL会被遗漏,对于这种遗漏的URL如何做权限判断.通过应用中的"未配置的URL"选项进行控制.

3.每个URL都有一种类型,默认/菜单/目录三者之一,可以为用户登录后列出用户菜单时使用,包括排序(正序).

4.每个URL都可以设置一个父菜单,注意不要出现菜单循环, 如:A的父菜单是B,B的父菜单是C,C的父菜单是A.

接口
---------------------
接口使用 hessian 的远程方法调用(RMI).


		public interface RurlRightService {

			/**
			 * 
			 * 使用账户密码登录.
			 * 如果登录失败 会抛出异常, NoRightException 的 getMessage 可以获取错误的原因.
			 * 
			 * @param account
			 * @param password
			 * @return
			 * @throws NoRightException 
			 */
			public Map<String, Object> login(String account, String password) throws NoRightException;

			/**
			 * 某账户是否有登录应用(appname)的权限.
			 * 跟 login 方法分开的目的是 login 方法是用户系统实现的方法,而该方法是用于 RURL系统 来实现的.
			 * 
			 * @param appname
			 * @param account
			 * @return 
			 */
			public boolean hasLoginRight(String appname, String account);

			/**
			 * 列出某个账户在某个应用中的,拥有权限的所有URL.
			 * 
			 * @param appname
			 * @param account
			 * @return 
			 */
			public Map<String, Object>[] listAccountUrlRight(String appname, String account);

			/**
			 * 列出某应用中的被配置在 RURL系统 中的所有URL.
			 * 
			 * @param appname
			 * @return 
			 */
			public Map<String, Object>[] listUrlRight(String appname);

			/**
			 * 获取应用的配置信息.
			 * 
			 * @param appname
			 * @return 
			 */
			public Map<String, Object> getApplication(String appname);

			/**
			 * 保存一组URL, 用于客户端主动向服务器添加URL.
			 * 详细见 save4url(String appname, String url) 方法
			 * @param appname
			 * @param url
			 * @return 
			 */
			public Map<String, Object>[] saveURL(String appname, String[] url);

			/**
			 * 保存单个URL, 用于客户端主动向服务器添加URL.
			 * 对于已经存在的url不会继续添加,更不会修改.
			 * 
			 * @param appname
			 * @param url
			 * @return 
			 */
			public Map<String, Object> save4url(String appname, String url);
		}

注意如果打算修改源码,建议不用使用 重载/多态(因为有的语言不支持,导致hessian不必要的麻烦)

数据集合只使用数组和map,因为不同语言数据结构映射会有不同.


