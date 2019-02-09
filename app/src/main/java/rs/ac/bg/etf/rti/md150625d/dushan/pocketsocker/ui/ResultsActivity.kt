package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_results.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.repositories.GameRepository
import java.util.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        if (intent != null) {
            val player1Name = intent.getStringExtra("player1Name")
            val player2Name = intent.getStringExtra("player2Name")

            val repository: GameRepository = GameRepository(applicationContext as Application, player1Name, player2Name)

            val player1 = repository.player1
            val player2 = repository.player2

            val allMatchesForPlayers = repository.allMatchesForPlayers

            fillTheList(allMatchesForPlayers,
                player1Name,
                player2Name,
                repository.player1Wins,
                repository.player2Wins)

            allMatchesForPlayers?.observe(this, android.arch.lifecycle.Observer {
                fillTheList(allMatchesForPlayers,
                    player1Name,
                    player2Name,
                    repository.player1Wins,
                    repository.player2Wins)
            })
        }


    }

    private fun fillTheList(allMatchesForPlayers: LiveData<List<Match>>?,
                            player1Name: String,
                            player2Name: String,
                            player1Wins: Int,
                            player2Wins: Int) {
        val results = LinkedList<String>()

        if (allMatchesForPlayers == null) {
            return
        }

        if (allMatchesForPlayers.value == null) {
            return
        }

        for (match in allMatchesForPlayers.value!!) {
            results.add("" + match.date + " "
                    + match.player1 + " " + match.player1Score
                    + " : " + match.player2Score + " " + match.player2)
        }

        results.add("TOTAL SCORE: " + player1Name + " " + player1Wins
                + " : " + player2Wins + " " + player2Name)

        results.add(" ")
        results.add(" ")
        results.add(" ")
        results.add(" ")

        listView.adapter =
            ArrayAdapter<String>(this, R.layout.list_item, results)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
