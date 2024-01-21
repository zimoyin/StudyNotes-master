package com.github.zimoyin.nav.example


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.singleWindowApplication
import com.github.zimoyin.nav.Home
import com.github.zimoyin.nav.Nav
import com.github.zimoyin.nav.composeMapOf
import com.github.zimoyin.ui.TextButton
import java.util.UUID

/**
 * 导航示例
 * @author : zimo
 * @date : 2024/01/12
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Example1() {
    Nav {
    add(TestPage())
        add(TestPage2())
        //主页面从这进行组建
        Home {
            println("渲染主页面")
            println("渲染页面: ${currentPageName()}")
            println("主页面: $homeName")
            //存储信息到上下文
            content["key"] = "你好，TestPage2！"
            Text("Home", modifier = Modifier.fillMaxSize().onClick {
                goto("TestPage")
//                goto("TestPage2")
                println("B:$this")
            })
        }
    }
}


@Composable
fun Example2() {
    val mapOf = composeMapOf(TestPage(), TestPage2())
    mapOf.currentPage()
}

fun main() = singleWindowApplication {

}
