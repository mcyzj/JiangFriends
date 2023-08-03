package com.mcyzj.jiangfriends.database

import com.mcyzj.jiangfriends.Main
import com.xbaimiao.easylib.module.database.OrmliteMysql

class MysqlDatabaseApi : AbstractDatabaseApi(OrmliteMysql(
    Main.instance.config.getConfigurationSection("Mysql")!!,
    Main.instance.config.getBoolean("Mysql.HikariCP")
)
)