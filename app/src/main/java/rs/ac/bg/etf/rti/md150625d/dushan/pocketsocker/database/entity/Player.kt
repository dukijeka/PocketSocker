package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "players")
data class Player (
    @PrimaryKey
    var name: String,

    var winsCnt: Int
)
