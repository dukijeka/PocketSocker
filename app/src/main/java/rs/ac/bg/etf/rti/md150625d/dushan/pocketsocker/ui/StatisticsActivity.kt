package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.app.Application
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_statistics.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.repositories.GameRepository
import java.util.*

class StatisticsActivity : AppCompatActivity() {
    private var distinctPairs: LinkedList<Match> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        var repository: GameRepository = GameRepository(applicationContext as Application, "", "")

        var allMatches = repository.allMatches

        if (allMatches == null) {
            return
        }

        var results: LinkedList<String> = LinkedList()



        if (allMatches.size != 0) {
            distinctPairs.add(allMatches[0])
        }
        for (match in allMatches) {
            var shouldAdd = true
            for (match1 in distinctPairs) {
                if ((match.player1 == match1.player1 && match.player2 == match1.player2)
                    || (match.player2 == match1.player1 && match.player1 == match1.player2)
                ) {
                    shouldAdd = false // we already have this pair
                    break
                }
            }
            if (shouldAdd) {
                distinctPairs.add(match)
            }
        }

        for (match in distinctPairs) {
            repository = GameRepository(applicationContext as Application, match.player1, match.player2)
            results.add(match.player1 + " " + repository.player1Wins + " : "
                   + repository.player2Wins + " " + match.player2)
        }

        var adapter = ArrayAdapter<String>(this, R.layout.list_item, results)

        statisticsListView.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id -> goToMatchStatistics(position)

        }
        statisticsListView.adapter = adapter
    }


    fun goToMatchStatistics(matchPosition: Int) {
        val player1Name = distinctPairs[matchPosition].player1
        val player2Name = distinctPairs[matchPosition].player2

        val intent: Intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("player1Name", player1Name)
        intent.putExtra("player2Name", player2Name)
        startActivity(intent)
    }
}
