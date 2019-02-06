package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.MatchDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.PlayerDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player

@Database(entities = arrayOf(Player::class, Match::class), version = 1)
@TypeConverters(GameDatabaseConverters::class)
abstract class GameDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var databaseInstance: GameDatabase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): GameDatabase {
            if(databaseInstance == null) {
                databaseInstance =
                    Room.databaseBuilder(
                        context, GameDatabase::class.java, "game_statistics"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return databaseInstance!!
        }
    }

    abstract fun playerDao(): PlayerDao
    abstract fun matchDao(): MatchDao
}