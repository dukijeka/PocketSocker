package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        if (!sharedPreferences.contains(getString(R.string.preference_file_key))) {
            loadGameButton.setTextColor(getColor(R.color.material_grey_100))
        } else {
            loadGameButton.setTextColor(startGameButton.textColors)
        }
    }

    fun startGameButtonClicked(view: View) {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        // delete previous saved state
        with (sharedPreferences.edit()) {
            remove(getString(R.string.preference_file_key))
            commit()
        }

        val intent = Intent(this, TeamSelectActivity::class.java)
        startActivity(intent)
    }

    fun onLoadGameButtonClicked(view: View) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        if (!sharedPreferences.getBoolean(getString(R.string.preference_file_key), false)) {
            return // disable loading saved game if no game is saved
        }

        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun onSettingsButtonClicked(v: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun onStatisticsButtonClicked(v: View) {
        val intent = Intent(this, StatisticsActivity::class.java)
        startActivity(intent)
    }

}
