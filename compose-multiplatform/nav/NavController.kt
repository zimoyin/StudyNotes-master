package com.github.zimoyin.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

/**
 * 使用方式
 *     //获取页面组
 *     val pages = composeMapOf(
 *         "homePage" to HomePagez(),
 *         "two" to Two(),
 *     )
 *
 *     MaterialTheme {
 *         Surface {
 *             //渲染当前页面
 *             pages.currentPage()
 *         }
 *     }
 */
class NavController(val ID: String, private var currentPage: MutableState<String>) : HashMap<String, AbsPage>() {

    private val stack: MutableList<String> = GlobalContent.getOrPut("__INTERNAL_NVA_STACK_ID:$ID", mutableListOf())
    val content: Content = Content.create(this)
    val homeName = stack.firstOrNull() ?: currentPage.value


    fun stack(): List<String> {
        return stack.toList()
    }

    @Composable
    private fun getPage(page: String, map: Content = content) {
        get(page)?.apply {
            update(this@NavController)
            stack.add(currentPage.value)
            Page()
        } ?: getPage(stack.last(), map)
    }


    @Composable
    fun getPage(page: String): Unit? {
        return get(page)?.Page()
    }

    fun goto(page: String, map: Map<String, Any>) {
        if (page == currentPage.value) return
        if (map.isNotEmpty()) {
            content.sendDataToPage(page, map)
        }
        get(page)?.let {
            currentPage.value = page
        }
    }

    fun goto(page: String, vararg map: Pair<String, Any>) {
        goto(page, map.toMap())
    }

    fun back() {
        if (stack.size > 1) {
            stack.removeLast()
            val last = stack.last()
            get(last)?.let {
                currentPage.value = last
                stack.removeLast()
            }
        }
    }

    fun home() {
        get(homeName)?.let {
            currentPage.value = homeName
            stack.clear()
        }
    }

    @Composable
    fun currentPageName() = currentPage.value

    @Composable
    fun currentPage(map: Content = content) = getPage(currentPage.value, map)


    fun isHome(): Boolean {
        return homeName == currentPage.value || homeName == NULL_PAGE_ID
    }

    /**
     * 添加一个页面
     */
    fun add(page: AbsPage) {
        put(page.name, page)
    }


    /**
     * 添加一个页面
     */
    fun add(name: String, content: @Composable () -> Unit) {
        val page = object : AbsPage() {
            override val name: String
                get() = name

            @Composable
            override fun Page() {
                content()
            }
        }
        add(page)
    }

    override fun toString(): String {
        return super.toString()
    }

    data class Content(val nav: NavController) : MapContent() {
        init {
            GlobalContent.getOrPut<Content>("__INTERNAL_NAV_CONTENT_ID:${nav.ID}", this).let {
                it.forEach { key, value ->
                    if (key != NAV_STRING) set(key, value)
                }
            }
            set(NAV_STRING, nav)
        }

        /**
         * 针对某页面发生的数据
         * @WARNING: 该方法可能会出现BUG
         */
        fun sendDataToPage(page: String, data: MapContent) {
            keys().filter { it.startsWith("Send Data To $page ") }.let { it ->
                it.forEach {
                    remove(it)
                }
            }
            data.forEach { key, value ->
                set("Send Data To $page {[(*key:$key*)]}", value)
            }
        }

        /**
         * 针对某页面发生的数据
         * @WARNING: 该方法可能会出现BUG
         */
        fun sendDataToPage(page: String, key: String, value: Any) {
            keys().filter { it.startsWith("Send Data To $page ") }.let { it ->
                it.forEach {
                    remove(it)
                }
            }
            set("Send Data To $page {[(*key:$key*)]}", value)
        }

        override fun toString(): String {
            return super.toString()
        }

        fun sendDataToPage(page: String, data: Map<String, Any>) {
            keys().filter { it.startsWith("Send Data To $page ") }.let { it ->
                it.forEach {
                    remove(it)
                }
            }
            data.forEach { (key, value) ->
                set("Send Data To $page {[(*key:$key*)]}", value)
            }
        }

        companion object {
            const val NAV_STRING = "nav"
            fun create(nav: NavController): Content {
                return GlobalContent.getOrPut<Content>("__INTERNAL_NAV_CONTENT_ID:${nav.ID}", Content(nav)).apply {
                    set(Content.NAV_STRING, nav)
                }
            }
        }
    }

    companion object {
        const val NULL_PAGE_ID = "__NAV_ID_NONE__$20240112"
    }
}