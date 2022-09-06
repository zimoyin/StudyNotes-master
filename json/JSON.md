# JSON(待完善)

作者: 子墨

参考: 狂神文章，百度百科



## 一、什么是JSON

- [JSON](https://baike.baidu.com/item/JSON)([JavaScript](https://baike.baidu.com/item/JavaScript) Object Notation, JS 对象简谱) 是一种轻量级的数据交换格式。
- 它基于 [ECMAScript](https://baike.baidu.com/item/ECMAScript) (欧洲计算机协会制定的js规范)的一个子集，采用完全独立于编程语言的文本格式来存储和表示数据。简
- 洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。 
- 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。



## 二、JSON 语法规则

> 语法

JSON 语法衍生于 JavaScript 对象标记法语法：

- 数据在名称/值对中
  - 键必须是字符串，由双引号包围
- 数据由逗号分隔
- 花括号容纳对象
- 方括号容纳数组
- 数组和对象可以互相套娃

> JSON 值

在 JSON 中，值必须是以下数据类型之一：

- 字符串
- 数字
- 对象（JSON 对象）
- 数组
- 布尔
- null



## 三、JavaScript中的JSON

> **JSON 和 JavaScript 对象互转**

要实现从JSON字符串转换为JavaScript 对象，使用 JSON.parse() 方法：

```javascript
var obj = JSON.parse('{"a": "Hello", "b": "World"}');
//结果是 {a: 'Hello', b: 'World'}
```

要实现从JavaScript 对象转换为JSON字符串，使用 JSON.stringify() 方法：

```javascript
var json = JSON.stringify({a: 'Hello', b: 'World'});
//结果是 '{"a": "Hello", "b": "World"}'
```



> JSON访问和修改值

通过json.键=值修改

```js
var json={"a":true,"name":"zimo"};
json.a=false;	//修改值

var i = json.a;//访问值
console.log(json.a)
```

```
false
```



当然也可以这样访问和修改他

通过json.键=值修改

```js
var json={"a":true,"name":"zimo"};
json["a"]=false;	//修改值

var i = json["a"];//访问值
console.log(json.a)
```

```
false
```



## 四、java访问json

java是有原生API的，但是原生API不太友好所以这里就不去管它。我们直接去用别人写好的第三方jar包

Jackson应该是目前比较好的json解析工具了

当然工具不止这一个，比如还有阿里巴巴的 fastjson 等等。



#### Jackson

我们这里使用Jackson，使用它需要导入它的jar包；

> 导包

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.9.8</version>
</dependency>
```



> 常用方法

```java
ObjectMapper mapper = new ObjectMapper();
//将我们的对象解析成为json格式
String str = mapper.writeValueAsString(user);

String str = mapper.writeValueAsString(new Data()); //返回的是一个时间戳
```

```java
//工具类，返回当前日期
public class JsonUtils {
   
   public static String getJson(Object object) {
       return getJson(object,"yyyy-MM-dd HH:mm:ss");
  }

   public static String getJson(Object object,String dateFormat) {
       ObjectMapper mapper = new ObjectMapper();
       //不使用时间戳的方式
       mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
       //自定义日期格式对象
       SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
       //指定日期格式
       mapper.setDateFormat(sdf);
       try {
           return mapper.writeValueAsString(object);
      } catch (JsonProcessingException e) {
           e.printStackTrace();
      }
       return null;
  }
}
```





#### FastJson

fastjson.jar是阿里开发的一款专门用于Java开发的包，可以方便的实现json对象与JavaBean对象的转换，实现JavaBean对象与json字符串的转换，实现json对象与json字符串的转换。实现json的转换方法很多，最后的实现结果都是一样的。



> 导包

```xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>fastjson</artifactId>
   <version>1.2.60</version>
</dependency>
```



fastjson 三个主要的类：

**JSONObject  代表 json 对象** 

- JSONObject实现了Map接口, 猜想 JSONObject底层操作是由Map实现的。
- JSONObject对应json对象，通过各种形式的get()方法可以获取json对象中的数据，也可利用诸如size()，isEmpty()等方法获取"键：值"对的个数和判断是否为空。其本质是通过实现Map接口并调用接口中的方法完成的。

**JSONArray  代表 json 对象数组**

- 内部是有List接口中的方法来完成操作的。

**JSON代表 JSONObject和JSONArray的转化**

- JSON类源码分析与使用
- 仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化。



> 常用方法

```java
public class UserController {
   public void json1() throws JsonProcessingException {   
       


       //JSON字符串 转 Java对象
       对象 Obj = JSON.parseObject(JSON字符串,对象.class);
       
       //json字符串转JSON对象
       JSONObject parse = (JSONObject) JSONObject.parse(json);

       //Java对象 转 JSON对象
       JSONObject jsonObject1 = (JSONObject) JSON.toJSON(对象);
       //对象转JSON
       String str1 = JSON.toJSONString(对象); 

       //JSON对象 转 Java对象
       Java对象 obj = JSON.toJavaObject(JSON对象, Java对象.class);
       
       //字符串转数组
       JSONArray jsonArray = JSONObject.parseArray (s);
       //获取数组里面的第0个元素
       JSONObject jsonObject = jsonArray.getJSONObject(0);
       
  }
}
```



判断json是对象还是数组

```java
if (json.startsWith("[") && json.endsWith("]")) {
    //是数组
    System.out.println("我是jsonArray");
} else {
    //是对象
    System.out.println("我是jsonObject");
}
```







## END