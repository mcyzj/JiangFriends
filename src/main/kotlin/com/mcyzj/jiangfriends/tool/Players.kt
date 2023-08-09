package com.mcyzj.jiangfriends.tool

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object Players {
    fun getplayeruuid(uuid: UUID): Player? {
        return try{
            Bukkit.getPlayer(uuid)
        }catch (e:Exception){
            Bukkit.getOfflinePlayer(uuid) as Player
        }
    }
    fun getplayer(name: String): Player? {
        return Bukkit.getPlayer(name)
    }
}