package com.mcyzj.jiangfriends.chat

import com.mcyzj.jiangfriends.Main
import com.mcyzj.jiangfriends.api.Chat
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener: Listener {
    @EventHandler
    fun playerChat(e: AsyncPlayerChatEvent) {
        val player = e.player
        val friend = Chat.getPlayerChat(player) ?:return
        e.isCancelled = true
        Bukkit.getConsoleSender().sendMessage("§aJiangFriends [私聊] ${player.name} -> ${friend.name}: ${e.message}")
        friend.sendMessage("${Main.Config.getString("prefix")}[私聊] ${player.name}: ${e.message}")
        player.sendMessage("${Main.Config.getString("prefix")}[私聊->${friend.name}] ${player.name}: ${e.message}")
        Chat.setLastPlayerChat(friend, player)
    }
}