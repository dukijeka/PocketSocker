package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGameButtonClicked(view: View) {
        val intent = Intent(this, TeamSelectActivity::class.java)
        startActivity(intent)
    }
}
