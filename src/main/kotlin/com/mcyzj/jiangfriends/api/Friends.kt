package com.mcyzj.jiangfriends.api

import java.util.UUID

object Friends {

    fun getapplylist(uuid: UUID): List<UUID>? {
        val playerdata = Database.getplayerdata(uuid)
        if (playerdata != null) {
            return playerdata.apply
        }
        return null
    }
    fun getfriendslist(uuid: UUID): List<UUID>? {
        val playerdata = Database.getplayerdata(uuid)
        if (playerdata != null) {
            return playerdata.friends
        }
        return null
    }
    fun getblacklist(uuid: UUID): List<UUID>? {
        val playerdata = Database.getplayerdata(uuid)
        if (playerdata != null) {
            return playerdata.blacks
        }
        return null
    }
    fun addapply(friend: UUID, uuid: UUID): Boolean{
        val playerdata = Database.getplayerdata(uuid)
        val apply = playerdata?.apply as ArrayList<UUID>
        if(uuid in apply){
            return false
        }
        apply.add(friend)
        playerdata.apply = apply
        return Database.setplayerdata(playerdata)
    }
    fun removeapply(uuid: UUID, friend: UUID): Boolean{
        val playerdata = Database.getplayerdata(uuid)
        val apply = playerdata?.apply as ArrayList<UUID>
        if(friend !in apply){
            return false
        }
        apply.remove(friend)
        playerdata.apply = apply
        return Database.setplayerdata(playerdata)
    }
    fun addfriends(uuid: UUID, friend: UUID): Boolean{
        var playerdata = Database.getplayerdata(uuid)
        var friends = playerdata?.friends as ArrayList<UUID>
        if(friend in friends){
            return false
        }
        friends.add(friend)
        playerdata.friends = friends
        Database.setplayerdata(playerdata)

        playerdata = Database.getplayerdata(friend) ?: return false
        friends = playerdata.friends as ArrayList<UUID>
        if(uuid in friends){
            return false
        }
        friends.add(uuid)
        playerdata.friends = friends
        return Database.setplayerdata(playerdata)
    }
    fun removefriends(uuid: UUID, friend: UUID): Boolean{
        var playerdata = Database.getplayerdata(uuid) ?: return false
        var friends = playerdata.friends as ArrayList<UUID>
        if(friend !in friends){
            return false
        }
        friends.remove(friend)
        playerdata.friends = friends
        Database.setplayerdata(playerdata)

        playerdata = Database.getplayerdata(friend) ?: return false
        friends = playerdata.friends as ArrayList<UUID>
        if(uuid !in friends){
            return false
        }
        friends.remove(uuid)
        playerdata.friends = friends
        return Database.setplayerdata(playerdata)
    }
    fun addblack(uuid: UUID, black: UUID): Boolean{
        val playerdata = Database.getplayerdata(uuid)
        val blacks = playerdata?.friends as ArrayList<UUID>
        if(black in blacks){
            return false
        }
        blacks.add(black)
        playerdata.friends = blacks
        return Database.setplayerdata(playerdata)
    }
    fun removeblack(uuid: UUID, black: UUID): Boolean{
        val playerdata = Database.getplayerdata(uuid)
        val blacks = playerdata?.friends as ArrayList<UUID>
        if(black !in blacks){
            return false
        }
        blacks.remove(black)
        playerdata.friends = blacks
        return Database.setplayerdata(playerdata)
    }
    fun setmaxfriends(uuid: UUID, length: Int): Boolean{
        val playerdata = Database.getplayerdata(uuid)
        if (playerdata != null) {
            playerdata.maxfriends = length
            return Database.setplayerdata(playerdata)
        }
        return false
    }
}