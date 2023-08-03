package com.mcyzj.jiangfriends.api

import com.mcyzj.jiangfriends.Main
import com.mcyzj.jiangfriends.database.PlayerData
import java.util.UUID

object Database {

    val database = Main.databaseApi

    fun getplayerdata(uuid: UUID): PlayerData? {
        return database.getPlayerData(uuid)
    }

    fun setplayerdata(playerData: PlayerData): Boolean{
        return try{
            database.setPlayerData(playerData.uuid, playerData)
            true
        }catch (e:Exception){
            throw e
        }
    }
}