package com.mcyzj.jiangfriends.api

import org.bukkit.entity.Player

object Chat {
    private val chatMap = mutableMapOf<Player, Player>()
    private val lastChatMap = mutableMapOf<Player, Player>()
    fun setPlayerChat(player: Player, friend: Player){
        chatMap[player] = friend
    }

    fun removePlayerChat(player: Player){
        chatMap.remove(player)
    }

    fun getPlayerChat(player: Player): Player? {
        return chatMap[player]
    }

    fun setLastPlayerChat(player: Player, friend: Player){
        lastChatMap[player] = friend
    }

    fun getLastPlayerChat(player: Player): Player? {
        return lastChatMap[player]
    }
}