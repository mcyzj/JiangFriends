package com.mcyzj.jiangfriends.listener

import com.mcyzj.jiangfriends.Main
import com.mcyzj.jiangfriends.api.Database
import com.mcyzj.jiangfriends.database.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.spigotmc.event.player.PlayerSpawnLocationEvent
import java.util.*
import kotlin.collections.ArrayList

class OnPlayerJoin : Listener {
    @EventHandler
    fun on(event: PlayerSpawnLocationEvent) {
        var playerData = Database.getplayerdata(event.player.uniqueId)
        if(playerData == null){
            playerData = PlayerData(
                event.player.uniqueId,
                ArrayList<UUID>(),
                ArrayList<UUID>(),
                ArrayList<UUID>(),
                Main.Config.getInt("maxfriends")
            )
            Main.databaseApi.setPlayerData(event.player.uniqueId, playerData)
        }
        if(playerData.apply.size != 0){
            event.player.sendMessage("${Main.Config.getString("prefix")}你有${playerData.apply.size}好友申请等待处理")
        }
    }
}