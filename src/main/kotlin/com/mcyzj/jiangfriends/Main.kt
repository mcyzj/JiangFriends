package com.mcyzj.jiangfriends

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */

import com.mcyzj.jiangfriends.command.Command
import com.mcyzj.jiangfriends.database.DatabaseApi
import com.mcyzj.jiangfriends.database.MysqlDatabaseApi
import com.mcyzj.jiangfriends.database.SQLiteDatabaseApi
import com.mcyzj.jiangfriends.listener.RegisterListener
import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.module.chat.BuiltInConfiguration
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration

@Suppress("unused")
class Main : EasyPlugin(){

    companion object {
        lateinit var databaseApi: DatabaseApi
        lateinit var instance: Main
        lateinit var Config: FileConfiguration
        var isGeyser = false
    }

    private var config = BuiltInConfiguration("config.yml")

    override fun onLoad() {

    }

    override fun onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aJiangFriends加载中....祈祷成功")
        saveDefaultConfig()
        instance = this

        Config = config
        Config.options().copyDefaults(true)
        saveConfig()

        //加载数据库
        if (config.getString("Database").equals("db", true)) {
            databaseApi = SQLiteDatabaseApi()
        }else if (config.getString("Database").equals("mysql", true)) {
            databaseApi = MysqlDatabaseApi()
        }else{
            Bukkit.getConsoleSender().sendMessage("配置文件里似乎混入了奇奇怪怪的数据库类型")
        }

        //注册监听事件
        RegisterListener.registerAll()
        //注册指令
        Command().commandRoot.register()
        Command().fast.register()
        Command().back.register()
    }

    override fun onDisable() {

    }
}