

# 异步改为同步

## 方法一: CompletableFuture

```java
import io.vertx.core.Vertx;
import io.vertx.core.Future;
import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
	//JDK 原生工具
    CompletableFuture<String> future = new CompletableFuture<>();

    // 异步操作
    vertx.createHttpClient().getNow(8080, "localhost", "/")
      .handler(response -> {
        response.bodyHandler(buffer -> {
          String result = buffer.toString();
          System.out.println("Received: " + result);
          // 模拟一个异常情况
          if (result.isEmpty()) {
            Throwable error = new RuntimeException("Empty result");
            // 标记异常
            future.completeExceptionally(error);
          } else {
            // 标记结果
            future.complete(result);
          }
        });
      });

    // 等待结果并获取它（阻塞调用线程）
    try {
      String result = future.get();
      System.out.println("Synchronous result: " + result);
    } catch (Exception e) {
      e.printStackTrace();
    }

    vertx.close();
  }
}
```

## 方法二

```kotlin
Future.toCompletionStage().toCompletableFuture().get()
```



#  禁止自己创建线程

vertx 如果需要执行阻塞代码请使用官方API

在Vert.x中，由于异步编程的特性，可能会遇到线程不一致的情况。Vert.x是一个基于事件驱动的框架，它使用了非阻塞的I/O和异步的消息传递机制。这可能导致回调函数在不同的线程上执行，从而引起线程不一致的问题。

问题代码:

在 Verticle 中使用以下代码，成功回调会运行在事件循环线程之外。这是错误的应用

>  Vert.x是单线程的，通过事件循环来处理事件。如果成功回调在不同的线程上执行，可能会导致并发问题和竞态条件，因为事件循环线程和其他线程之间可能会同时访问共享的数据。
>
>  如果成功回调执行在事件循环线程之外，且涉及到一些不是线程安全的操作，可能会引发数据竞争和其他并发问题。

```kotlin
fun r1(): Future<Int> {
    val promise = Promise.promise<Int>()
    Thread{
        Thread.sleep(3000)
        promise.complete(10)
    }.start()
    return promise.future()
}

//调用
r1().onSuccess {
    println(Thread.currentThread().name)
}
```

以下是修复代码

```kotlin
fun r1(): Future<Int> {
    val promise = Promise.promise<Int>()
    Thread{
        Thread.sleep(3000)
        context.runOnContext{
            promise.complete(10)
        }
    }.start()
    return promise.future()
}
```





# 区分 get/result

* get() 阻塞当前线程等待结果

> - `CompletableFuture`是Java标准库中`java.util.concurrent`包下的一部分。
> - 它提供了一种在异步操作完成时触发回调的机制，允许你以函数式的方式处理异步任务的结果。

* result() 不阻塞当前线程，如果有结果就返回，没有就不返回，他不去等待你是否执行完毕

> - Vert.x的`Future`不同于Java标准库的`CompletableFuture`，它更加轻量级，并且与Vert.x的异步模型更为契合。
> - `Future`的结果通常是通过回调函数进行处理，而不是通过阻塞等待。



# Future 的方法

* **`compose`** `compose` 方法用于将当前的 `Future` 与另一个 `Future` 进行组合。它允许你在第一个 `Future` 完成后，将其结果传递给一个函数，该函数返回另一个 `Future`。可以用于链式调用，比如打开文件是个`Future` 最后执行完毕后通过compose 执行下一个 `Future` 
* **`map`** 

# 结构化并发 CompositeFuture

在 Vert.x 中，你可以使用 `io.vertx.core.CompositeFuture` 类来实现结构化并发。`CompositeFuture` 允许你同时等待多个 `Future` 对象的完成，并在它们都完成后执行某些操作。这对于并行执行多个异步任务并汇总结果非常有用。

1. **创建多个 `Future` 对象：**

   - 创建多个需要并行执行的 `Future` 对象。这些 `Future` 可以表示不同的异步任务。

   ```
   javaCopy codeFuture<String> future1 = //...
   Future<Integer> future2 = //...
   // ... 可以创建更多的 Future
   ```

2. **使用 `CompositeFuture.all` 或 `CompositeFuture.join`：**

   - 使用 `CompositeFuture.all` 方法，该方法接受一个 `List`，并返回一个新的 `Future` 对象。这个新的 `Future` 将在列表中的所有 `Future` 都完成时完成。

   ```
   javaCopy codeList<Future> futures = Arrays.asList(future1, future2);
   
   CompositeFuture compositeFuture = CompositeFuture.all(futures);
   ```

   或者，你可以使用 `CompositeFuture.join` 方法，该方法接受多个 `Future` 作为参数，返回一个新的 `Future` 对象，该对象在所有传入的 `Future` 完成时完成。

   ```
   javaCopy code
   CompositeFuture compositeFuture = CompositeFuture.join(future1, future2);
   ```

3. **处理 `CompositeFuture` 的结果：**

   - 注册一个处理 `CompositeFuture` 完成时的回调，以便在所有 `Future` 都完成时执行某些操作。

   ```
   javaCopy codecompositeFuture.setHandler(result -> {
       if (result.succeeded()) {
           // 所有 Future 都成功完成
           // 可以从 CompositeFuture 中获取每个 Future 的结果
           String result1 = compositeFuture.resultAt(0);
           Integer result2 = compositeFuture.resultAt(1);
           // 执行进一步的操作...
       } else {
           // 处理失败的情况
           Throwable cause = result.cause();
           // 处理异常...
       }
   });
   ```

   //...............