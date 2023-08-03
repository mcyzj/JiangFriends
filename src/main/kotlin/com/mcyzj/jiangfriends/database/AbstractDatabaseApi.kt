package com.mcyzj.jiangfriends.database

import com.j256.ormlite.dao.Dao
import com.xbaimiao.easylib.module.database.Ormlite
import java.util.*


open class AbstractDatabaseApi(ormlite: Ormlite):DatabaseApi {
    private val playerTable: Dao<PlayerDao, Int> = ormlite.createDao(PlayerDao::class.java)
    private val blackTable: Dao<BlackDao, Int> = ormlite.createDao(PlayerDao::class.java)

    override fun getPlayerData(uuid: UUID): PlayerData? {
        val queryBuilder = playerTable.queryBuilder()
        queryBuilder.where().eq("user", uuid)
        val playerDao = queryBuilder.queryForFirst() ?: return null
        val blackqueryBuilder = blackTable.queryBuilder()
        blackqueryBuilder.where().eq("user", uuid)
        val blackDao = blackqueryBuilder.queryForFirst() ?: return null
        return PlayerData(
            uuid,
            playerDao.friends,
            playerDao.apply,
            blackDao.blacks,
            playerDao.maxfriends.toInt()
        )
    }

    override fun setPlayerData(uuid: UUID, playerData: PlayerData) {
        val queryBuilder = playerTable.queryBuilder()
        queryBuilder.where().eq("user", uuid)
        var playerDao = queryBuilder.queryForFirst()
        if (playerDao == null) {
            playerDao = PlayerDao()
            playerDao.user = uuid
            playerDao.friends = playerData.friends
            playerDao.apply = playerData.apply
            playerDao.maxfriends = playerData.maxfriends.toString()
            playerTable.create(playerDao)
        } else {
            playerDao.user = uuid
            playerDao.friends = playerData.friends
            playerDao.apply = playerData.apply
            playerDao.maxfriends = playerData.maxfriends.toString()
            playerTable.update(playerDao)
        }
        val blackqueryBuilder = blackTable.queryBuilder()
        blackqueryBuilder.where().eq("user", uuid)
        var blackDao = blackqueryBuilder.queryForFirst()
        if(blackDao == null){
            blackDao = BlackDao()
            blackDao.blacks = playerData.blacks
            blackTable.update(blackDao)
        }else{
            blackDao.blacks = playerData.blacks
            blackTable.update(blackDao)
        }
    }
}