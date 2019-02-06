package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player

@Dao
interface PlayerDao {

    @Query("select * from players p where p.name = :name")
    fun getPlayerByName(name: String): Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Update
    fun updatePlayer(player: Player)

    @Query("select * from players")
    fun getAllPlayers(): LiveData<Player>

    @Query("delete from players")
    fun deleteAllPlayers()
}