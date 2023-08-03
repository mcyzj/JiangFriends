package com.mcyzj.jiangfriends.listener

import com.xbaimiao.easylib.module.utils.registerListener


object RegisterListener {
    fun registerAll() {
        registerListener(OnPlayerJoin())
    }
}