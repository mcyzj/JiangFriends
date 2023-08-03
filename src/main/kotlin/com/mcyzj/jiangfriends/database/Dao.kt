package com.mcyzj.jiangfriends.database

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "Jiang_friends")
class PlayerDao {
    //玩家uuid
    @DatabaseField(dataType = DataType.UUID, canBeNull = false, columnName = "user")
    lateinit var user: UUID
    //玩家好友数据
    @DatabaseField(dataType = DataType.LONG_STRING,canBeNull = false, columnName = "friends")
    lateinit var friends: List<UUID>
    //玩家好友申请
    @DatabaseField(dataType = DataType.LONG_STRING,canBeNull = false, columnName = "apply")
    lateinit var apply: List<UUID>
    //玩家最大好友数量
    @DatabaseField(dataType = DataType.LONG_STRING,canBeNull = false, columnName = "maxfriends")
    lateinit var maxfriends: String
}

@DatabaseTable(tableName = "Jiang_black")
class BlackDao {
    //玩家uuid
    @DatabaseField(dataType = DataType.UUID, canBeNull = false, columnName = "user")
    lateinit var user: UUID
    //玩家黑名单
    @DatabaseField(dataType = DataType.LONG_STRING,canBeNull = false, columnName = "blacks")
    lateinit var blacks: List<UUID>
}