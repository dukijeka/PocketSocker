package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.res.ResourcesCompat
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_team_select.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.TeamSelectViewModel
import java.io.InputStream

class TeamSelectActivity : AppCompatActivity() {

    private lateinit var model: TeamSelectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_select)

        model = ViewModelProviders.of(this).get(TeamSelectViewModel::class.java)
        model.flags = assets.list("image_flags")

        // workaround android studio checkbox font bug
        player1ComputerCheckBox.typeface = ResourcesCompat.getFont(this, R.font.frijole)
        player2ComputerCheckBox.typeface = ResourcesCompat.getFont(this, R.font.frijole)

    }

    fun onFirstPlayerLeftArrowClick(view: View) {
        // TODO: log, check if model.flags == null
        model.player1Flag--
        if (model.player1Flag < 0) { // if current flag was 0, select the last flag
            model.player1Flag = model.flags!!.size - 1
        }

        var inputStream: InputStream = assets.open("image_flags/" +
                model.flags!![model.player1Flag])

        firstTeamFlag.setImageBitmap(BitmapFactory.decodeStream(inputStream))
    }

    fun onSecondPlayerLeftArrowClick(view: View) {
        // TODO: log, check if model.flags == null
        model.player2Flag--
        if (model.player2Flag < 0) { // if current flag was 0, select the last flag
            model.player2Flag = model.flags!!.size - 1
        }

        var inputStream: InputStream = assets.open("image_flags/" +
                model.flags!![model.player2Flag])

        secondTeamFlag.setImageBitmap(BitmapFactory.decodeStream(inputStream))
    }

    fun onFirstPlayerRightArrowClick(view: View) {
        // TODO: log, check if model.flags == null
        model.player1Flag = (model.player1Flag + 1) % model.flags!!.size

        var inputStream: InputStream = assets.open("image_flags/" +
                model.flags!![model.player1Flag])

        firstTeamFlag.setImageBitmap(BitmapFactory.decodeStream(inputStream))
    }

    fun onSecondPlayerRightArrowClick(view: View) {
        // TODO: log, check if model.flags == null
        model.player2Flag = (model.player2Flag + 1) % model.flags!!.size

        var inputStream: InputStream = assets.open("image_flags/" +
                model.flags!![model.player2Flag])

        secondTeamFlag.setImageBitmap(BitmapFactory.decodeStream(inputStream))
    }

    fun onStartGameClick(view: View) {
        if (player1NameEditText.text.toString() == "") {
            Toast.makeText(this, "Please enter first player's name!", Toast.LENGTH_SHORT).show()
            return
        }

        if (player2NameEditText.text.toString() == "") {
            Toast.makeText(this, "Please enter second player's name!", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        // delete previous saved state
        with (sharedPreferences.edit()) {
            remove(getString(R.string.preference_file_key))
            commit()
        }

        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("player1Name", player1NameEditText.text.toString().toUpperCase())
        intent.putExtra("player2Name", player2NameEditText.text.toString().toUpperCase())
        intent.putExtra("player1FlagNumber", model.player1Flag)
        intent.putExtra("player2FlagNumber", model.player2Flag)
        intent.putExtra("isPlayer1Computer", player1ComputerCheckBox.isChecked)
        intent.putExtra("isPlayer2Computer", player2ComputerCheckBox.isChecked)

        startActivity(intent)
    }

    fun onPlayer1ComputerCheckboxClicked(v: View) {
        if (player1ComputerCheckBox.isChecked) {
            player1NameEditText.setText("SKYNET")
            player1NameEditText.setTextColor(getColor(android.R.color.holo_red_dark))
            player1NameEditText.isEnabled = false
        } else {
            player1NameEditText.setTextColor(getColor(R.color.colorAccent))
            player1NameEditText.setText("")
            player1NameEditText.isEnabled = true
        }
    }

    fun onPlayer2ComputerCheckboxClicked(v: View) {
        if (player2ComputerCheckBox.isChecked) {
            player2NameEditText.setText("HAL 9000")
            player2NameEditText.setTextColor(getColor(android.R.color.holo_red_dark))
            player2NameEditText.isEnabled = false
        } else {
            player2NameEditText.setText("")
            player2NameEditText.setTextColor(getColor(R.color.colorAccent))
            player2NameEditText.isEnabled = true
        }
    }
}
