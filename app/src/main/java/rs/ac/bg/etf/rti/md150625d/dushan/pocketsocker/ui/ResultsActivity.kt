package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
