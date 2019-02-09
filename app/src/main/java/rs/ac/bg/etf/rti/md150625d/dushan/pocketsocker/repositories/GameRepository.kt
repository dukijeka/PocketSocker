package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.util.Log
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.GameDatabase
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.MatchDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.dao.PlayerDao
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player

class GameRepository (application: Application,
                      player1Name: String,
                      player2Name: String) {
    private lateinit var matchDao: MatchDao
    private lateinit var playerDao: PlayerDao
    var allMatches: List<Match>
    var player1: Player?
    var player2: Player?
    var allMatchesForPlayers: LiveData<List<Match>>
    var player1Wins: Int
    var player2Wins: Int

    init {
        val database = GameDatabase.getDatabaseInstance(application)
        matchDao = database.matchDao()
        playerDao = database.playerDao()
        allMatches = matchDao.getAllMatches()
        player1 = playerDao.getPlayerByName(player1Name)
        player2 = playerDao.getPlayerByName(player2Name)
        allMatches = matchDao.getAllMatches()

        allMatchesForPlayers = matchDao.getMatchesForPlayers(player1Name, player2Name)

        player1Wins = matchDao.getPlayer1Wins(player1Name, player2Name)
        player2Wins = matchDao.getPlayer2Wins(player1Name, player2Name)
    }

    fun insertPlayer(player: Player) {
        Thread(Runnable { playerDao.insertPlayer(player) }).start()
    }

    fun updatePlayer(player: Player) {
        Thread(Runnable { playerDao.updatePlayer(player) }).start()
    }

//    fun findPlayerByName(name: String) : Player? {
//        var player: Player? = null
//        player = playerDao.getPlayerByName(name)
//        return player
//    }

    fun insertMatch(match: Match) {
        Thread(Runnable { matchDao.insertMatch(match) }).start()
    }

//    fun getAllMatchesForPlayers(player1Name: String, player2Name: String)
//            : LiveData<List<Match>>? {
//        var res: LiveData<List<Match>>? = null
//
//        Thread(Runnable {
//            res = matchDao.getMatchesForPlayers(player1Name, player2Name)
//        }).start()
//
//        return res
//
//    }


}
