package com.github.zimoyin.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * 页面组
 * @param id     页面组id
 * @param pairs 页面名称与页面对象的映射关系
 */
@Composable
fun composeMapOf(id: String, vararg pairs: Pair<String, AbsPage>): NavController {
    val currentPage: String = pairs[0].first
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        this.putAll(pairs)
    }
}

/**
 * 页面组
 * @param pairs 页面名称与页面对象的映射关系
 */
@Composable
fun composeMapOf(vararg pairs: Pair<String, AbsPage>): NavController {
    val currentPage: String = pairs[0].first
    var id = ""
    pairs.forEach {
        id += it.first
    }
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        this.putAll(pairs)
    }
}

/**
 * 页面组
 * @param id 页面组id
 */
@Composable
fun composeMapOf(id: String): NavController {
    return NavController(id, remember { mutableStateOf(NavController.NULL_PAGE_ID) })
}

fun notComposeMapOf(id: String): NavController {
    return NavController(id, mutableStateOf(NavController.NULL_PAGE_ID))
}


/**
 * 页面组
 * @param id 页面组id
 * @param pages 页面对象
 */
@Composable
fun composeMapOf(id: String, vararg pages: AbsPage): NavController {
    val currentPage: String = pages[0].name
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        pages.forEach {
            this[it.name] = it
        }
    }
}

/**
 * 页面组
 * @param pages 页面对象
 */
@Composable
fun composeMapOf(vararg pages: AbsPage): NavController {
    val currentPage: String = pages[0].name
    var id = ""
    pages.forEach {
        id += it.name
    }
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        pages.forEach {
            this[it.name] = it
        }
    }
}

/**
 * 页面组
 * @param id 页面组id
 * @param clazz 页面的class
 */
@Composable
fun composeMapOf(id: String, vararg clazz: Class<out AbsPage>): NavController {
    val pages = clazz.map { it.getConstructor().newInstance() }
    val currentPage: String = pages[0].name
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        pages.forEach {
            this[it.name] = it
        }
    }
}

/**
 * 页面组
 * @param clazz 页面的class
 */
@Composable
fun composeMapOf(vararg clazz: Class<out AbsPage>): NavController {
    val pages = clazz.map { it.getConstructor().newInstance() }
    val currentPage: String = pages[0].name
    var id = ""
    pages.forEach {
        id += it.name
    }
    return NavController(id, remember { mutableStateOf(currentPage) }).apply {
        pages.forEach {
            this[it.name] = it
        }
    }
}