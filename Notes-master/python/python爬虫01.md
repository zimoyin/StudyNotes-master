# 爬虫学习任务
1. requests库：自动爬取html页面，自动网络请求提交
2. robots.txt：robots网络爬虫协议。网落爬虫标准排除（合理合法使用爬虫）
3. beautiful soup库：解析html页面
4. project：实战项目A/B
5. re库：正则表达式详解提取页面关键信息
6. scrapy

# 1. http及URL

> http是一个超文本传输协议
> http是一个基于“请求与响应的”模式的，无状态(两次请求互不关联)的应用层(http协议工作在tcp协议之上)协议
> http协议采用URL作为定位网络资源的标识

* url：统一资源定位符
> url格式：http：host[:port][path]
>> host：合法的Internet主机域名或ip
>> port：端口号，可以省略默认端口为80，如果端口号不是80则不能省略
>> path：请求资源路径

* http协议对资源的操作
> get：请求获取url位置的资源
> head：请求获取url位置资源的响应消息报告，既获得该资源的头部信息
> post：请求向url位置的资源后附加新的数据
> put：请求url位置储存一个资源，覆盖url位置的资源
> patch：请求局部更新url资源位置的资源，既改变该处资源的部分内容
> delete：请求删除url位置存储的资源

# 2. requests
 <a href="http://cn.python-requests.org/zh_CN/latest/">requests文档</a>
## 2.1 requests安装

1. 打开终端（cmd）键入回车命令：
```
pip install requests
```

2. 简单使用
	```python
	import requests 							#导入库
	r = requests.get("http://www.baidu.com")	#爬取百度页面
	r.status_code 								#返回状态码，如果不是200就是访问/爬取页面失败
	r.encoding = 'utf-8'						#更改页面编码
	r.text 										#打印页面
	```
## 2.2 requests重要方法

```python
requests.request(method，url，**kwargs):构造一个请求,支持以下各方法的基础方法
    
requests.get(url，params=None，**kwargs)：获取html页面的主要方法，对应html的get

requests.head(url，**kwargs)：获取html网页头部信息的方法，对应html的head

requests.post(url，data=None，json=None，**kwargs)：向html网页提交post请求的方法，对应html的post

requests.put(url，data=None，**kwargs)：向html网页提交put请求的方法，对应html的put

requests.patch(url，data=None，**kwargs)：向html网页请求局部修改请求，对应html的patch

requests.delete(url，**kwargs)：向网页提交删除请求，对应html的delete
```

* requests.requests方法
> requests.requests(method，url，**kwargs）
>
> * method：GET,HEAD,POST,PUT,PATCH,delete,OPTIONS
> * **kwargs:控制访问参数，均为可选
>   * params：字典或字节序列，作为参数增加到url中（例1）
>   *  data字典，字节序列或文件对象，作为request的内容(例2)
>   * json:json格式数据，作为request的内容(列3)
>   * header：字典，http定制头(在字典里重写一个字段后发送出去，服务器会看到你发的头)(例4)
>   * cookie：字典或CookieJar,Request中的cookie
>   * auth：元组，支持http认证功能
>   * files：字典类型，传输文件(例5)
>   * timeout:设置超时时间，秒为单位，如果请求超过该时间还没有返回回来将抛出异常
>   * proxies：字典类型，设定访问代理服务器，可以增加登录认证
>   * allow_redirects:boolean 默认true，重定向开关
>   * stream：boolean，默认true，获取内容立即下载开关
>   * cert：本地ssl证书

### 1. requests方法
	* 基础方法例1：GET方法构造请求
```python

kv={'key1':'value1','key2':'value2'}
r=requests.requests('GET','http://python123.io/ws',params=kv)
print(r.text)#http://python123.io/ws?key1=value&key2=value2

```

	* 基础方法例2：POST方法构造请求
```python
kv={'key1':'value1','key2':'value2'}
r=requests.requests('POST','http://python123.io/ws',data=kv)

kz="主体内容"
r=requests.requests('POST','http://python123.io/ws',data=kz)
```


	* 基础方法列3:GET方法构造请求
```python
kv={'key1':'value1','key2':'value2'}
r=requests.requests('GET','http://python123.io/ws',json=kv)
```
	* 基础方法例4：模拟浏览器

```python
h={'user_agent':'Chrome/10'}
r=requests.requests('POST','http://python123.io/ws',header=h)#模拟了谷歌浏览器
```
	* 基础方法例5：
```python
fs={'file':open('data.xls','rb')}
r=requests.requests('POST','http://python123.io/ws',file=fs)
```




### 2. hear方法
```python
r=reques.head('http://httpbin.org/get')
r.header #输出头部信息
r.text #内容为空
```
### 3. post方法：向url post一个字典自动编码为form（表单）
```python
payload={'key1':'value1','key2':'value2'}
r=requests.post('http://httpbin.org/post',data='')
print(r.text)
```
输出：
```html
{...
"from":{
	'key1':'value1',
	'key2':'value2'
	}

}
```
```python
r=requests.post('http://httpbin.org/post',data='abc'
print(r.text)
```
输出：
```html
{...
"data":"abc"
"form":{},

}
```

### 4. requests.get(url,params=None,**kwargs）方法
> r=requests.get(url)：构造一个向服务器请求资源的`Requests`对象并返回一个包含服务器资源的`Response`对象
> requests.get(url,params=None,**kwargs）
>> url:模拟页面的url链接
>> params：url中的额外参数，字典或字节流格式，可选
>> **pwargs：12个控制访问的参数

* 例1：
```pythin
import requests
r=requests.get("http://baidu.com")#访问百度主页
print(r.status_code)#请求的状态码
type(r)#<class 'requests.models.Respones'> 检查r的类型
r.headers #返回get页面的头部信息
```


## 2.3 Response对象属性
```python
r.status_code：http请求返回状态，200表示连接成功，404失败
r.text：http响应内容的字符串形式，既url对应的页面内容
r.encoding：从http header中猜测的响应内容的编码方式
r.apparent_encoding：从内容中分析出内容编码方式（备选编码方式）
r.content：http响应内容的二进制形式
```
* get获取网络资源流程
> r.status_code获取状态码如果为404或其他非200就是获取失败，如果是200就获取成功进行下一步
> 第二步就是解析页面代表
> r.text等

## 2.4 编码
**获取网页编码两种方式：**
> r.encoding：从http header中猜测的响应内容的编码方式
> r.apparent_encoding：从内容中分析出内容编码方式（备选编码方式）

**方式不同之处极其用途：**
> r.encoding是从http header charset字段中获取编码方式，如果header中不存在charset，则认为编码为`ISO-8859-1`
> r.apparent_encoding是从http内容中分析出可能得编码方式
> 所以如果用r.endoding不能正确解码内容时用r.apparent_encoding从网页内容中分析出可能得编码方式
> （注意：r.apparent_encoding分析后的编码方式要赋值给r.encoding)

## 2.5 Requests库常用异常
```python
requests.ConnectinonError：网络连接错误异常，如dns查询失败，拒绝连接等
requests.HTTPError：HTTP错误异常
requests.URLREquired：URL缺失异常
requests.TooManyRedirects：超过最大重定向次数，产生重定向异常
requests.ConnectTimeout：连接远程服务器超时异常
requests.Timeout：请求URL超时，产生异常
```

### 例
```
r.raise_for_status()：判断状态码是不是200，如果不是就产生一个requests.HTTPError异常
```

## 2.6 爬取网页通用代码框架

```python
import requests

def getHTMLText(url):


        r=requests.get(url,timeout=30)
        print(r.status_code)
        r.raise_for_status()#如果状态不是200，就抛出HTTPError异常
        r.encoding=r.apparent_encoding #获取网页内容编码
        return r.text



if __name__ =="__main__": #执行py文件最先执行该语句，然后是引用该文件则不执行
    url = "https://www.baidu.com/s?ie=utf-8&word=aise_for_status"
    print(getHTMLText(url))

```

### 例

```python
import requests

def getHTMLText(url):


        r=requests.get(url,timeout=30)
        print(r.status_code)
        r.raise_for_status()#如果状态不是200，就抛出HTTPError异常
        r.encoding=r.apparent_encoding #获取网页内容编码
        return r.text



if __name__ =="__main__": #执行py文件最先执行该语句，然后是引用该文件则不执行
    url = "https://www.baidu.com/s?ie=utf-8&word=aise_for_status"
    print(getHTMLText(url))

```







# 3. robots协议
* 什么是爬虫协议
> Robots协议（也称为爬虫协议、机器人协议等）的全称是“网络爬虫排除标准”（Robots Exclusion Protocol），网站通过Robots协议告诉（建议）搜索引擎哪些页面可以抓取，哪些页面不能抓取。（非强制性）
* 爬虫协议如何遵守
> Robots协议也就是robots.txt文本文件，（如：www.baidu.com/robots.txt）
> 当一个搜索蜘蛛访问一个站点时，它会首先检查该站点根目录下是否存在robots.txt。如果存在，搜索爬虫就会按照该文件中的内容来确定访问的范围；
> 如果robots.txt文件不存在，搜索爬虫将会抓取网站上所有没有被口令保护的页面。
> (如果爬虫访问量小，可适当遵守爬虫协议)

* 语法
> *代表所有 /代表该(根）目录下内容
> User-agent: * 这里的*代表的所有的搜索引擎种类，*是一个通配符
> Disallow: /admin/*.html 这里定义是禁止爬寻admin目录下面的html文件


