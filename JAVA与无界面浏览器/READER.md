# 无界面浏览器

作者：zimo

日期： 2023.04.15

资料来源：CSDN、CHATGPT，BING，GOOGLE，API DOC



## 1. 概要

### 1.1  概念

**无头浏览器**指的是没有[图形用户界面](https://zh.wikipedia.org/wiki/图形用户界面)的[浏览器](https://zh.wikipedia.org/wiki/浏览器)。

无头浏览器在类似于流行网络浏览器的环境中提供对网页的自动控制，但是通过命令行界面或使用网络通信来执行。 它们对于测试网页特别有用，因为它们能够像浏览器一样呈现和理解超文本标记语言，包括页面布局、颜色、字体选择以及[JavaScript](https://zh.wikipedia.org/wiki/JavaScript)和[AJAX](https://zh.wikipedia.org/wiki/AJAX)的执行等样式元素，这些元素在使用其他测试方法时通常是不可用的。[[1\]](https://zh.wikipedia.org/wiki/无头浏览器#cite_note-1)[[2\]](https://zh.wikipedia.org/wiki/无头浏览器#cite_note-2)

> 用例[[编辑](https://zh.wikipedia.org/w/index.php?title=无头浏览器&action=edit&section=1)]

无头浏览器通常用来：

- Web应用程序中的测试自动化。
- 拍摄网页截图
- 对JavaScript库运行自动化测试
- 收集网站数据
- 自动化网页交互

---来自 **[维基百科](https://zh.wikipedia.org/wiki/%E6%97%A0%E5%A4%B4%E6%B5%8F%E8%A7%88%E5%99%A8)**

-----

### 1.2 与传统的Http 库做爬虫对比

无头浏览器与HTTP库都可以用于爬虫，但它们有一些区别和优劣势。无头浏览器指的是使用脚本来模拟真实浏览器的使用场景，它能够更好地处理JavaScript渲染、异步请求等问题，而HTTP库则更适合处理静态页面、API等。

**无头浏览器:**

* **JavaScript渲染**和动态页面处理: 如果你需要爬取动态页面或者需要执行JavaScript代码，无头浏览器是更好的选择。
* JavaScript解密：无头浏览器不能解密js脚本以此让你查阅，但是他能执行该脚本以此来得到结果。而HTTP库对于加密的JS需要进行JS逆向等操作之后在模拟加密过程才能与API进行访问，而无头浏览器直接帮你进行模拟。**无头模拟器通常用于有加密的js的网站**
* 隐私和安全：无头浏览器可以**模拟真实的用户行为**，更能够避免被网站检测到并阻止。此外，无头浏览器可以自主的使用代理、设置cookies等方式来保护隐私和安全。**因此可以做到处理反爬虫的效果**



**HTTP库：**

* **性能和速度**：HTTP库通常比无头浏览器更快。HTTP只需要考虑API和静态页面的获取，不需要解析页面，获取CSS，渲染页面
* **代码复杂度和维护成本**：获取某个数据可以通过少量代码直接调用API。使用无头浏览器可能需要更复杂的代码，因为需要编写JavaScript代码来模拟用户行为和操作DOM



### 1.3 无头浏览器一览

- **Headless Chrome**： Google推出的无头浏览器，可以模拟Chrome浏览器的所有功能，包括JavaScript解析、HTML解析、CSS解析等。
- Selenium ： **并不是一个无头浏览器**，而是一个浏览器自动化测试工具，这个通常会被搞混，他可以通过驱动程序来自动化操作对应的浏览器。Selenium可以支持多款主流浏览器，包括Chrome、Firefox、Safari等。同时 Selenium还支持浏览器的无头模式，比如Firefox、PhantomJS、Chrome的无头模式（Headless）等
- Puppeteer： Google开发的一个Node.js库，用于控制Headless Chrome，可以模拟用户操作、截屏、生成PDF等。
- PhantomJS：一个已经停止维护的无头浏览器，但仍然被很多人使用，可以模拟浏览器的行为，支持JavaScript解析和网页截屏等。
- Playwright： Microsoft开发的一个Node.js库，可以控制多个浏览器，包括Chrome、Firefox和Safari等，可以模拟用户操作、截屏、生成PDF等。
- Splash：一个基于Python的无头浏览器，可以解析JavaScript、渲染网页、截屏等，可以通过HTTP API进行控制。

- **HtmlUnit**: 一款基于Java的开源无头浏览器，它可以模拟浏览器行为并执行JavaScript代码。它可以与JUnit和TestNG等测试框架集成，用于自动化测试和Web爬虫等任务。
- JBrowserDriver: 一款基于Selenium WebDriver的无头浏览器，它使用Java Swing库模拟浏览器界面。它可以模拟用户行为，进行自动化测试和Web爬虫等任务。然而，由于它使用了Java Swing库，因此可能需要更多的资源。
- Cobra: 一款Java HTML解析器和渲染引擎，可以模拟浏览器行为并执行JavaScript代码。它可以用于自动化测试、Web爬虫和Web应用程序等任务。然而，由于它不是专门为自动化测试而设计的，因此可能需要更多的配置和代码。
- JavaFX WebView：它不是一个真正的无头浏览器，因为它需要一个可见的JavaFX窗口来运行.

### 1.4 环境选择

对于无头浏览器我们选择两个(**Headless Chrome**  or **HtmlUnit**)，这两个都是讲究的对象，但是不是一起研究的。同时我们选择使用 **JAVA 17** 作为开发语言进行开发，使用**IDEA 2023** 为IDE 进行编写。在 **Win系统** 下编写代码

### 1.5 问答

User : selenium 是无头浏览器吗，和Headless Chrome是什么关系，他们能混为一谈吗

> AI:
>
> Selenium是一个用于网站前端压力测试框架的工具，它能够直接操作浏览器，因此可以用代码来操纵浏览器进行各种操作。Selenium支持所有主流的浏览器，包括Chrome、Firefox、Safari等，而且可以通过Selenium的WebDriver来控制这些浏览器。Selenium自己不带浏览器，需要与第三方浏览器结合在一起才能使用[[0\]](https://cloud.tencent.com/developer/article/1800146)[[3\]](https://blog.csdn.net/wakaicon/article/details/109098036)。
>
> Chrome的Headless模式是Chrome浏览器的一种运行模式，也称为无头浏览器。在无头模式下，Chrome浏览器在后台运行，用户无法看到浏览器界面。无头浏览器通常用于自动化测试、网络爬虫等场景。Chrome浏览器的无头模式可以通过Selenium的WebDriver来控制[[0\]](https://cloud.tencent.com/developer/article/1800146)[[1\]](https://blog.csdn.net/bqw18744018044/article/details/81351137)[[2\]](https://cloud.tencent.com/developer/article/1740262)[[3\]](https://blog.csdn.net/wakaicon/article/details/109098036)。
>
> Selenium可以与Chrome的无头模式（Headless）混为一谈，因为Selenium可以通过ChromeDriver来控制Chrome浏览器的无头模式。此外，Selenium还支持其他浏览器的无头模式，比如Firefox、PhantomJS等[[0\]](https://cloud.tencent.com/developer/article/1800146)[[2\]](https://cloud.tencent.com/developer/article/1740262)[[3\]](https://blog.csdn.net/wakaicon/article/details/109098036)。

## 2.Headless Chrome

### 2.1 环境搭建

#### 2.1.1 安装Chrome

Windows : 直接在 ~~[Chrome](https://www.google.cn/chrome/index.html)~~/[Chromium ](https://chromiumdash.appspot.com/schedule) 官网下载。推荐使用 Chromium  开箱即用。

历史版本: 

* [Chromium历史版本第三方版本统计](https://vikyd.github.io/download-chromium-history-version/#/): 基本包括驱动版本
* [官方历史快照版](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html): 基本包括驱动版本
  1. 选择您的平台：Mac、Win、Linux、ChromiumOS
  2. 选择您想要使用的 Chromium 内部版本号
     1. `LAST_CHANGE`文件中提到了最新的
  3. 下载包含 Chromium 的 zip 文件
  4. 里面有一个二进制可执行文件可以运行

如何指定打开的浏览器，而不是默认的浏览器 :  

1. 通过将驱动放在你浏览器所在的文件夹内，并设置 `System.setProperty("webdriver.chrome.driver","驱动地址");` 即可
2. 通过代码进行设置

```java
System.setProperty("webdriver.chrome.driver","bin path:驱动地址")
ChromeOptions options = new ChromeOptions();
options.setBinary("bin path: 浏览器的执行文件");
ebDriver driver = new ChromeDriver(options);
```





----
**linux： 请百度，我使用的是Ubuntu 所以我直接百度的 Ubuntu Ser ver 安装 Chrome**

-----



Linux : 安装chrome headless  **CHATGPT**

Chrome Headless实际上是Chrome浏览器的无界面版本，可以通过命令行进行操作。以下是在Linux系统中安装Chrome Headless的步骤：

1. 确认系统已安装Google Chrome浏览器，如果没有，可以使用以下命令安装：

   ```
   sqlCopy codesudo apt update
   sudo apt install google-chrome-stable
   ```

2. 安装Chromium浏览器所需的依赖项：

   ```
   sqlCopy codesudo apt update
   sudo apt install -y unzip libgconf-2-4 libnss3-dev libfontconfig1
   ```

3. 下载ChromeDriver：

   ```
   bashCopy codewget https://chromedriver.storage.googleapis.com/91.0.4472.101/chromedriver_linux64.zip
   unzip chromedriver_linux64.zip
   sudo mv chromedriver /usr/local/bin/
   sudo chown root:root /usr/local/bin/chromedriver
   sudo chmod +x /usr/local/bin/chromedriver
   ```

4. 启动Chrome浏览器，并设置Headless模式：

   ```
   cssCopy code
   google-chrome-stable --headless --disable-gpu --no-sandbox --remote-debugging-port=9222
   ```

5. 现在您可以使用Chrome浏览器和Headless模式进行操作。例如，您可以使用Selenium WebDriver进行自动化测试或网络爬虫。

注意：请注意安全问题。在生产环境中使用Chrome Headless时，请确保采取适当的安全措施，如限制访问Chrome浏览器的端口或禁用某些Chrome浏览器的功能。



#### 2.1.2 启动浏览器

打开 Chromestatus  网站

```
# chrome 是二进制文件不是命令，你可以打开Chrome的安装目录找到
# 或者新建一个 Chrome 快捷方式，在快捷方式的属性里找到 快捷方式-目标 添加以下参数
# --user-data-dir=D:\Chrome\user1 这是设置缓存目录的参数
chrome --headless --disable-gpu --remote-debugging-port=9222 https://www.chromestatus.com
```



#### 2.1.3 下载chromedriver

找到对应浏览器版本的驱动
淘宝镜像网站：http://npm.taobao.org/mirrors/chromedriver/

官方网站：http://chromedriver.storage.googleapis.com/index.html



#### 2.1.4 配置 chromedriver 的环境变量

在环境变量里面的 Path 添加 chromedriver 所在文件夹的路径。注意可以不设置



#### 2.1.5 添加依赖

注意第一个的版本必须是最新的否则可能与驱动不匹配。第二个依赖可以不引入，如果发生了异常再引入也可以

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <!--<version>3.4.0</version>-->
    <version>4.7.2</version>
</dependency>


<!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>24.1-jre</version>
</dependency>
```

#### 2.1.6 代码启动浏览器

https://blog.csdn.net/qq_22003641/article/details/79137327

https://blog.csdn.net/dengjie811227/article/details/102292146

https://www.cnblogs.com/eastonliu/p/9102239.html

https://blog.csdn.net/erhuobuer/article/details/108680617

```java
//驱动地址: 如果不设置环境变量则需要设置驱动地址
//如果想要打开特点的浏览器，就把驱动放在那个浏览器的所在文件夹内，比如要打开Chromium 就放在 Chromium 的文件夹内
System.setProperty("webdriver.chrome.driver","/chromedriver");
// 设置驱动
ChromeOptions options=new ChromeOptions();
//设置 chrome 的无头模式
options.addArguments("--headless");
options.addArguments("--disable-gpu");
options.addArguments("--no-sandbox");
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--start-maximized");
//因为报表页面必须滚动才能全部展示，这里直接给个很大的高度
options.addArguments("--window-size=1280,4300");
//设置用户目录
options.addArguments(" --user-data-dir=D:\\Chrome\\user1");
// 创建webdriver驱动
WebDriver driver = new ChromeDriver(options);

//注意 UA为 User-Agent: selenium/4.8.3 (java windows)
// 访问网页
webDriver.get(url);
// Selenium提供了8种定位方式。请通过 By这个类查看
//clear() 清除文本。sendKeys(*value) 模拟按键输入。click() 单击元素。submit()方法用于提交表单
// 获取输入框，输入selenium
driver.findElement(By.id("kw")).sendKeys("selenium");
// 获取“百度一下”按钮，进行搜索
driver.findElement(By.id("su")).click();


//模拟键盘
//WebElement 对象.sendKeys(Keys.SPACE)//注意可以用组合键

//执行javascript 可以直接设置localstroage,cookie等方式。不过拼字符串实在太麻烦
String token = "localStorage.setItem('cloud.token','{\"val\":\"5d31f455-8ed5-4ebe-80d1-760665df452c\",\"expires\":1584531900299}')";
((JavascriptExecutor)webDriver).executeScript(token);

//定位section元素
WebElement element =  webDriver.findElement(By.tagName("section"));
Point p = element.getLocation();
int width = element.getSize().getWidth();
int height = element.getSize().getHeight();
Rectangle rec = new Rectangle(p.getX(),p.getY(),height,width);

//截取全屏
File scrFile  = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
//在全屏图片下裁剪
BufferedImage img = ImageIO.read(scrFile);
BufferedImage dest = img.getSubimage(p.getX(), p.getY(),rec.getWidth(),rec.getHeight());
ImageIO.write(dest, "png", scrFile);
//拷贝文件
FileUtils.copyFile(scrFile , new File("a.png"));
log.info("截图完成");
//退出
webDriver.quit();
```



```java
WebDriver driver = new ChromeDriver();    //Chrome浏览器
WebDriver driver = new FirefoxDriver();   //Firefox浏览器
WebDriver driver = new EdgeDriver();      //Edge浏览器
WebDriver driver = new InternetExplorerDriver();  // Internet Explorer浏览器
WebDriver driver = new OperaDriver();     //Opera浏览器
WebDriver driver = new PhantomJSDriver();   //PhantomJS
```





### 2.2 报错

如果报错请首要排查三个原因

* chrome 版本是否与驱动大版本一致，建议都升级为最新版
* java 引入的jar库是否是最新版

* `Exception in thread "main" org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Response code 500. Message: unknown error: failed to write first run file` 

> 这个异常通常是由于Selenium驱动程序无法启动浏览器会话导致的，可能是由于驱动程序版本与浏览器版本不兼容，或者是由于缺少浏览器二进制文件或用户权限不足等原因。 如果你设置了用户缓存目录请给予java程序权限

* `org.openqa.selenium.UnhandledAlertException: unexpected alert open: {Alert text : Hello World!}`

> 这个异常通常是由于在执行测试期间，页面上出现了未预期的弹窗，例如警告框、确认框或提示框等。
>
> 要解决这个问题，您可以尝试以下几个步骤：
>
> 1. 使用`switchTo().alert()`方法切换到警告弹窗并处理它。例如，您可以使用`accept()`方法点击弹窗上的“确定”按钮，或使用`dismiss()`方法点击弹窗上的“取消”按钮。
> 2. 在执行任何可能引发警告弹窗的操作之前，可以使用`driver.switchTo().alert()`方法来检查页面是否有警告弹窗。如果存在，可以通过上述方法来处理它。

* `NoSuchElementException`：当尝试访问不存在的元素时，会引发此异常。
* `TimeoutException`：当 Selenium 超时并无法在指定时间内找到所需的元素或页面元素时，会引发此异常。
* `StaleElementReferenceException`：当尝试访问一个已经被删除或不再可用的元素时，会引发此异常。
* `ElementNotVisibleException`：当尝试访问一个隐藏的元素时，会引发此异常。
* `ElementNotInteractableException`：当尝试与无法与之交互的元素进行交互时，会引发此异常。
* `InvalidSelectorException`：当选择器无效或不符合语法规则时，会引发此异常
* `NoSuchWindowException`：当尝试访问不存在的窗口时，会引发此异常。
* `UnhandledAlertException`：当出现未处理的警告弹窗时，会引发此异常。
* `SessionNotCreatedException`：当浏览器会话无法启动时，会引发此异常。
* `WebDriverException`：这是一个通用异常，可能由各种原因引起，例如网络连接问题、代理设置错误等。
* `SessionNotCreatedException`：当浏览器会话无法创建或启动时，会引发此异常。可能是因为缺少驱动程序或浏览器版本不兼容等原因。
* `TimeoutException`：当 Selenium 无法在指定的时间内执行操作时，会引发此异常。例如，当页面加载过慢或元素不可见时。
* `NoSuchWindowException`：当尝试访问不存在的浏览器窗口时，会引发此异常。
* `NoSuchSessionException`：当尝试访问已经关闭的浏览器会话时，会引发此异常。
* `ElementNotVisibleException`：当尝试访问不可见的元素时，会引发此异常。
* `ElementNotSelectableException`：当尝试选择不支持选择操作的元素时，会引发此异常。
* `InvalidSelectorException`：当选择器无效或不符合语法规则时，会引发此异常。
* `JavascriptException`：当在 JavaScript 代码中发生错误时，会引发此异常。
* `UnhandledAlertException`：当出现未处理的警告弹窗时，会引发此异常。

### 2.3 项目实例

#### 2.3.0 设置WebDriver 

`DesiredCapabilities` 和 `ChromeOptions` 都是用于设置 WebDriver 的选项和配置的类。它们之间的区别在于：

1. `DesiredCapabilities` 是 Selenium WebDriver 中的一个通用选项类，可以用于设置所有支持的浏览器的选项。而 `ChromeOptions` 是针对 Chrome 浏览器的选项类，只能用于设置 Chrome 浏览器的选项。
2. `DesiredCapabilities` 可以设置的选项包括浏览器类型、版本、操作系统、页面加载策略、代理等。而 `ChromeOptions` 可以设置的选项包括二进制文件路径、启动参数、扩展、调试端口等。
3. 在使用 Chrome 浏览器时，您通常需要使用 `ChromeOptions` 来设置选项，例如指定浏览器的二进制文件路径或设置启动参数。而在使用其他浏览器时，您可能需要使用 `DesiredCapabilities` 来设置选项。

```java
// 创建一个 ChromeOptions 对象
ChromeOptions options = new ChromeOptions();
// 设置 Chrome 浏览器的二进制文件路径
options.setBinary("/usr/bin/google-chrome");
// 设置启动参数
options.addArguments("--disable-extensions");

// 创建一个 DesiredCapabilities 对象
DesiredCapabilities capabilities = DesiredCapabilities.chrome();
// 设置 ChromeOptions 选项
capabilities.setCapability(ChromeOptions.CAPABILITY, options);

// 使用 DesiredCapabilities 对象创建 ChromeDriver
// 也可以使用 options 直接设置
WebDriver driver = new ChromeDriver(capabilities);

// 访问网站
driver.get("https://www.example.com");

// 关闭浏览器
driver.quit();
```



#### 2.3.1 控制浏览器

```java
//控制浏览器
// 设置全屏
driver.manage().window().fullscreen();
//获取当前窗口相对于屏幕左上角的位置。
System.out.println(driver.manage().window().getPosition());
//获取浏览器大小
System.out.println(driver.manage().window().getSize());
//最小化
driver.manage().window().minimize();
//最大化
driver.manage().window().maximize();
//设置窗口大小
driver.manage().window().setSize(...);
```

#### 2.3.2 模拟鼠标

* `ActionChains` 模拟鼠标

* `Actions action = new Actions(driver);`

* `contextClick()` 右击

* `clickAndHold()` 鼠标点击并控制

* `doubleClick()` 双击

* `dragAndDrop()` 拖动

* `release()` 释放鼠标

* `dragAndDrop(source, target)：`模拟鼠标拖拽 拖拽到某个元素然后松开

* `dragAndDropBy(source, xoffset, yoffset)：`模拟鼠标拖拽 拖拽到某个坐标然后松开

* `moveToElement()` 模拟鼠标悬停

* `clickAndHold()` 方法用于模拟鼠标悬停操作， 在调用时需要指定元素定位

* `perform()` 执行所有Actions中存储的行为

* `build()` 构建所有操作

* 模拟鼠标移动 它是模拟鼠标移动而不是真实的鼠标移动
  * `moveByOffset()`
  * `moveToElement`
  * 示例
  * `action.clickAndHold(WebElement对象).build().perform();`

#### 2.3.3 历史记录

```java
//允许驱动程序访问浏览器的历史记录并导航到给定的URL。
WebDriver.Navigation navigate = driver.navigate();
navigate.refresh();//刷新
navigate.back();//回退
navigate.forward();//向前
navigate.to(..);//跳转URL
```

#### 2.3.4 窗口切换

```java
//获取当前打开窗口的所有句柄
Set<String> handles = driver.getWindowHandles();
//获取当前窗口的句柄(String类型)
String handle = driver.getWindowHandle();
//跳转页面，通过窗口句柄，这里使用第一个句柄
driver.switchTo().window(handles.stream().findFirst().orElse(""));
//新开一个窗口
driver.switchTo().newWindow(WindowType.TAB);
driver.get("https://bilibili.com");
```



#### 2.3.5 Cookie

```java
//Cookie 操作
Set<Cookie> cookies = driver.manage().getCookies();
driver.manage().addCookie(null);
driver.manage().deleteAllCookies();
driver.manage().deleteCookieNamed("");
driver.manage().getCookieNamed("");
```



#### 2.3.6 等待页面加载

我们经常会碰到用[selenium](https://so.csdn.net/so/search?q=selenium&spm=1001.2101.3001.7020)操作页面上某个元素的时候， 需要等待页面加载完成后， 才能操作。 否则页面上的元素不存在，会抛出异常。或者碰到[AJAX](https://so.csdn.net/so/search?q=AJAX&spm=1001.2101.3001.7020)异步加载，我们需要等待元素加载完成后， 才能操作。

##### 2.3.6.1 页面加载策略

默认Chrome 浏览器下 **所有的元素定位是在页面被完全加载后（页面tab不再转圈）才开始。执行 get 后的代码**

在 Selenium 中，可以通过 `WebDriver` 接口的 `manage()` 方法来设置页面加载策略。页面加载策略是指当页面处于加载状态时，WebDriver 应该等待多长时间才应该抛出 `TimeoutException` 异常。以下是几种页面加载策略：

1. `normal`：这是默认的页面加载策略。它会等待页面加载完毕，并在超时时间内等待。
2. `eager`：这个策略会尝试尽快地完成页面加载，并在超时时间内等待。如果页面在超时时间内没有加载完毕，它会抛出 `TimeoutException` 异常。
3. `none`：这个策略不会等待页面加载完毕，并立即返回。如果您想在页面加载完毕之前执行某些操作，则可以使用这个策略。请注意，如果您在等待操作之前尝试访问页面元素，则可能会抛出 `StaleElementReferenceException` 异常。

**1. 元素出现但页面未加载完毕**

首选需要明白的一点是，如果什么都不设置，通常，以chrome浏览器为例，所有的元素定位是在页面被完全加载后（页面tab不再转圈）才开始。

有时候其实想要的元素已经加载出来了，只是页面还在加载其他东西，例如图片，此时若不想继续等待直接执行元素定位操作，则需要在创建driver的时候设置页面加载策略：

当调用driver.get("https://xxxx.xxx.xxx")来访问某页面时，get方法通常会阻塞浏览器直到页面完全加载后才执行后面的动作，若一个页面加载过慢，则会导致get方法一直阻塞。有时候希望页面在加载过程中就开始检测元素是否存在，而不是等到页面加载完了才开始检测，想要实现这个效果，可以用~~DesiredCapabilities类下的setPageLoadStrategy方法（Python，Chrome浏览器）~~ 设置页面加载策略为 `none`，以便在页面加载期间执行其他操作。接下来，我们使用 `WebDriverWait` 类等待元素的可见性。在这里，我们等待了最多 10 秒钟，直到元素可见。如果元素在这个时间内没有可见，它会抛出 `TimeoutException` 异常。

```java
ChromeOptions options = new ChromeOptions();
options.setPageLoadStrategy(PageLoadStrategy.NONE);
WebDriver driver = new ChromeDriver(options);

driver.get("https://example.com");

//等待页面30s
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//指定要等待的元素。
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-id")));
System.out.println("Page element is visible: " + element.isDisplayed());

driver.quit();
```



##### 2.3.6.2 隐示等待

隐式等待是一种全局等待，它在 WebDriver 对象实例化后设置一次即可，对于整个 WebDriver 实例的生命周期都起作用。

隐式等待的作用是让 WebDriver 在查找页面元素时等待一段时间，如果在等待时间内找到了指定的元素，则立即执行后面的操作。如果等待时间结束仍未找到指定的元素，则抛出 `NoSuchElementException` 异常。这种等待方式适用于那些需要加载一些资源（例如 JavaScript、图片等）的页面，因为这些资源可能会影响页面元素的加载速度。`driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);`

```java
// 设置 Chrome 浏览器驱动路径
System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
// 创建 ChromeDriver 实例
WebDriver driver = new ChromeDriver();

// 设置隐式等待时间为 10 秒
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

// 访问网站
driver.get("https://www.example.com");

// 查找并操作页面元素
WebElement element = driver.findElement(By.id("example-id"));
element.click();

// 关闭浏览器
driver.quit();
```



##### 2.3.6.3 显示等待

显示等待是一种精确控制等待时间的等待方式，它可以让我们在指定时间内等待某个条件的成立。与隐式等待不同，显式等待是在代码中手动编写的，需要指定等待的最长时间，以及判断条件的类型和条件值。当等待时间内判断条件成立，则立即执行后续的操作，否则在等待时间到达后抛出异常。

Selenium WebDriver 提供了一个名为 `WebDriverWait` 的类来实现显式等待。使用 `WebDriverWait` 可以指定等待的最长时间和等待条件，常用的等待条件包括 `elementToBeClickable`、`presenceOfElementLocated`、`visibilityOfElementLocated` 等等。

```java
// 设置 Chrome 浏览器驱动路径
System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
// 创建 ChromeDriver 实例
WebDriver driver = new ChromeDriver();

// 访问网站
driver.get("https://www.example.com");

// 显式等待页面元素加载完成
WebDriverWait wait = new WebDriverWait(driver, 10);
//ExpectedConditions.visibilityOfElementLocated 方法来指定等待条件为指定元素的可见性。如果等待时间内指定元素的可见性成立，则 wait.until 方法立即返回找到的页面元素，并执行后续的操作；如果等待时间到达后仍未找到指定元素，则抛出 TimeoutException 异常。
//ExpectedConditions 提供的各种判断条件。WebElement element = wait.until(ExpectedConditions.condition());
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-id")));

// 对页面元素进行操作
element.click();

// 关闭浏览器
driver.quit();
```



##### 2.3.6.4 流畅等待

`FluentWait`是WebDriver提供的另一种等待方式，用于在指定时间内以一定的时间间隔不断地进行条件判断，直到超时或者条件成立为止。与`WebDriverWait`相比，`FluentWait`更加灵活，可以根据具体需求自定义等待时间和等待条件，适用于一些比较特殊的场景。

使用`FluentWait`需要先定义等待条件（`ExpectedCondition`对象）和等待时间间隔，然后将它们传入`FluentWait`的构造方法中，最后调用`until`方法即可。与显示等待的 WebDriverWait类似，区别是WebDriverWait已经设置好几个等待条件，而流畅等待 FluentWait可以自己设置等待条件。

```java
FluentWait<WebDriver> wait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(10)) // 设置超时时间为10秒
        .pollingEvery(Duration.ofSeconds(1)) // 每隔1秒检测一次
        .ignoring(NoSuchElementException.class); // 忽略NoSuchElementException异常

WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-id")));

```



#### 2.3.7 定位方式

1. 根据元素ID进行定位

```java
driver.findElement(By.id("element-id"));
```

2. 根据元素名称进行定位

```java
driver.findElement(By.name("element-name"));
```

3. 根据链接文本进行定位

```java
driver.findElement(By.linkText("link-text"));
```

4. 根据部分链接文本进行定位

```java
driver.findElement(By.partialLinkText("partial-link-text"));
```

5. 根据元素标签名称进行定位

```java
driver.findElement(By.tagName("tag-name"));
```

6. 根据CSS选择器进行定位

```java
driver.findElement(By.cssSelector("selector"));
```

7. 根据XPath表达式进行定位

```java
driver.findElement(By.xpath("xpath-expression"));
```

8. 根据元素类名进行定位

```java
driver.findElement(By.className("class-name"));
```

需要注意的是，不同的定位方式的效率和适用场景也不同，需要根据实际情况选择合适的定位方式。另外，还可以使用复合定位方式，将多种定位方式组合使用来定位元素。

##### 2.3.7.1 css定位

根据CSS选择器进行定位是Selenium中常用的一种方式，可以通过使用 `By.cssSelector()` 方法实现。

在使用CSS选择器进行定位时，可以通过元素的id、class、属性和标签名称等信息来定位元素。下面是一些常用的CSS选择器示例：

1. 通过id定位元素

```
driver.findElement(By.cssSelector("#element-id"));
```

2. 通过class定位元素

```
driver.findElement(By.cssSelector(".element-class"));
```

3. 通过属性定位元素

```
driver.findElement(By.cssSelector("[attribute-name='attribute-value']"));
```

4. 通过标签名称定位元素

```
driver.findElement(By.cssSelector("tag-name"));
```

5. 通过组合选择器定位元素

```
driver.findElement(By.cssSelector("tag-name#element-id.attribute-name[attribute-value]"));
```

6. 子选择器

使用`>`符号来选择作为某元素子元素的元素，例如：

```
driver.findElement(By.cssSelector("parent-element > child-element"));
```

7. 后代选择器

使用空格符号来选择某元素后代元素，例如：

```
driver.findElement(By.cssSelector("parent-element child-element"));
```

8. 相邻兄弟选择器

使用`+`符号来选择某元素之后相邻的兄弟元素，例如：

```
driver.findElement(By.cssSelector("preceding-element + sibling-element"));
```

9. 通用兄弟选择器

使用`~`符号来选择某元素之后的兄弟元素，例如：

```
driver.findElement(By.cssSelector("preceding-element ~ sibling-element"));
```

需要注意的是，在使用CSS选择器进行定位时，需要确保选择器的唯一性，否则可能会定位到不符合预期的元素。同时，还需要注意选择器的复杂度和性能问题。在定位复杂的元素时，建议优先考虑使用XPath表达式进行定位。



#### 2.3.8 获取页面源代码

1. Java 

```java
driver.getPageSource()
```

2. JS

```java
// 注入JavaScript脚本
String script = "return document.documentElement.outerHTML;";
String pageSource = (String) ((JavascriptExecutor) driver).executeScript(script);
```



#### 2.3.9 打开相同的浏览器（来自于网络）

在使用 Selenium 进行自动化测试时，如果希望在每次运行脚本时打开相同的浏览器，可以使用 WebDriver 的 `SessionId` 属性来实现。

首先，打开浏览器并执行测试代码，可以通过 `driver.getSessionId()` 方法获取当前浏览器会话的 `SessionId` 值。然后，在下一次运行测试脚本时，可以使用 `WebDriver` 的 `attach` 方法，传入上一次运行时获取的 `SessionId` 值，即可重新打开相同的浏览器并继续测试。

以下是使用 Java 实现上述方法的示例代码：

```java
// 第一次打开浏览器并执行测试代码
WebDriver driver = new ChromeDriver();
driver.get("https://www.example.com");
String sessionId = driver.getSessionId().toString();
driver.quit();

// 下一次打开相同的浏览器并继续测试
ChromeOptions options = new ChromeOptions();
options.setExperimentalOption("debuggerAddress", "localhost:9222");
WebDriver driver = new ChromeDriver(options);
driver = new ChromeDriver(options);
driver.get("https://www.example.com");
RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
remoteWebDriver.executeScript("window.open('about:blank', '_blank');");
remoteWebDriver.switchTo().window(remoteWebDriver.getWindowHandles().stream().skip(1).findFirst().get());
remoteWebDriver.get("http://localhost:9222/json/new?"+sessionId);
remoteWebDriver.switchTo().window(remoteWebDriver.getWindowHandles().stream().findFirst().get());
```

需要注意的是，在上述代码中，需要事先在命令行启动一个 Chrome 浏览器实例，并启用远程调试模式。在启动浏览器实例时，需要使用 `--remote-debugging-port` 参数指定一个空闲的端口号，如 `9222`。在运行测试脚本时，需要将 `ChromeOptions` 实例的 `debuggerAddress` 属性设置为 `localhost:9222`，表示连接到远程调试端口。在连接上一个已有的浏览器实例后，需要调用 `window.open()` 方法打开一个新的空白窗口，并切换到该窗口，最后通过 `http://localhost:9222/json/new?sessionId` URL 加载之前会话的 `SessionId` 值，以恢复之前浏览器会话的状态。



#### 2.3.10 [启动参数设置](https://www.cnblogs.com/ugvnui/p/11919073.html)

```java
// Chrome
options = new ChromeOptions();
// 启动就最大化
// options.addArguments("start-fullscreen");
// options.addArguments("--start-maximized");
// 禁用浏览器弹出窗口拦截器。
options.addArguments("--disable-popup-blocking");
// 取消沙盘模式
options.addArguments("no-sandbox");
//禁用 Chrome 的 /dev/shm 空间，在一些低配服务器上可能会导致 Chrome 崩溃。
options.addArguments("--disable-dev-shm-usage");
// 禁止扩展
options.addArguments("disable-extensions");
// 禁止默认浏览器检查
options.addArguments("no-default-browser-check");
options.addArguments("about:histograms");
options.addArguments("about:cache");
// 设置浏览器固定大小
// 注意：设定了浏览器固定大小后，浏览器打开后浏览器的位置可能会变到其他位置，因此可以使用设置刘浏览器的位置方法和设置浏览器的大小方法一起使用；driver.manage().window().maximize();
options.addArguments("--window-size=1600,900");
// chrome正受到自动测试软件的控制
options.addArguments("disable-infobars");

//谷歌插件 注意对于路径都要使用 new File(...).getCanonicalPath();或者使用相对路径
options.addExtensions(new File("/path/to/extension.crx"))
options.setBinary(new File("/path/to/chrome"));
// 禁用 GPU 硬件加速，可以避免一些图形渲染问题和内存泄漏问题。
options.addArguments("--disable-gpu");
//用户工作目录（缓存地址）
options.addArguments("--user-data-dir="+new File("./cache/user/user1").getCanonicalPath());

//每当我们使用selenium启动chrome浏览器时，将为每个新会话创建一个新实例/临时配置文件。如果我们要加载默认的Chrome浏览器或自定义Chrome配置文件，
//我们可以将'user-data-dir'参数传递给ChromeOptions，这是Chrome命令行切换，告诉Chrome使用哪个配置文件。如果路径不存在，chrome将在指定的路径中创建新的配置文件。
options.addArgument("user-data-dir=/path/to/your/custom/profile");

//无头浏览器
options.addArguments("--headless");
//禁用浏览器的同源策略，允许在同一网站内加载来自多个域的资源。
options.addArguments("--disable-web-security");
//设置浏览器的 User-Agent 字符串，可以用来伪装浏览器类型和版本。
options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

//性能优化
options.addArguments("--disable-extensions");//禁用 Chrome 扩展程序。
options.addArguments("--no-sandbox");//高危
options.addArguments("--blink-settings=imagesEnabled=false");//禁用浏览器中的图片加载，可以加快浏览器的渲染速度。
options.addArguments("--disable-features=IsolateOrigins,site-per-process");//高危
options.addArguments("--disable-features=VizDisplayCompositor");//高危
options.addArguments("--disable-features=RendererCodeIntegrity");//高危
options.addArguments("--disable-blink-features=AutomationControlled");// 高危
options.addArguments("--mute-audio");// 禁用浏览器声音。
options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); //禁用自动化标识
options.setExperimentalOption("useAutomationExtension", false);// 禁用拓展程序
//不禁用 CSS 的情况下，将网页加载为无样式的版本，从而提高性能。注意这设置了UA
options.addArguments("--user-agent=Googlebot");
options.addArguments("--disable-javascript");//禁用JavaScript
options.addArguments("--disable-blink-features=CSSAnimations");//禁用CSS动画
options.addArguments("--disable-blink-features=CSSTransitions");//禁用CSS过渡
```

> 其他
>
> - `--disable-notifications`: 禁用浏览器通知。
>
> - `--disable-popup-blocking`: 禁用浏览器弹出窗口拦截器。
>
> - `--disable-default-apps`: 禁用默认应用程序。
>
> - `--hide-scrollbars`: 隐藏浏览器滚动条。
>
> - `--mute-audio`: 禁用浏览器声音。
>
> - `--log-level=3`: 设置浏览器的日志级别为 `WARNING`。
>
> - `--disable-logging`: 禁用浏览器日志输出。
>
> - `setExperimentalOption("useAutomationExtension", false)`: 禁用 Chrome 扩展程序，用于避免一些自动化测试过程中出现的问题。
>
> - `setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))`: 禁用 Chrome 的自动化开关，也是用于避免自动化测试过程中出现的问题。这行代码会禁用 Chrome 的自动化开关。当启用自动化测试时，Chrome 会自动开启一个特殊的标志“enable-automation”，这个标志可能会导致一些网站行为异常或者被检测到并阻止自动化测试。因此，在执行自动化测试时，禁用此开关可以避免这些问题的发生。
>
> - `setExperimentalOption("prefs", prefs)`: 设置 Chrome 的偏好选项。方法可以设置 Chrome 浏览器的偏好选项。在这个方法中，`prefs`参数是一个 `Map<String, Object>` 对象，其中包含一些键值对，用于设置浏览器的偏好选项。
>
>   偏好选项可以用来控制浏览器的一些行为，例如下载文件的默认路径、启用 Flash 插件、禁用自动填充表单等。通过设置偏好选项，可以使自动化测试脚本更加灵活和可控。
>
> - `--disable-browser-side-navigation`: 禁用浏览器侧边导航。
>
> - `--disable-features=VizDisplayCompositor`: 禁用 Chrome 的某个功能，可以提高 Chrome 的性能。这个功能用于渲染页面的图形界面，禁用它可以提高 Chrome 的性能，但可能会影响页面的显示效果。
>
> - `--disable-features=RendererCodeIntegrity`: 禁用 Chrome 的某个功能，可以提高 Chrome 的性能。这个功能用于保护渲染器进程不被攻击者利用，但它会消耗一些额外的性能，禁用它可以提高 Chrome 的性能。
>
> - `--blink-settings=imagesEnabled=false`: 禁用浏览器中的图片加载，可以加快浏览器的渲染速度。
>
> - `--disable-blink-features=AutomationControlled`: 禁用 Blink 引擎自动化控制的功能。可以禁用 Chrome 浏览器中 Blink 引擎的自动化控制功能，使得一些原本受到限制的操作变得可行。但需要注意的是，禁用这个功能可能会导致一些不安全的操作被执行，因此需要谨慎使用。
>
> - `--disable-features=IsolateOrigins,site-per-process`: 禁用 Chrome 的网站隔离和进程隔离功能，可以提高性能和减少内存占用。
>
> - `--remote-debugging-port=9222`: 开启 Chrome 的远程调试端口。
>
> - `--disable-extensions`: 禁用 Chrome 扩展程序。
>
> - `--disable-web-security`: 禁用 Chrome 的同源策略，允许在同一网站内加载来自多个域的资源。
>
> - ~~`--disable-features=CSSGridLayout`。启用该参数后，Chrome 浏览器将不会使用 CSS 网格布局来排版页面，可能会提高页面加载速度和浏览器性能，但也会导致网页排版样式异常~~。
>
> 

#### 2.3.11 执行 JS

在Java中使用Selenium执行JavaScript可以使用`JavascriptExecutor`接口。这个接口可以在Selenium WebDriver对象上使用

```jav
System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
WebDriver driver = new ChromeDriver();

driver.get("http://www.example.com");

JavascriptExecutor js = (JavascriptExecutor) driver;

// 在页面上执行JavaScript
js.executeScript("alert('Hello World!');");

driver.quit();
```

如何处理js的返回值: 接收返回值，根据 Class 进行强制转换

```java
Object o = js.executeScript("return 1+1");
//class java.lang.Long
System.out.println(o.getClass());
if (o instanceof  Long){
    Long l = (Long)o;
    // ........
}
```



#### 2.3.12 关闭日志

您使用的是其他日志框架，例如 `log4j` 或 `logback`，则需要查找该框架提供的特定方法或配置来关闭日志记录。通常，这些框架都提供了一些级别配置，您可以将其设置为“OFF”或“ERROR”来关闭日志记录。



#### 2.3.13 截图

```java
//截取全屏
File scrFile  = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
//在全屏图片下裁剪
BufferedImage img = ImageIO.read(scrFile);
BufferedImage dest = img.getSubimage(p.getX(), p.getY(),rec.getWidth(),rec.getHeight());
ImageIO.write(dest, "png", scrFile);
//拷贝文件
FileUtils.copyFile(scrFile , new File("a.png"));
log.info("截图完成");
```

* `File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);`

这行代码使用了 `TakesScreenshot` 接口中的 `getScreenshotAs` 方法来获取元素的屏幕截图。 `getScreenshotAs` 方法接受一个参数，即 `OutputType`，用于指定截图的类型。在这里，我们使用了 `OutputType.FILE` 来指定截图以文件形式返回。

`TakesScreenshot` 接口是一个由 WebDriver 实现的接口，它允许您获取 WebDriver 实例的屏幕截图。通过将 WebDriver 实例转换为 `TakesScreenshot` 接口类型，您可以调用 `getScreenshotAs` 方法来获取屏幕截图。但是，请注意，如果您传递的是一个 WebElement 对象而不是 WebDriver 实例，那么将会截取该元素的屏幕截图，而不是整个页面的截图。

另外，由于 `getScreenshotAs` 方法返回的是一个 `File` 类型的对象，您需要使用 Java 的 `FileUtils` 类或其他类库来将截图保存到本地文件中。在上面的代码中，我们使用了 Apache Commons IO 库中的 `FileUtils` 类来复制文件



#### 2.3.14 插件（来自网络）

控制浏览器插件的方式取决于您使用的浏览器和插件类型。下面是一些常见浏览器插件的控制方式：

1. AdBlock Plus：使用 AdBlock Plus 的 `org.openqa.selenium.chrome.ChromeOptions` 类和 `addExtensions` 方法来加载插件。示例代码如下：

```
javaCopy codeChromeOptions options = new ChromeOptions();
options.addExtensions(new File("/path/to/adblockplus.crx"));
WebDriver driver = new ChromeDriver(options);
```

1. uBlock Origin：使用 uBlock Origin 的 `org.openqa.selenium.chrome.ChromeOptions` 类和 `addExtensions` 方法来加载插件。示例代码如下：

```
javaCopy codeChromeOptions options = new ChromeOptions();
options.addExtensions(new File("/path/to/ublockorigin.crx"));
WebDriver driver = new ChromeDriver(options);
```

1. Firebug：使用 Firefox 的 `org.openqa.selenium.firefox.FirefoxProfile` 类和 `addExtension` 方法来加载插件。示例代码如下：

```
javaCopy codeFirefoxProfile profile = new FirefoxProfile();
File firebug = new File("/path/to/firebug.xpi");
File netExport = new File("/path/to/netExport.xpi");
profile.addExtension(firebug);
profile.addExtension(netExport);
WebDriver driver = new FirefoxDriver(profile);
```

在上面的代码中，我们使用了 Firefox 的 `addExtension` 方法来加载 Firebug 插件和 NetExport 插件。

请注意，加载浏览器插件可能会对浏览器性能产生一定影响，因此请谨慎使用。此外，不同版本的浏览器和插件可能需要不同的加载方式，请根据您的具体情况进行调整。

## 3. HtmlUnit

HtmlUnit 是一个Java 写的 内存浏览器，他没有界面所以对于具体的浏览器方法在此内存浏览器上无法使用，比如截图等。



**坐标:**

如果你使用了最新版，那么包名 com.gargoylesoftware 改为了 org

```xml
//3.0 与 2.x 版本是不兼容的
<!--<dependency>-->
<!--    <groupId>org.htmlunit</groupId>-->
<!--    <artifactId>htmlunit</artifactId>-->
<!--    <version>3.0.0</version>-->
<!--</dependency>-->

<dependency>
    <groupId>net.sourceforge.htmlunit</groupId>
    <artifactId>htmlunit</artifactId>
    <version>2.70.0</version>
</dependency>
```



https://blog.csdn.net/weixin_44151627/article/details/104755508

```java
// 禁用 HtmlUnit 日志记录
//Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
//Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
//Logger.getLogger("com.gargoylesoftware.htmlunit.IncorrectnessListenerImpl").setLevel(Level.OFF);
//Logger.getLogger("com.gargoylesoftware.htmlunit.WebConsole").setLevel(Level.OFF);
//Logger.getLogger("com.gargoylesoftware.htmlunit.javascript").setLevel(Level.OFF);

WebClient webClient = new WebClient(BrowserVersion.CHROME); //设置模拟浏览器内核，就如打开相应的浏览器一样
webClient.getOptions().setThrowExceptionOnScriptError(false); //当JS执行出错的时候是否抛出异常
webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); //当请求状态不是200时是否抛出异常信息
webClient.getOptions().setActiveXNative(false);
webClient.getOptions().setRedirectEnabled(true);//是否跳转页面
webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
webClient.getOptions().setUseInsecureSSL(true);//忽略ssl认证
webClient.getOptions().setCssEnabled(false);//设置是否启用css
webClient.getOptions().setJavaScriptEnabled(true);//设置是否启用JavaScript
webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持Ajax
webClient.getOptions().setTimeout(2*60*1000); //设置“浏览器”的请求超时时间
webClient.setJavaScriptTimeout(60*1000); //设置JS执行的超时时间
webClient.waitForBackgroundJavaScript(600*1000);
HttpWebConnection httpwebconnection = new HttpWebConnection(webClient);
webClient.setWebConnection(httpwebconnection);
HtmlPage page = webClient.getPage("http://www.baidu.com");
//获取页面的html
System.out.println(page.asXml());

// 关闭 WebClient
webClient.close();
```

### 3.2 执行jS

通过 HtmlPage 的 executeJavaScript(...) 方法来执行js

```java
ScriptResult result = page.executeJavaScript("let a = 'a';a");
```

`ScriptResult` 是执行结果返回值，但是通常不能直接使用需要使用该对象的`getJavaScriptResult` 方法进一步获取到返回值

```java
//返回值的类型默认是Object，如果你确定是什么类型可以直接进行转换
//如果你不知道是什么类型可以打印Class 来确定
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());
```

返回经过js修改过后的页面

```java
HtmlPage newPage = (HtmlPage) page.getWebClient().getCurrentWindow().getEnclosedPage();
```



1. 返回值是字符串

```java
//这里是两条js语句，这里执行到最后一个js语句是将a进行了返回交给了控制台。控制台交给了java
//其他数据类型同理
ScriptResult result = page.executeJavaScript("let a = 'a';a");
System.out.println(result);
//class java.lang.String
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());
```



2. 返回值是Undefined值

```java
ScriptResult result = page.executeJavaScript("console.log(b)");
System.out.println(result);
//此类在JavaScript中实现Undefined值
//class net.sourceforge.htmlunit.corejs.javascript.Undefined
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());

//返回值是数组
if (scriptResult instanceof NativeArray) {
    NativeArray val = (NativeArray) scriptResult;
    //遍历数组
    for (Object i : val) {
        System.out.println(i);
    }
}
```

3. 返回值是数组

```java
ScriptResult result = page.executeJavaScript("let a = [1,2,3];a");
System.out.println(result);
//class net.sourceforge.htmlunit.corejs.javascript.NativeArray
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());
```



4. 返回值是对象

```java
page.executeJavaScript("let b = {a:1,b:2}"); //定义一个变量该变量是个对象
page.executeJavaScript("console.log(b)"); //前端控制台输出一下这个对象
ScriptResult result = page.executeJavaScript("b");//获取这个对象并返回
System.out.println(result);//输出这个接收到的对象
//获取这个对象（执行结果）
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());

//返回值是对象
//class net.sourceforge.htmlunit.corejs.javascript.NativeObject
if (scriptResult instanceof NativeObject) {
    //将对象进行强制转化
    NativeObject scriptableObject = (NativeObject) scriptResult;
    //获取这个对象里面的值，输出一下这个值的类型。这是个Double类型
    //class java.lang.Double
    System.out.println(scriptableObject.get("a", scriptableObject).getClass());
    //将值转为字符串进行输出
    String a = scriptableObject.get("a", scriptableObject).toString();
    String b = scriptableObject.get("b", scriptableObject).toString();
    System.out.println("Name: " + a);
    System.out.println("Age: " + b);
}
```

5. 返回值是箭头函数

```java
ScriptResult result = page.executeJavaScript("let a = ()=>{};a");
System.out.println(result);
//class net.sourceforge.htmlunit.corejs.javascript.ArrowFunction
Object scriptResult = result.getJavaScriptResult();
System.out.println(scriptResult.getClass());
```

### 3.3 浏览器窗口

#### 1. 新建窗口

```java
//打开窗口 openWindow
WebWindow window = webClient.openWindow(new URL("http://www.baidu.com"), "again");
Page windowEnclosedPage = window.getEnclosedPage();
HtmlPage windowEnclosedHtmlPage = (HtmlPage)window.getEnclosedPage();
//返回包含页面的窗口 getEnclosingWindow
WebWindow enclosingWindow = windowEnclosedPage.getEnclosingWindow();
```

#### 2.切换窗口

```java
//切换窗口 setCurrentWindow
webClient.setCurrentWindow(window);
webClient.setCurrentWindow(windowEnclosedPage.getEnclosingWindow());
```

#### 3.查找窗口

```java
//获取窗口
WebWindow again = webClient.getWebWindowByName("again");

//遍历窗口
// 获取所有窗口的列表
List<WebWindow> windows = webClient.getWebWindows();
// 遍历列表，找到需要的窗口
for (WebWindow window0 : windows) {
    if (window0.getEnclosedPage() == page) {
        // 切换到另一个新窗口
        webClient.setCurrentWindow(window0);
        break;
    }
}
```

#### 4. 获取当前窗口

```java
page.getWebClient().getCurrentWindow()
```

#### 5. 返回当前窗口加载的页面

```java
//返回当前加载的页面
HtmlPage newPage = (HtmlPage) page.getWebClient().getCurrentWindow().getEnclosedPage();
```

### 3.4 选择器

通常爬取到html会使用jsoup来进行查找，但是这里就使用了本身的选择器来使用

```java
HtmlElement body = page.getBody();
HtmlElement head = page.getHead();
//儿子节点
DomNodeList<DomNode> childNodes = page.getChildNodes();
Iterable<DomNode> children = page.getChildren();
List<HtmlElement> elementsByAttribute = body.getElementsByAttribute(null,null,null);//根据元素的指定属性名和属性值获取元素，返回一个包含所有匹配的元素的列表
DomElement id = page.getElementById("id");
DomNodeList<DomElement> tag = page.getElementsByTagName("tag");//标签选择器：通过标签名获取元素
DomElement name = page.getElementByName("name");//根据元素的 name 属性值获取元素
HtmlForm formByName = page.getFormByName("name");//根据元素的 name 属性值获取一个表单
HtmlInput inputByName = formByName.getInputByName("name");
HtmlElement elementFromPoint = page.getElementFromPoint(0, 0);//通过X、Y获取在该网页位置上的元素
List<Object> xpath = page.getByXPath("//div[@class='myClass']");//Xpath 类选择器 通过元素的 class 属性获取元素

```

注意：

使用 jsoup时你应该使用`page.getBody().asXml()` 来获取网页内容，以此来减弱发生错误的几率



#### 1.通过ID

打开百度网页并搜索 `你知道什么吗，比如JAVA`

```java
HtmlPage page = webClient.getPage("http://www.baidu.com");
//获取搜索栏
DomElement search = page.getElementById("kw");
search.setAttribute("value","你知道什么吗，比如JAVA");
//或者使用
//HtmlInput input = (HtmlInput) search;
//input.setValueAttribute("你知道什么吗，比如JAVA");


//点击搜索按钮
DomElement button = page.getElementById("su");
button.click();
```

## 4. Selenium+HtmlUnit

[HtmlUnitDriver 是HtmlUnit](http://htmlunit.sourceforge.net/)无头浏览器的 WebDriver 兼容驱动程序。

优点：

* 只学习一份API： API 封装后使用 Selenium 的API风格即可，如果不喜欢可以使用 HtmlUnit 的API风格
* 更快的速度：对于js进行了优化。但在某些情况下，HtmlUnit可能比HtmlUnitDriver更快，因为它更轻量级
* 更好的封装：支持与优化JavaScript的一些高级特性和AJAX的异步调用，从而可以更好地模拟真实的浏览器环境。



**坐标：**

```xml

<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit3-driver</artifactId>
    <!--使用了HtmlUnit 3.x  注意与下面的坐标是不兼容的-->
    <version>4.8.1</version>
</dependency>

<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <version>4.8.1.1</version>
</dependency>
```



### 1. 第一份程序

如果你使用了最新版，那么包名 com.gargoylesoftware 改为了 org

```java
long start = System.currentTimeMillis();
//specify the browser - javascript support enabled
HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME,true){
    //HtmlUnit 提供了更多的自定义选项。要调整这些选项，您可以使用此模式。
    @Override
    protected WebClient modifyWebClient(WebClient client) {
        final WebClient webClient = super.modifyWebClient(client);
        //日志
        Logger.getLogger("com.gargoylesoftware.htmlunit.IncorrectnessListenerImpl").setLevel(Level.OFF);
        Logger.getLogger("com.gargoylesoftware.htmlunit.WebConsole").setLevel(Level.OFF); //控制台输出
        Logger.getLogger("com.gargoylesoftware.htmlunit.javascript").setLevel(Level.OFF);
        // you might customize the client here
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false); //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); //当请求状态不是200时是否抛出异常信息
        webClient.getOptions().setActiveXNative(false);//请注意，除非您完全信任JavaScript代码，否则永远不应允许运行本机ActiveX组件，因为它不受Java虚拟机的控制。
        webClient.getOptions().setRedirectEnabled(true);//是否跳转页面
        webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
        webClient.getOptions().setUseInsecureSSL(true);//忽略ssl认证
        webClient.getOptions().setCssEnabled(false);//设置是否启用css
        webClient.getOptions().setJavaScriptEnabled(true);//设置是否启用JavaScript
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持Ajax
        webClient.getOptions().setTimeout(2 * 60 * 1000); //设置“浏览器”的请求超时时间
        webClient.setJavaScriptTimeout(60 * 1000); //设置JS执行的超时时间
        webClient.getOptions().setDoNotTrackEnabled(false);//不跟踪抓取
        webClient.waitForBackgroundJavaScript(600 * 1000);//设置页面等待js响应时间,
        HttpWebConnection httpwebconnection = new HttpWebConnection(webClient);
        webClient.setWebConnection(httpwebconnection);
        return webClient;
    }
};
HtmlUnitOptions options = new HtmlUnitOptions(driver);

// 2.打开百度首页
driver.get("https://baidu.com");

// 5.退出浏览器
driver.quit();
System.out.println(System.currentTimeMillis() - start)
```







## 5. 通过 javet 使用 puppeteer

### 1. 环境搭建

* 导入包

```xml
    <!-- Linux and Windows (x86_64) -->
        <dependency>
            <groupId>com.caoccao.javet</groupId>
            <artifactId>javet</artifactId>
            <version>3.0.1</version>
        </dependency>

        <!-- Linux (arm64) -->
        <dependency>
            <groupId>com.caoccao.javet</groupId>
            <artifactId>javet-linux-arm64</artifactId>
            <version>3.0.1</version>
        </dependency>

        <!-- Mac OS (x86_64 and arm64) -->
        <dependency>
            <groupId>com.caoccao.javet</groupId>
            <artifactId>javet-macos</artifactId>
            <version>3.0.1</version>
        </dependency>
```

* 安装 node
* 安装 puppeteer 库

```shell
 npm install puppeteer
```



### 2. 使用

```kotlin
    V8Host.getNodeInstance().createV8Runtime<V8Runtime>().use{ v8Runtime ->
        v8Runtime.getExecutor("""
                        (async function (){
                         let a = 0;
                            while (a<=50) {
                            const puppeteer = require('puppeteer');
                            const browser = await puppeteer.launch({ headless: 'new' }); // 使用新的 Headless 模式
                            const page = await browser.newPage();
                            await page.goto('https://www.baidu.com', { waitUntil: 'networkidle0' });
            //                const title = await page.title(); // 直接获取页面标题
            //                console.log(title);
                            await page.screenshot({ path: 'example.png' });
                            await browser.close();
                            a++
                            }
                        })();
        """.trimIndent()).executeVoid()
    }
```



### 3.  优缺点分析

~~总结：脱裤子放屁，多此一举~~

* 易于使用： 使用js 编写的代码

* 可能存在的性能优势
* 简洁的API
* ~~吹不下去了~~