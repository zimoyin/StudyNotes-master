```kotlin

/**
 * 启动一个虚拟线程
 * 注意：在虚拟线程上启动一个新的虚拟线程，他们的关系是同级的
 */
fun virtual(content: () -> Unit): Thread = Thread.startVirtualThread {
    content()
}

/**
 * 创建一个虚拟线程
 */
fun createVirtual(): Thread.Builder.OfVirtual = Thread.ofVirtual()

/**
 * 创建一个虚拟线程池
 */
fun createVirtualThreadPool(): ExecutorService = Executors.newVirtualThreadPerTaskExecutor()

/**
 * 创建一个虚拟线程空间
 */
fun <T> virtualSpace(content: ThreadSpace<T>.() -> Unit) = ThreadSpace<T>().apply {
    content()
}.let {
    VirtualSpaceResult(it.tasks)
}

class ThreadSpace<T> {
    val tasks: ArrayList<() -> T> = arrayListOf()

    /**
     * 创建一个任务
     */
    fun launch(content: () -> T) {
        tasks.add(content)
    }
}

class VirtualSpaceResult<T>(
    val tasks: List<() -> T>,
    var executors: ExecutorService? = null,
) {
    private fun getCallables(): List<Callable<T>> = tasks.map {
        Callable { it() }
    }

    /**
     * 任一任务完成则返回结果，并结束其他任务
     */
    fun any(timestamp: Long = -1): T {
        val executors0 = executors ?: Executors.newVirtualThreadPerTaskExecutor()
        return if (timestamp <= -1) executors0.invokeAny(getCallables(), timestamp, TimeUnit.MILLISECONDS)
        else executors0.invokeAny(getCallables())
    }

    /**
     * 等待所有任务完成
     */
    fun all(timestamp: Long = -1): MutableList<Future<T>>? {
        val executors0 = executors ?: Executors.newVirtualThreadPerTaskExecutor()
        return if (timestamp <= -1) executors0.invokeAll(getCallables(), timestamp, TimeUnit.MILLISECONDS)
        else executors0.invokeAll(getCallables())
    }

    /**
     * 任一任务失败则结束其他任务
     */
    fun anyFailure() {
        StructuredTaskScope.ShutdownOnFailure().use { stf ->
            tasks.forEach {
                stf.fork(it)
            }
            stf.join()
            stf.throwIfFailed()
        }
    }

    /**
     * 任一任务成功则结束其他任务
     */
    fun anySuccess() {
        StructuredTaskScope.ShutdownOnSuccess<T>().use { stf ->
            tasks.forEach {
                stf.fork(it)
            }
            stf.join()
        }
    }
}
```





协程封装

```KOTLIN
package io.github.zimoyin.tools.web.util

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.reflect.KProperty

/**
 * 在后台线程执行代码块。
 *
 * @author : zimo
 * @date : 2023/12/21
 * @param callback 需要在后台线程执行的非挂起代码块。
 * @return 一个 [Job] 对象，用于管理协程的生命周期。
 */
fun io(callback: suspend CoroutineScope.() -> Unit): Job = CoroutineScope(Dispatchers.IO).launch {
    callback()
}

/**
 * 禁止在协程中调用。
 * @author: zimo
 * @date:   2024/4/18
 */
fun CoroutineScope.io(ignore: suspend CoroutineScope.() -> Unit) {
    throw IllegalStateException("This function can only be called from the platform thread or the virtual thread.")
}

/**
 * 切换到IO协程
 * @author: zimo
 * @date:   2024/4/18
 */
suspend fun <T> withContextIO(callback: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO) {
        callback()
    }
}

/**
 * 切换到CPU协程
 * @author: zimo
 * @date:   2024/4/18
 */
suspend fun <T> withContextCPU(callback: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Default) {
        callback()
    }
}


/**
 * 在后台线程执行代码块。
 *
 * @author : zimo
 * @date : 2023/12/21
 * @param callback 需要在后台线程执行的非挂起代码块。
 * @return 一个 [Job] 对象，用于管理协程的生命周期。
 */
fun cpu(callback: suspend CoroutineScope.() -> Unit): Job = CoroutineScope(Dispatchers.Default).launch {
    callback()
}

/**
 * 禁止在协程中调用。
 * @author: zimo
 * @date:   2024/4/18
 */
fun CoroutineScope.cpu(ignore: suspend CoroutineScope.() -> Unit) {
    throw IllegalStateException("This function can only be called from the platform thread or the virtual thread.")
}

/**
 * 在后台线程执行代码块。
 */
fun coroutine(dispatcher: CoroutineDispatcher = Dispatchers.Default, callback: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(dispatcher).launch {
        callback()
    }

/**
 * 在后台线程执行代码块。
 */
fun CoroutineScope.coroutine(
    dispatcher: CoroutineDispatcher? = null,
    callback: suspend CoroutineScope.() -> Unit,
): Job =
    CoroutineScope(dispatcher ?: this.coroutineContext).launch {
        callback()
    }


/**
 * 在后台线程执行挂起的代码块，并返回一个 [Deferred] 对象，用于获取执行结果。使用 await 可以挂起协程并等待结果
 * 注意调用 await 后不会阻塞线程但是会阻塞协程。
 * 注意和标准库的 async 分辨
 * @author : zimo
 * @date : 2023/12/21
 * @param callback 需要在后台线程执行的挂起代码块。
 * @return 一个 [Deferred] 对象，可以用于等待执行完成并获取结果。
 */
fun <T> async(dispatcher: CoroutineDispatcher = Dispatchers.Default, callback: suspend () -> T): Deferred<T> =
    CoroutineScope(dispatcher).async {
        callback()
    }

/**
 * 创建一个允许等待所有的协程任务执行完毕的列表
 * 调用 result.jonAll() 来等待
 */
fun coordination(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    list: List<suspend CoroutineScope.() -> Unit>,
): ArrayList<Job> {
    val jobs: ArrayList<Job> = ArrayList<Job>()
    list.forEach {
        CoroutineScope(dispatcher).launch {
            it()
        }.apply {
            jobs.add(this)
        }
    }
    return jobs
}

/**
 * 创建一个允许等待所有的协程任务执行完毕的列表
 * 调用 result.join() 来等待
 * @author: zimo
 * @date:   2024/4/18
 * @param isImmediateAwait 是否立即等待,否则需要调用 join 来等待
 * @return 一个 [Job] 对象，用于管理协程的生命周期。
 */
fun coordination(
    isImmediateAwait: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    content: suspend CoroutineScope.() -> Unit,
): Job {
    var jon: Job? = null
    runBlocking(dispatcher) {
        jon = launch {
            content()
        }.apply {
            if (isImmediateAwait) this.join()
        }
    }
    return jon!!
}

/**
 * 创建一个协程池
 * @author: zimo
 * @date:   2024/4/18
 */
fun createCoroutinePool(coroutineCount: Int = 12): ExecutorCoroutineDispatcher =
    Executors.newFixedThreadPool(coroutineCount).asCoroutineDispatcher()

/**
 * 创建一个协程池,并执行一个阻塞线程的方法，该方法允许创建n个协程
 * @author: zimo
 * @date:   2024/4/18
 */
fun coroutinePool(coroutineCount: Int = 12, content: suspend CoroutineScope.() -> Unit) {
    val dispatcher = createCoroutinePool(coroutineCount)
    coordination(true, dispatcher, content)
    dispatcher.close()
}

/**
 * 异步创建一个值，并在使用的时候等待其生产完成
 * 注意，不能生产空值
 * 例如：异步创建值，并在需要使用时等待其等待生产完成，如果已经生产完成就直接返回
 *     val image: BufferedImage by AsyncValue {
 *         ImageIO.read(File("E:\\仓库\\study\\散图\\F0FadEKaEAAm9_m.jpg")).apply {
 *             delay(1100)
 *             println("78")
 *         }
 *     }
 */
class AsyncValue<T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val producer: suspend () -> T,
) {
    private var result: T? = null
    private var finished: Boolean = false
    private var exception: Throwable? = null
    private var production: Deferred<T?> = async(dispatcher) {
        try {
            return@async producer().apply {
                if (!finished) result = this
                finished = true
            }
        } catch (e: Exception) {
            exception = e
            finished = true
        }
        null
    }

//    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
//        if (exception != null) throw exception!!
//        return if (finished) result ?: throw NullPointerException("The value[$property] produced is null")
//        else blockThreadAwaitResult() ?: throw NullPointerException("The value[$property] produced is null")
//    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (exception != null) throw exception!!
        return if (finished) result ?: throw NullPointerException("The value[$property] produced is null")
        else runBlocking { blockThreadAwaitResult() }
            ?: throw NullPointerException("The value[$property] produced is null")
    }

//    /**
//     * 获取值，如果值还未生产完成，则阻塞线程等待。允许值为null
//     */
//    suspend fun getValueOrNull(): T? {
//        return if (finished) result else blockThreadAwaitResult()
//    }

    /**
     * 获取值，如果值还未生产完成，则挂起协程等待
     */
    suspend fun await(): T {
        return if (finished) {
            result!!
        } else {
            production.await()
            if (exception != null) {
                throw exception!!
            } else {
                result!!
            }
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        kotlin.runCatching { production.cancel() }
        finished = true
        result = value
    }

    private suspend fun blockThreadAwaitResult(): T? {
        return if (finished) {
            if (exception != null) throw exception!!
            result
        } else {
            while (!finished) {
//                Thread.sleep(100)
                delay(100)
            }
            if (exception != null) throw exception!!
            result
        }
    }
}
```

