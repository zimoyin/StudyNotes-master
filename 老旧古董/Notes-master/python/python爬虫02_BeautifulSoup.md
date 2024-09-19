
# beautiful soup库
>Beautiful Soup 是一个可以从HTML或XML文件中提取数据的Python库.它能够通过你喜欢的转换器实现惯用的文档导航,查找,修改文档的方式.Beautiful Soup会帮你节省数小时甚至数天的工作时间.

* <a href="https://beautifulsoup.readthedocs.io/zh_CN/v4.4.0/">官方中文文档</a>
## 下载
```python
pip install beautifulsoup4
```

## 使用
```python
from bs4 import BeautifulSoup
soup=BeautifulSoup(demo,'html.parser')#参数： 网页，解析器
```
## 解析器
* Python标准库
	* 使用方法
		* BeautifulSoup(markup, "html.parser")
	* 优势
		* Python的内置标准库
		* 执行速度适中
		* 文档容错能力强
	* 劣势
		* Python 2.7.3 or 3.2.2)前 的版本中文档容错能力差
* lxml HTML 解析器
	* 使用方法
		* BeautifulSoup(markup, "lxml")
	* 优势
		* 速度快
		* 文档容错能力强
	* 劣势
		* 需要安装C语言库
* lxml XML 解析器
	* 使用方法：`安装：pip install lxml`
		* BeautifulSoup(markup, ["lxml-xml"])
		* BeautifulSoup(markup, "xml")
	* 优势
		* 速度快
		* 唯一支持XML的解析器
	* 劣势
		* 需要安装C语言库
* html5lib	
	* 使用方法`安装： pip install html5lib`
		* BeautifulSoup(markup, "html5lib")
	* 优势
		* 最好的容错性
		* 以浏览器的方式解析文档
		* 生成HTML5格式的文档
	* 劣势
		* 速度慢
		* 不依赖外部扩展
## 对象种类
> Beautiful Soup将复杂HTML文档转换成一个复杂的树形结构,每个节点都是Python对象,所有对象可以归纳为4种: Tag , NavigableString , BeautifulSoup , Comment .



### Tag
Tag 对象与XML或HTML原生文档中的tag相同:
```python
soup = BeautifulSoup('<b class="boldest">Extremely bold</b>')
tag = soup.b
type(tag)
# <class 'bs4.element.Tag'>
```
Tag有很多方法和属性,在 遍历文档树 和 搜索文档树 中有详细解释.现在介绍一下tag中最重要的属性: name和attributes

### Name
每个tag都有自己的名字,通过 .name 来获取:
```python
tag.name
# u'b'
```
如果改变了tag的name,那将影响所有通过当前Beautiful Soup对象生成的HTML文档:

```python
tag.name = "blockquote"
tag
# <blockquote class="boldest">Extremely bold</blockquote>

```
### Attributes

一个tag可能有很多个属性. tag <b class="boldest"> 有一个 “class” 的属性,值为 “boldest” . tag的属性的操作方法与字典相同:
```
tag['class']
# u'boldest'
```
也可以直接”点”取属性, 比如: .attrs :
```
tag.attrs
# {u'class': u'boldest'}
```
tag的属性可以被添加,删除或修改. 再说一次, tag的属性操作方法与字典一样
```
tag['class'] = 'verybold'
tag['id'] = 1
tag
# <blockquote class="verybold" id="1">Extremely bold</blockquote>

del tag['class']
del tag['id']
tag
# <blockquote>Extremely bold</blockquote>

tag['class']
# KeyError: 'class'
print(tag.get('class'))
# None
```

### 可以遍历的字符串
字符串常被包含在tag内.Beautiful Soup用 NavigableString 类来包装tag中的字符串:
```
tag.string
# u'Extremely bold'
type(tag.string)
# <class 'bs4.element.NavigableString'>
```
一个 NavigableString 字符串与Python中的Unicode字符串相同,并且还支持包含在 遍历文档树 和 搜索文档树 中的一些特性. 通过 unicode() 方法可以直接将 NavigableString 对象转换成Unicode字符串:
```
unicode_string = unicode(tag.string)
unicode_string
# u'Extremely bold'
type(unicode_string)
# <type 'unicode'>
```
tag中包含的字符串不能编辑,但是可以被替换成其它的字符串,用 replace_with() 方法:
```
tag.string.replace_with("No longer bold")
tag
# <blockquote>No longer bold</blockquote>
```
NavigableString 对象支持 遍历文档树 和 搜索文档树 中定义的大部分属性, 并非全部.尤其是,一个字符串不能包含其它内容(tag能够包含字符串或是其它tag),字符串不支持 .contents 或 .string 属性或 find() 方法.

如果想在Beautiful Soup之外使用 NavigableString 对象,需要调用 unicode() 方法,将该对象转换成普通的Unicode字符串,否则就算Beautiful Soup已方法已经执行结束,该对象的输出也会带有对象的引用地址.这样会浪费内存.


### 注释及特殊字符串

Tag , NavigableString , BeautifulSoup 几乎覆盖了html和xml中的所有内容,但是还有一些特殊对象.容易让人担心的内容是文档的注释部分:

```python
markup = "<b><!--Hey, buddy. Want to buy a used parser?--></b>"
soup = BeautifulSoup(markup)
comment = soup.b.string
type(comment)
# <class 'bs4.element.Comment'>
```
Comment 对象是一个特殊类型的 NavigableString 对象:
```
comment
# u'Hey, buddy. Want to buy a used parser'
```
但是当它出现在HTML文档中时, Comment 对象会使用特殊的格式输出:
``pythkn
print(soup.b.prettify())
# <b>
#  <!--Hey, buddy. Want to buy a used parser?-->
# </b>
```
Beautiful Soup中定义的其它类型都可能会出现在XML的文档中: CData , ProcessingInstruction , Declaration , Doctype .与 Comment 对象类似,这些类都是 NavigableString 的子类,只是添加了一些额外的方法的字符串独享.下面是用CDATA来替代注释的例子:

```python
from bs4 import CData
cdata = CData("A CDATA block")
comment.replace_with(cdata)

print(soup.b.prettify())
# <b>
#  <![CDATA[A CDATA block]]>
# </b>

```
### 概括_例
```python
#导入库
import requests
from bs4 import BeautifulSoup

#爬取网页
r=requests.get("http://ffff.zhaoxs.club/")
r.encoding=r.apparent_encoding
demo=r.text

#要解析的内容并且定义解析器
soup=BeautifulSoup(demo,'html.parser')

#标签
soup.title #获取标题
soup.a #获取标签（只获取第一个）
soup.a.name #获取标签名字
soup.a.parent #获取父标签
soup.a.parent.parent.name #获取爷爷标签的名字

#内容
soup.a.string #获取a标签中的内容
#注意如果标签内有注释也会被获取，注意区分

#属性
tag=soup.a
tag.attrs #获取标签的属性返回字典
tag['href'] #获取href属性的值
tag['href']="233" #更改属性值


```



## 遍历html文档
### 遍历指定节点标签
#### .tag
* 用法：
	* tag.标签名 ：此用法只会返回html文档里面的第一个标签
	* 例：
		```python
			#获取标签
			tag.a #<a>a</a>
			
			获取指定标签下的标签
			tag.body.a #<a>a</a>
		```
#### .find_add()
* 用法：
	* soup.find_all('标签名') : 此方法会返回html上所有该标签
	* 例：
		```python
			for child in soup.find_all('a'):
 
			print(child)
			
		```
### 遍历下行节点
#### .children
* 子节点迭代类型，用于循环遍历儿子节点
```python
for s in soup.body.children :
    print(s)
```
#### .contents
* 使用方法：
	* soup.标签.contents
* .contents 属性可以将tag的子节点存入列表输出:

* 例
```python
#获取头部节点
head_tag = soup.head
head_tag # <head><title>The Dormouse's story</title></head>

#获取头部的所有子节点
head_tag.contents #[<title>The Dormouse's story</title>]

#获取头部的第一个子节点
title_tag = head_tag.contents[0]
title_tag # <title>The Dormouse's story</title>

title_tag.contents # [u'The Dormouse's story']
```

##### 注意：
* BeautifulSoup 对象本身一定会包含子节点,也就是说<html>标签也是 BeautifulSoup 对象的子节点:

```python
len(soup.contents)
# 1
soup.contents[0].name
# u'html'
```

* 字符串没有 .contents 属性,因为字符串没有子节点:
```python
text = title_tag.contents[0]
text.contents
# AttributeError: 'NavigableString' object has no attribute 'contents'
```
#### .descendants
* .descendants 属性可以对所有tag的子孙节点进行递归循环 :
	* 它可以使tap的子节点的子节点...子节点进行递归（大白话）
```python
for child in soup.head.descendants:
    print(child)
    # <title>The Dormouse's story</title>
    # The Dormouse's story
```

#### .string
* 如果tag只有一个 NavigableString 类型子节点,那么这个tag可以使用 .string 得到子节点:
```python
title_tag.string
# u'The Dormouse's story'
```
* 如果一个tag仅有一个子节点,那么这个tag也可以使用 .string 方法,输出结果与当前唯一子节点的 .string 结果相同:
```python
head_tag.contents
# [<title>The Dormouse's story</title>]

head_tag.string
# u'The Dormouse's story'
```

* 如果tag包含了多个子节点,tag就无法确定 .string 方法应该调用哪个子节点的内容, .string 的输出结果是 None :
```python
print(soup.html.string)
# None
```
#### .strings 和 stripped_strings(没鸟用，不用看了）
如果tag中包含多个字符串 [2] ,可以使用 .strings 来循环获取:
```python
for string in soup.strings:
    print(repr(string))
    # u"The Dormouse's story"
    # u'\n\n'
    # u"The Dormouse's story"
    # u'\n\n'
    # u'Once upon a time there were three little sisters; and their names were\n'
    # u'Elsie'
    # u',\n'
    # u'Lacie'
    # u' and\n'
    # u'Tillie'
    # u';\nand they lived at the bottom of a well.'
    # u'\n\n'
    # u'...'
    # u'\n'
    
```
输出的字符串中可能包含了很多空格或空行,使用 .stripped_strings 可以去除多余空白内容:
```python
for string in soup.stripped_strings:
    print(repr(string))
    # u"The Dormouse's story"
    # u"The Dormouse's story"
    # u'Once upon a time there were three little sisters; and their names were'
    # u'Elsie'
    # u','
    # u'Lacie'
    # u'and'
    # u'Tillie'
    # u';\nand they lived at the bottom of a well.'
    # u'...'
    
```
全部是空格的行会被忽略掉,段首和段末的空白会被删除
    
### 遍历上行节点
* 标签树中每个tag或字符串都有父节点:被包含在某个tag中
#### .parent
* 用法
	* soup.tag.parent
* 通过 .parent 属性来获取某个元素的父节点.
* 在例子“爱丽丝”的文档中,<head>标签是<title>标签的父节点:
```python
title_tag = soup.title
title_tag #<title>The Dormouse's story</title>
title_tag.parent # <head><title>The Dormouse's story</title></head>
```
* 文档title的字符串也有父节点:<title>标签
```python
title_tag.string.parent
# <title>The Dormouse's story</title>
```

* 文档的顶层节点比如<html>的父节点是 BeautifulSoup 对象:
```python
html_tag = soup.html
type(html_tag.parent)
# <class 'bs4.BeautifulSoup'>
```
* BeautifulSoup 对象的 .parent 是None:
```python
print(soup.parent)
# None

```
#### .parents

* 通过元素的 .parents 属性可以递归得到元素的所有父辈节点,下面的例子使用了 .parents 方法遍历了<a>标签到根节点的所有节点.
```python
link = soup.a
link
# <a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>
for parent in link.parents:
    if parent is None:
        print(parent)
    else:
        print(parent.name)
# p
# body
# html
# [document]
# None

```

### 遍历平行节点
#### .next_sibling(获取下平行标签) 和 .previous_sibling（获取上平行标签） 属性来查询兄弟（平行）节点:
例子： 
```html
 <p id = "link1">a</p>
 <a id="link2">ab</a>
 <p id="link3">abc</p>
 ``` 
```python
print(bs.p.next_sibling)
# \n
```
注意：
 * 当你用这方法去获取时并不会获取到标签而是获取到换行符或顿号等等
 * 所以当你获取到换行符后就要再次获取下/上平行标签
 * 因为太麻烦了所以就要获取上下平行标签集合
#### .next_siblings 和 .previous_siblings 属性可以对当前节点的兄弟节点迭代输出
```python
for sibling in soup.a.next_siblings:
    print(repr(sibling))
   

for sibling in soup.find(id="link3").previous_siblings:
    print(repr(sibling))
   

```

