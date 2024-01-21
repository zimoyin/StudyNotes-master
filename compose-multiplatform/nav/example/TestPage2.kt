package com.github.zimoyin.nav.example

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.github.zimoyin.nav.AbsPage

/**
 *
 * @author : zimo
 * @date : 2024/01/11
 */
class TestPage2 : AbsPage() {

    @Composable
    override fun Page() {
        println("key -> " + content.get<String>("key"))
        println(content)
        getData().forEach { key, value ->
            println("$key ->  $value")
        }
        Button(
            onClick = {
                nav.back()
            },
        ) {
            Text(
                "返回上个页面",
            )
        }

    }
}