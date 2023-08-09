package com.mcyzj.jiangfriends.command

import com.mcyzj.jiangfriends.Main
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
                val friend = Players.getplayer(args[0])
                if (friend != null){
                    if(friend == player){
                        player.sendMessage("${Main.Config.getString("prefix")}你不能添加自己为自己的好友")
                        return@exec
                    }
                    if(Friends.addapply(player.uniqueId, friend.uniqueId)){
                        player.sendMessage("${Main.Config.getString("prefix")}发送好友申请成功")
                        return@exec
                    }else{
                        player.sendMessage("${Main.Config.getString("prefix")}发送好友申请失败")
                        return@exec
                    }
                }
                val uuidfriend = Players.getplayeruuid(UUID.fromString(args[0]))
                if (uuidfriend != null){
                    if(uuidfriend == player){
                        player.sendMessage("${Main.Config.getString("prefix")}你不能添加自己为自己的好友")
                        return@exec
                    }
                    if(Friends.addapply(player.uniqueId, uuidfriend.uniqueId)){
                        player.sendMessage("${Main.Config.getString("prefix")}发送好友申请成功")
                        return@exec
                    }else{
                        player.sendMessage("${Main.Config.getString("prefix")}发送好友申请失败")
                        return@exec
                    }
                }
                player.sendMessage("${Main.Config.getString("prefix")}无法寻找到玩家${args[0]}")
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
                                player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}成功")
                                return@exec
                            } else {
                                player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}失败")
                                return@exec
                            }
                        } else {
                            player.sendMessage("${Main.Config.getString("prefix")}${friend.name}并不是你的好友")
                        }
                    }else{
                        player.sendMessage("${Main.Config.getString("prefix")}删除好友${friend.name}失败")
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

    val commandRoot = command<CommandSender>("friends") {
        permission = "jiangfriends.command"
        sub(add)
        sub(apply)
        sub(remove)
    }
}