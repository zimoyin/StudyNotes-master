package com.github.zimoyin.nav.example

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.zimoyin.nav.AbsPage

/**
 *
 * @author : zimo
 * @date : 2024/01/11
 */
class TestPage : AbsPage() {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Page() {
        nav.content.sendDataToPage("TestPage2", "key0", "测试API")
        nav.content["a"] = "b"
        content["b"] = "b"
        Column {
            CounterPage()
            CounterPage2()
        }
    }


    @OptIn(ExperimentalStdlibApi::class)
    @Composable
    fun CounterPage() {
        var count by rememberPage { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Count: $count", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { count-- }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { count++ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }


            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = {
                nav.goto("TestPage2")
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(16.dp))

            if (count > 2) {
                CounterPage2("动态加入的组件")
            }
        }
    }


    @OptIn(ExperimentalStdlibApi::class)
    @Composable
    fun CounterPage2(
        msg: String = "",
    ) {
        var count by rememberPage { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$msg Count: $count", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { count-- }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = { count++ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }


            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = {
                nav.goto("TestPage2")
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}