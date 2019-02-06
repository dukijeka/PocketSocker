package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match


@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMatch(match: Match)

    @Query("select * from matches")
    fun getAllMatches(): List<Match>

    @Query("select * from matches where id = :id")
    fun getMatchByID(id: Int): Match

    @Delete
    fun deleteMatch(match: Match)

    @Query("delete from matches")
    fun deleteAllMatches()

    @Query("select * from matches m where (m.player1 = :player1Name and m.player2 = :player2Name) or (m.player1 = :player2Name and m.player2 = :player1Name)")
    fun getMatchesForPlayers(player1Name: String, player2Name: String): LiveData<List<Match>>
}