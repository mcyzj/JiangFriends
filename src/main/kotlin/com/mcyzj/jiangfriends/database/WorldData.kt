package com.mcyzj.jiangfriends.database

import java.util.UUID

data class PlayerData (
    var uuid: UUID,
    var friends: List<UUID>,
    var apply: List<UUID>,
    var blacks: List<UUID>,
    var maxfriends: Int
)