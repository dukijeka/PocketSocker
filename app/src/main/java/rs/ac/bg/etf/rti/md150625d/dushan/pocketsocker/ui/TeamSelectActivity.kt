package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("player1Name", player1NameEditText.text.toString())
        intent.putExtra("player2Name", player2NameEditText.text.toString())
        intent.putExtra("player1FlagNumber", model.player1Flag)
        intent.putExtra("player2FlagNumber", model.player2Flag)
        startActivity(intent)
    }
}
