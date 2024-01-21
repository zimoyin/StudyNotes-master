package com.github.zimoyin.nav

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


/**
 *
 * @author : zimo
 * @date : 2024/01/11
 */
abstract class AbsPage {
    private var nav0: NavController? = null

    val nav: NavController
        get() = nav0!!

    val content
        get() = nav0!!.content


    fun update(nav: NavController) {
        nav0 = nav
    }


    open val name: String = this.javaClass.simpleName

    /**
     * 获取针对该页面发生的数据
     * @WARNING: 该方法可能存在BUG
     */
    fun getData(): MapContent {
        val content0 = MapContent()
        content.contentInternal.filter { it.key.startsWith("Send Data To ") }.forEach { (key, value) ->
            val message = getMessage(key)
            if (message.page != name) return@forEach
            content0[message.key] = value
        }
        return content0
    }

    private fun getMessage(input: String): DataMessageHeader {
        val regex = Regex("Send Data To (.+\\s*) \\{\\[\\(\\*key:(.*?)\\*\\)\\]\\}")

        val matchResult = regex.findAll(input)
        for (match in matchResult) {
            val page = match.groupValues[1].trim() // 使用 trim() 去除多余的空格
            val key = match.groupValues[2]
            return DataMessageHeader(page, key)
        }
        throw NullPointerException("DataMessageHeader not found")
    }

    private data class DataMessageHeader(
        val page: String,
        val key: String,
    )


    /**
     * 页面内容,当发生跳转后执行。或者该方法内的状态发生变更就执行
     */
    @Composable
    abstract fun Page()

    /**
     * Compose 销毁时调用
     */
    @Composable
    fun unload(onlyOne: () -> Unit={}, content: () -> Unit) {
        DisposableEffect(Unit) {
            onlyOne()
            onDispose {
                content()
            }
        }
    }


    /**
     * 保存该页面的状态
     * 但是注意该方法因为将状态放到了全局的上下文里面，可能会引发问题，并且违反了 Compose 的设计规则
     * @param key0 保存的 key,如果为空则使用默认的 key
     * @param effectiveTimestamp 变量有效时间，如果超过就销毁（从其他页面返回该页面的时候）
     * @param calculation 要保存的变量
     */
    @Composable
    inline fun <reified T : Any> rememberPage(
        key0: Any? = null,
        effectiveTimestamp: Long = Long.MAX_VALUE,
        noinline calculation: () -> T,
    ): T {
        val sid by remember { mutableStateOf(getCallPageStackID()) }
        val key: Any =
            key0 ?: (nav!!.ID + name + currentCompositeKeyHash.toString() + calculation.hashCode().toString() + sid)
        return rememberStackTrace(key, effectiveTimestamp, System.currentTimeMillis(), calculation)
    }

    /**
     * 获取调用栈ID，如果在当前类中的同一位置调用，那么ID是相同的
     */
    fun getCallPageStackID(): String {
        val list = ArrayList<StackTraceElement>()
        val stackTrace = Thread.currentThread().stackTrace
        for (element in stackTrace) {
            list.add(element)
            if (element.className == this.javaClass.name) {
                break
            }
        }
        var id = ""
        list.forEach {
            id += it.hashCode()
        }
        return id
    }

    companion object {

        /**
         * 获取调用栈ID，如果在当前类中的同一位置调用，那么ID是相同的
         */
        fun getCallStackID(): String {
            val list = ArrayList<StackTraceElement>()
            val stackTrace = Thread.currentThread().stackTrace
            for ((index, element) in stackTrace.withIndex()) {
                if (index > 2) break
                list.add(element)
            }
            var id = ""
            list.forEach {
                id += it.hashCode()
            }
            return id
        }

        /**
         * 获取刷新的调用栈
         */
        fun getCallRefreshStackTrace(): List<StackTraceElement> {
            val list = ArrayList<StackTraceElement>()
            val stackTrace = Thread.currentThread().stackTrace
            var isRePage = false
            for (element in stackTrace) {
                list.add(element)
                NavController::class.java.methods.first { it.name == "currentPage" }?.let {
                    if (element.methodName == it.name) {
                        isRePage = true
                    }
                } ?: throw NullPointerException("currentPage method not found")
                if (isRePage) break
            }

            if (!isRePage) {
                list.clear()

                for (element in stackTrace) {
                    if (element.className.startsWith("androidx.compose.runtime.")) break
                    list.add(element)
                }
            }
            return list
        }

        /**
         * 判断是否是刷新页面了
         */
        fun isRefresh(): Boolean {
            val stackTrace = Thread.currentThread().stackTrace
            var isRePage = false
            for (element in stackTrace) {
                NavController::class.java.methods.first { it.name == "currentPage" }?.let {
                    if (element.methodName == it.name) {
                        isRePage = true
                    }
                }
            }
            return isRePage
        }
    }
}

/**
 * 保存该页面的状态
 * 通过堆栈信息计算key
 */
@Composable
inline fun <reified T : Any> rememberStackTrace(
    key0: Any? = null,
    effectiveTimestamp: Long = Long.MAX_VALUE,
    currentTimeMillis: Long = System.currentTimeMillis(),
    noinline calculation: () -> T,
): T {
    val sid by remember { mutableStateOf(AbsPage.getCallStackID()) }
    val key: Any = key0 ?: (currentCompositeKeyHash.toString() + calculation.hashCode().toString() + sid)
    var value = rememberSaveable(key) { calculation() }

    val contentValue = GlobalContent.get<T>("___Internal_Composeable_key:$key")?.apply {
        if (hashCode() == value.hashCode()) return@apply
        val startTimestamp =
            GlobalContent.getOrPut<Long>("___Internal_Composeable_key_timestamp:$key", currentTimeMillis)
        if (effectiveTimestamp - (currentTimeMillis - startTimestamp) > 0) {
            value = this
        } else {
            GlobalContent.remove("___Internal_Composeable_key:$key")
            GlobalContent.remove("___Internal_Composeable_key_timestamp:$key")
        }
    }
    if (contentValue == null) {
        GlobalContent["___Internal_Composeable_key:$key"] = value
        GlobalContent["___Internal_Composeable_key_timestamp:$key"] = currentTimeMillis
    }

    return value
}