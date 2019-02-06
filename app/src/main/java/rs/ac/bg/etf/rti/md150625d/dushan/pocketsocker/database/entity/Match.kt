package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches",
    foreignKeys = arrayOf(
        ForeignKey(
        entity = Player::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("player1")
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf("player2")
        )
    )
)
data class Match (
    var player1: String,
    var player2: String,
    var date: Date,

    var player1Score: Int,
    var player2Score: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}