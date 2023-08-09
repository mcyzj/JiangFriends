package com.mcyzj.jiangfriends.database

import com.j256.ormlite.dao.Dao
import com.xbaimiao.easylib.module.database.Ormlite
import java.util.*
import kotlin.collections.ArrayList


open class AbstractDatabaseApi(ormlite: Ormlite):DatabaseApi {
    private val playerTable: Dao<PlayerDao, Int> = ormlite.createDao(PlayerDao::class.java)
    private val blackTable: Dao<BlackDao, Int> = ormlite.createDao(BlackDao::class.java)

    override fun getPlayerData(uuid: UUID): PlayerData? {
        val queryBuilder = playerTable.queryBuilder()
        queryBuilder.where().eq("user", uuid)
        val playerDao = queryBuilder.queryForFirst() ?: return null
        val blackqueryBuilder = blackTable.queryBuilder()
        blackqueryBuilder.where().eq("user", uuid)
        val blackDao = blackqueryBuilder.queryForFirst() ?: return null
        val friendList = ArrayList<UUID>()
        for (friend in playerDao.friends.split(",")) {
            try {
                friendList.add(UUID.fromString(friend))
            } catch (_: Exception) {

            }
        }
        val applyList = ArrayList<UUID>()
        for (apply in playerDao.apply.split(",")) {
            try {
                applyList.add(UUID.fromString(apply))
            } catch (_: Exception) {

            }
        }
        val blackList = ArrayList<UUID>()
        for (black in blackDao.blacks.split(",")) {
            try {
                blackList.add(UUID.fromString(black))
            } catch (_: Exception) {

            }
        }
        return PlayerData(
            uuid,
            friendList,
            applyList,
            blackList,
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
            playerDao.friends = playerData.friends.joinToString(",")
            playerDao.apply = playerData.apply.joinToString(",")
            playerDao.maxfriends = playerData.maxfriends.toString()
            playerTable.create(playerDao)
        } else {
            playerDao.friends = playerData.friends.joinToString(",")
            playerDao.apply = playerData.apply.joinToString(",")
            playerDao.maxfriends = playerData.maxfriends.toString()
            playerTable.update(playerDao)
        }
        val blackqueryBuilder = blackTable.queryBuilder()
        blackqueryBuilder.where().eq("user", uuid)
        var blackDao = blackqueryBuilder.queryForFirst()
        if(blackDao == null){
            blackDao = BlackDao()
            blackDao.user = uuid
            blackDao.blacks = playerData.blacks.joinToString(",")
            blackTable.create(blackDao)
        }else{
            blackDao.blacks = playerData.blacks.joinToString(",")
            blackTable.update(blackDao)
        }
    }
}