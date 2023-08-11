package com.mcyzj.jiangfriends.listener

import com.mcyzj.jiangfriends.chat.ChatListener
import com.xbaimiao.easylib.module.utils.registerListener


object RegisterListener {
    fun registerAll() {
        registerListener(OnPlayerJoin())
        registerListener(ChatListener())
    }
}