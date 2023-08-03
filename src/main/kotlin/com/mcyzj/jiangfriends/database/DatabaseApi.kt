package com.mcyzj.jiangfriends.database

import java.util.*

interface DatabaseApi {
    fun getPlayerData(uuid: UUID): PlayerData?
    fun setPlayerData(uuid: UUID, playerData: PlayerData)
}