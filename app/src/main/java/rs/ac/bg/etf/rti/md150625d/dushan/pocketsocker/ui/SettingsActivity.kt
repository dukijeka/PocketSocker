package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R

class SettingsActivity : AppCompatActivity() {

    var field: Int = R.drawable.grass_background
    var timeLimited: Boolean = true
    var limit: Int = 60
    var speed: Int = 200



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initializeDefaultValues()
        restoreSavedSettings()
        restoreUI()
    }

    private fun restoreUI() {
        if (field == R.drawable.grass_background) {
            backgroundSpinner.setSelection(0, true)
        } else {
            backgroundSpinner.setSelection(1, true)
        }

        if (timeLimited) {
            timeLimitRadioButton.isChecked = true
            goalLimitRadioButtn.isChecked  = false
        } else {
            timeLimitRadioButton.isChecked = false
            goalLimitRadioButtn.isChecked  = true
        }

        limitEditText.setText(limit.toString())

        when {
            speed <= 100 -> speedSpinner.setSelection(0, true)
            speed > 200 -> speedSpinner.setSelection(2, true)
            else -> speedSpinner.setSelection(1, true)
        }

    }

    private fun restoreSavedSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        timeLimited = if (sharedPreferences.contains("timeLimited")) {
            sharedPreferences.getBoolean("timeLimited", timeLimited)
        } else {
            sharedPreferences.getBoolean("timeLimitedDefault", timeLimited)
        }

        field = if (sharedPreferences.contains("field")) {
            sharedPreferences.getInt("field", field)
        } else {
            sharedPreferences.getInt("fieldDefault", field)
        }

        limit = if (sharedPreferences.contains("limit")) {
            sharedPreferences.getInt("limit", limit)
        } else {
            sharedPreferences.getInt("limitDefault", limit)
        }

        speed = if (sharedPreferences.contains("speed")) {
            sharedPreferences.getInt("speed", speed)
        } else {
            sharedPreferences.getInt("speedDefault", speed)
        }
    }

    private fun initializeDefaultValues() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        // default values
        with(sharedPreferences.edit()) {
            putBoolean("timeLimitedDefault", true)
            putInt("fieldDefault", R.drawable.grass_background)
            putInt("limitDefault", 60)
            putInt("speedDefault", 200)
            apply()
        }

    }

    fun onSaveButtonClicked(view: View) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        // default values
        with(sharedPreferences.edit()) {
            putBoolean("timeLimited", timeLimitRadioButton.isChecked)
            when {
                backgroundSpinner.selectedItemPosition == 0 -> putInt("field", R.drawable.grass_background)
                else -> putInt("field", R.drawable.concrete_background)
            }

            putInt("limit", limitEditText.text.toString().toInt())

            when {
                speedSpinner.selectedItemPosition == 0 -> putInt("speed", 100)
                speedSpinner.selectedItemPosition == 1 -> putInt("speed", 200)
                speedSpinner.selectedItemPosition == 2 -> putInt("speed", 800)
            }

            commit()
        }
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    fun onDefaultButtonClicked(v: View) {
        // remove old settings
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)


        with(sharedPreferences.edit()) {
            remove("timeLimited")
            remove("field")
            remove("limit")
            remove("speed")
            apply()
        }

        restoreSavedSettings()
        restoreUI()

    }
}
