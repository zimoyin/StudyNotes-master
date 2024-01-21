package com.github.zimoyin.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.*


/**
 * 创建一个导航控制域
 * 该域中可以通过 add 方法添加页面
 * 如果是主页面请通过 Home{} 进行直接书写，他会进行渲染。主要如果不在 Home 里面渲染组件那么该组件会被重复在任何页面中
 */
@Composable
fun Nav(
    id: String? = null,
    composeNavContent: Boolean = false,
    content: @Composable NavController.() -> Unit,
) {

    val ID = id ?: remember { mutableStateOf(UUID.randomUUID().toString()) }.value
    val pages = if (composeNavContent) composeMapOf(ID) else remember { notComposeMapOf(ID) }

    setGlobalNavContentValueOrPut("Nav_${ID}_retry", false)
    content(pages)
    setGlobalNavContentValueOrPut("Nav_${ID}_retry", true)


    pages["home"] = HomePageZo(content, pages)
    if (pages.currentPageName() == NavController.NULL_PAGE_ID) pages.goto("home")


    pages.currentPage()
}


@Composable
fun NavController.Home(content: @Composable () -> Unit) {
    if (isHome() && getGlobalNavContentValueOrPut("Nav_${ID}_retry", true)) {
        content()
    }
}

class HomePageZo(private val handle: @Composable NavController.() -> Unit, private val pages: NavController) :
    AbsPage() {
    @Composable
    override fun Page() {
        handle(pages)
    }
}

inline fun <reified T : Any> getGlobalNavContentValue(key: String): T? {
    return GlobalContent.get<T>("___Internal_GlobalNavContent_key:$key")
}

inline fun <reified T : Any> getGlobalNavContentValueOrPut(key: String, value: T): T {
    return GlobalContent.getOrPut<T>("___Internal_GlobalNavContent_key:$key", value)
}

fun setGlobalNavContentValueOrPut(key: String, value: Any): Any? {
    return GlobalContent.set("___Internal_GlobalNavContent_key:$key", value)
}

fun removeGlobalNavContent(key: String) {
    return GlobalContent.remove("___Internal_GlobalNavContent_key:$key")
}