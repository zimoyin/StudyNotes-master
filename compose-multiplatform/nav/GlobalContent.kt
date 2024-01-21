package com.github.zimoyin.nav

/**
 *
 */
object GlobalContent: MapContent() {
    private fun readResolve(): Any = GlobalContent
}