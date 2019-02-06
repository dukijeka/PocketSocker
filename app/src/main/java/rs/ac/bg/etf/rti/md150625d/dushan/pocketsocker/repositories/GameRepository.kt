package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.util.Log
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.GameDatabase
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.MatchDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.PlayerDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player

class GameRepository (application: Application) {
    private lateinit var matchDao: MatchDao
    private lateinit var playerDao: PlayerDao
    var allMatches: LiveData<List<Match>>? = null

    init {
        val database = GameDatabase.getDatabaseInstance(application)
        matchDao = database.matchDao()
        playerDao = database.playerDao()
        allMatches = matchDao.getAllMatches()
    }

    fun insertPlayer(player: Player) {
        Thread(Runnable { playerDao.insertPlayer(player) }).start()
    }

    fun updatePlayer(player: Player) {
        Thread(Runnable { playerDao.updatePlayer(player) }).start()
    }

    fun findPlayerByName(name: String) : Player? {
        var player: Player? = null
        Thread(Runnable { player = playerDao.getPlayerByName(name) }).start()
        return player
    }

    fun insertMatch(match: Match) {
        Thread(Runnable { matchDao.insertMatch(match) }).start()
    }

    fun getAllMatchesForPLayers(player1Name: String, player2Name: String)
            : LiveData<List<Match>>? {
        return matchDao.getMatchesForPlayers(player1Name, player2Name)
    }


}
