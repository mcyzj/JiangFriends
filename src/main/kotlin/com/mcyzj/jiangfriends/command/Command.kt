package com.mcyzj.jiangfriends.command

import com.mcyzj.jiangfriends.Main
import com.mcyzj.jiangfriends.api.Chat
import com.mcyzj.jiangfriends.api.Friends
import com.mcyzj.jiangfriends.tool.Players
import com.xbaimiao.easylib.module.command.command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class Command {

    private val add = command<CommandSender>("add") {
        permission = "jiangfriends.command.add"
        exec {
            if(args.size == 0){
                sender.sendMessage("${Main.Config.getString("prefix")}参数不符合规范")
                sender.sendMessage("${Main.Config.getString("prefix")}/friends add [添加的玩家]")
                if (sender.isOp){
                    sender.sendMessage("${Main.Config.getString("prefix")}/friends add [添加的玩家] [被添加好友的玩家]")
                }
                return@exec
            }
            if((args.size == 1).and(sender is Player)){
                val player = sender as Player
                var friend = Players.getplayer(args[0])
                if (friend == null) {
                    friend = Players.getplayeruuid(UUID.fromString(args[0]))
                }
                if (friend == null) {
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                    return@exec
                }
                if(friend == player){
                    player.sendMessage("${Main.Config.getString("prefix")}你不能添加自己为自己的好友")
                    return@exec
                }
                val playerBlackList = Friends.getblacklist(player.uniqueId)
                if(playerBlackList == null){
                    player.sendMessage("${Main.Config.getString("prefix")}无法获取${player.name}的黑名单列表")
                    return@exec
                }
                val friendBlackList = Friends.getblacklist(friend.uniqueId)
                if(friendBlackList == null){
                    player.sendMessage("${Main.Config.getString("prefix")}无法获取${friend.name}的黑名单列表")
                    return@exec
                }
                if(player.uniqueId in friendBlackList){
                    player.sendMessage("${Main.Config.getString("prefix")}你在对方的黑名单内")
                    return@exec
                }
                if(friend.uniqueId in playerBlackList){
                    player.sendMessage("${Main.Config.getString("prefix")}从黑名单内移除${friend.name}")
                    Friends.removeblack(player.uniqueId, friend.uniqueId)
                }
                if(Friends.addapply(player.uniqueId, friend.uniqueId)){
                    player.sendMessage("${Main.Config.getString("prefix")}发送好友申请成功")
                    return@exec
                }else{
                    player.sendMessage("${Main.Config.getString("prefix")}发送好友申请失败")
                    return@exec
                }
            }
            if((args.size == 2).and(sender.isOp)){
                var friend = Players.getplayer(args[0])
                if(friend == null){
                    friend = Players.getplayeruuid(UUID.fromString(args[0]))
                }
                if(friend == null){
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                    return@exec
                }
                var player = Players.getplayer(args[1])
                if(player == null){
                    player = Players.getplayeruuid(UUID.fromString(args[1]))
                }
                if(player == null){
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[1]}")
                    return@exec
                }
                if(friend == player){
                    player.sendMessage("${Main.Config.getString("prefix")}你不能让一个人互相成为好友")
                    return@exec
                }
                val playerBlackList = Friends.getblacklist(player.uniqueId)
                if(playerBlackList == null){
                    player.sendMessage("${Main.Config.getString("prefix")}无法获取${player.name}的黑名单列表")
                    return@exec
                }
                val friendBlackList = Friends.getblacklist(friend.uniqueId)
                if(friendBlackList == null){
                    player.sendMessage("${Main.Config.getString("prefix")}无法获取${friend.name}的黑名单列表")
                    return@exec
                }
                if(player.uniqueId in friendBlackList){
                    player.sendMessage("${Main.Config.getString("prefix")}从${friend.name}的黑名单内移除${player.name}")
                    Friends.removeblack(friend.uniqueId, player.uniqueId)
                }
                if(friend.uniqueId in playerBlackList){
                    player.sendMessage("${Main.Config.getString("prefix")}从${player.name}的黑名单内移除${friend.name}")
                    Friends.removeblack(player.uniqueId, friend.uniqueId)
                }
                var applylist = Friends.getapplylist(friend.uniqueId)
                if (applylist != null) {
                    if (applylist.isNotEmpty()) {
                        if (player.uniqueId in applylist) {
                            Friends.removeapply(friend.uniqueId, player.uniqueId)
                        }

                    }
                }
                applylist = Friends.getapplylist(player.uniqueId)
                if (applylist != null) {
                    if (applylist.isNotEmpty()) {
                        if (player.uniqueId in applylist) {
                            Friends.removeapply(friend.uniqueId, player.uniqueId)
                        }

                    }
                }
                if(Friends.addfriends(player.uniqueId, friend.uniqueId)){
                    player.sendMessage("${Main.Config.getString("prefix")}添加好友${friend.name}成功")
                    return@exec
                }else{
                    player.sendMessage("${Main.Config.getString("prefix")}添加好友${friend.name}失败")
                    return@exec
                }
            }
        }
    }

    private val remove = command<CommandSender>("remove") {
        permission = "jiangfriends.command.remove"
        exec {
            if(args.size == 0){
                sender.sendMessage("${Main.Config.getString("prefix")}参数不符合规范")
                sender.sendMessage("${Main.Config.getString("prefix")}/friends remove [移除的玩家]")
                if (sender.isOp){
                    sender.sendMessage("${Main.Config.getString("prefix")}/friends remove [移除的玩家] [被移除好友的玩家]")
                }
                return@exec
            }
            if((args.size == 1).and(sender is Player)) {
                val player = sender as Player
                var friend = Players.getplayer(args[0])
                if (friend == null) {
                    friend = Players.getplayeruuid(UUID.fromString(args[0]))
                }
                if (friend == null) {
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                    return@exec
                }
                val friendlist = Friends.getfriendslist(player.uniqueId)
                if (friendlist != null) {
                    if (friendlist.isNotEmpty()) {
                        if (friend.uniqueId in friendlist) {
                            if (Friends.removefriends(player.uniqueId, friend.uniqueId)) {
                                player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}成功")
                            } else {
                                player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}失败")
                            }
                        } else {
                            player.sendMessage("${Main.Config.getString("prefix")}${friend.name}并不是你的好友")
                        }
                    } else {
                        player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}失败")
                    }
                }
            }
            if((args.size == 2).and(sender.isOp)){
                var friend = Players.getplayer(args[0])
                if(friend == null){
                    friend = Players.getplayeruuid(UUID.fromString(args[0]))
                }
                if(friend == null){
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                    return@exec
                }
                var player = Players.getplayer(args[1])
                if(player == null){
                    player = Players.getplayeruuid(UUID.fromString(args[1]))
                }
                if(player == null){
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[1]}")
                    return@exec
                }
                val friendlist = Friends.getfriendslist(player.uniqueId)
                if (friendlist != null) {
                    if (friendlist.isNotEmpty()) {
                        if (friend.uniqueId in friendlist) {
                            if (Friends.addfriends(player.uniqueId, friend.uniqueId)) {
                                player.sendMessage("${Main.Config.getString("prefix")}删除${player.name}的好友${friend.name}成功")
                                return@exec
                            } else {
                                player.sendMessage("${Main.Config.getString("prefix")}删除${player.name}的好友${friend.name}失败")
                                return@exec
                            }
                        } else {
                            player.sendMessage("${Main.Config.getString("prefix")}${friend.name}并不是${player.name}的好友")
                        }
                    }else{
                        player.sendMessage("${Main.Config.getString("prefix")}删除${player.name}的好友${friend.name}失败")
                    }
                }
            }
        }
    }

    private val apply = command<CommandSender>("apply") {
        permission = "jiangfriends.command.apply"
        exec {
            if(args.size == 0){
                sender.sendMessage("${Main.Config.getString("prefix")}参数不符合规范")
                sender.sendMessage("${Main.Config.getString("prefix")}/friends apply [好友申请中的玩家]")
                return@exec
            }
            if((args.size == 1).and(sender is Player)){
                val player = sender as Player
                var friend = Players.getplayer(args[0])
                if(friend == null){
                    friend = Players.getplayeruuid(UUID.fromString(args[0]))
                }
                if(friend == null){
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                    return@exec
                }
                val applylist = Friends.getapplylist(player.uniqueId)
                if (applylist != null) {
                    if (applylist.isNotEmpty()) {
                        if (friend.uniqueId in applylist) {
                            Friends.removeapply(player.uniqueId, friend.uniqueId)
                            if (Friends.addfriends(player.uniqueId, friend.uniqueId)) {
                                player.sendMessage("${Main.Config.getString("prefix")}通过好友${friend.name}的申请成功")
                            } else {
                                player.sendMessage("${Main.Config.getString("prefix")}通过好友${friend.name}的申请失败")
                            }
                        } else {
                            player.sendMessage("${Main.Config.getString("prefix")}${friend.name}并没有发起好友申请")
                        }
                    }else{
                        player.sendMessage("${Main.Config.getString("prefix")}通过好友${friend.name}的申请失败")
                    }
                }
            }
        }
    }

    private val chat = command<CommandSender>("chat") {
        permission = "jiangfriends.command.chat"
        exec {
            if(args.size == 0){
                sender.sendMessage("${Main.Config.getString("prefix")}参数不符合规范")
                sender.sendMessage("${Main.Config.getString("prefix")}/friends chat [好友申请中的玩家/all]")
                return@exec
            }
            if((args.size == 1).and(sender is Player)) {
                val player = sender as Player
                if (args[0] == "all") {
                    Chat.removePlayerChat(player)
                    player.sendMessage("${Main.Config.getString("prefix")}切换公共频道")
                } else {
                    var friend = Players.getplayer(args[0])
                    if (friend == null) {
                        friend = Players.getplayeruuid(UUID.fromString(args[0]))
                    }
                    if (friend == null) {
                        sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                        return@exec
                    }
                    val friendList = Friends.getfriendslist(player.uniqueId)
                        ?: return@exec sender.sendMessage("${Main.Config.getString("prefix")}无法获取好友列表")
                    if (friend.uniqueId in friendList) {
                        Chat.setPlayerChat(player, friend)
                        player.sendMessage("${Main.Config.getString("prefix")}切换聊天频道")
                    } else {
                        player.sendMessage("${Main.Config.getString("prefix")}你貌似还不是对方的好友")
                    }
                }
            }
            if((args.size == 2).and(sender.isOp)) {
                var player = Players.getplayer(args[1])
                if (player == null) {
                    player = Players.getplayeruuid(UUID.fromString(args[1]))
                }
                if (player == null) {
                    sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[1]}")
                    return@exec
                }
                if (args[0] == "all") {
                    Chat.removePlayerChat(player)
                    player.sendMessage("${Main.Config.getString("prefix")}切换公共频道")
                } else {
                    var friend = Players.getplayer(args[0])
                    if (friend == null) {
                        friend = Players.getplayeruuid(UUID.fromString(args[0]))
                    }
                    if (friend == null) {
                        sender.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
                        return@exec
                    }
                    Chat.setPlayerChat(player, friend)
                    player.sendMessage("${Main.Config.getString("prefix")}切换聊天频道")
                }
            }
        }
    }

    val commandRoot = command<CommandSender>(Main.Config.getString("command.main")?: "friends") {
        permission = "jiangfriends.command"
        sub(add)
        sub(apply)
        sub(chat)
        sub(remove)
    }

    val fast = command<CommandSender>(Main.Config.getString("command.fast")?: "c") {
        permission = "jiangfriends.command.fast"
        exec {
            if (sender is Player){
                val friend = Chat.getLastPlayerChat(sender as Player) ?: return@exec
                Chat.setPlayerChat(sender as Player, friend)
                sender.sendMessage("${Main.Config.getString("prefix")}切换聊天频道")
            }
        }
    }

    val back = command<CommandSender>(Main.Config.getString("command.back")?: "b") {
        permission = "jiangfriends.command.back"
        exec {
            if (sender is Player){
                Chat.removePlayerChat(sender as Player)
                sender.sendMessage("${Main.Config.getString("prefix")}切换公共频道")
            }
        }
    }
}