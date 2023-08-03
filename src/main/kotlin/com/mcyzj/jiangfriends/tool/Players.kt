package com.mcyzj.jiangfriends.tool

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object Players {
    fun getplayer(uuid: UUID): Player? {
        return try{
            Bukkit.getPlayer(uuid)
        }catch (e:Exception){
            Bukkit.getOfflinePlayer(uuid) as Player
        }
    }
    fun getplayer(name: String): Player? {
        return try{
            Bukkit.getPlayer(name)
        }catch (e:Exception){
            var players: Player? = null
            for(player in Bukkit.getOfflinePlayers()){
                if (player.name == name){
                    players = player as Player
                }
            }
            players
        }
    }
}