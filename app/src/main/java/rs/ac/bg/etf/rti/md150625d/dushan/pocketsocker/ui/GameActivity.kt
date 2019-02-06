package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View.*
import kotlinx.android.synthetic.main.activity_game.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers.GameController
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.gestureDetection.GameGestureDetector

import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.io.FileOutputStream
import android.content.Context.MODE_PRIVATE
import android.content.Context
import android.content.SharedPreferences
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Match
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database.entity.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.repositories.GameRepository
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import java.util.*


class GameActivity : AppCompatActivity() {

    private lateinit var model: GameViewModel
    private lateinit var controller: GameController
    private lateinit var gestureDetector: GestureDetectorCompat

    private var field: Int = R.drawable.grass_background
    private var timeLimited: Boolean = true
    private var limit: Int = 60
    private var speed: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R.layout.activity_game)
        //model = ViewModelProviders.of(this).get(GameViewModel::class.java)
        model = GameViewModel()
        model.flags = assets.list("image_flags")



        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())

        if (intent != null) { // intent shouldn't be null here
            if (sharedPreferences.contains(getString(R.string.preference_file_key))) {
                // load model from file
                val fis = this.openFileInput("storedGame.obj")
                val inStream = ObjectInputStream(fis)
                model = inStream.readObject() as GameViewModel
                inStream.close()
                fis.close()

                // restore non-serializable objects
                model.player1FlagBitmap = BitmapFactory.decodeStream(
                    assets.open("image_flags/" + model.flags!![model.player1FlagNumber])
                )
                model.player2FlagBitmap = BitmapFactory.decodeStream(
                    assets.open("image_flags/" + model.flags!![model.player2FlagNumber])
                )

                model.ballBitmap = BitmapFactory.decodeResource(resources, rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R.drawable.soccer_ball)
                model.loadedModel = true




            } else {
                initializeModel()
            }

            gameImageView.background = ContextCompat.getDrawable(this, model.background)

            controller = GameController(model, gameImageView, this)
        }


        gestureDetector = GestureDetectorCompat(this, GameGestureDetector(controller))


    }

    private fun initializeModel() {
        model.player1FlagNumber = intent.getIntExtra("player1FlagNumber",
            0)
        model.player2FlagNumber = intent.getIntExtra("player2FlagNumber",
            0)
        model.player1Name = intent.getStringExtra("player1Name")
        model.player2Name = intent.getStringExtra("player2Name")

        model.player1FlagBitmap = BitmapFactory.decodeStream(
            assets.open("image_flags/" + model.flags!![model.player1FlagNumber])
        )

        model.player2FlagBitmap = BitmapFactory.decodeStream(
            assets.open("image_flags/" + model.flags!![model.player2FlagNumber])
        )

        model.ballBitmap = BitmapFactory.decodeResource(resources, rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R.drawable.soccer_ball)

        model.isPlayer1Computer = intent.getBooleanExtra("isPlayer1Computer", false)
        model.isPlayer2Computer = intent.getBooleanExtra("isPlayer2Computer", false)

        // restore saved settings
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        model.timeLimited = if (sharedPreferences.contains("timeLimited")) {
            sharedPreferences.getBoolean("timeLimited", timeLimited)
        } else {
            sharedPreferences.getBoolean("timeLimitedDefault", timeLimited)
        }

        model.background = if (sharedPreferences.contains("field")) {
            sharedPreferences.getInt("field", field)
        } else {
            sharedPreferences.getInt("fieldDefault", field)
        }

        model.goalLimit = if (sharedPreferences.contains("limit")) {
            sharedPreferences.getInt("limit", limit)
        } else {
            sharedPreferences.getInt("limitDefault", limit)
        }

        model.timeLeft = if (sharedPreferences.contains("limit")) {
            sharedPreferences.getInt("limit", limit)
        } else {
            sharedPreferences.getInt("limitDefault", limit)
        }

        model.maxSpeed = if (sharedPreferences.contains("speed")) {
            sharedPreferences.getInt("speed", speed)
        } else {
            sharedPreferences.getInt("speedDefault", speed)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // enter full screen gaming mode(sticky immersive)
        if (hasFocus) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN or
                    SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        controller.pauseGame()
        saveGame()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun finishGame() {
        // update database
        val repository: GameRepository = GameRepository(applicationContext as Application, model.player1Name, model.player2Name)
        //try to find first player
        var player1 = repository.player1
        var player2 = repository.player2

        if (player1 == null) {
            player1 = Player(model.player1Name, 0)
        }

        if (model.player1Score > model.player2Score) {
            player1.winsCnt++
        }

        if (player2 == null) {
            player2 = Player(model.player2Name, 0)
        }

        if (model.player2Score > model.player1Score) {
            player2.winsCnt++
        }

        repository.insertPlayer(player1)
        repository.insertPlayer(player2)

        repository.insertMatch(
            Match(
                model.player1Name,
                model.player2Name,
                Date(), model.player1Score,
                model.player2Score
            )
        )


        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("player1Name", model.player1Name)
        intent.putExtra("player2Name", model.player2Name)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        controller.pauseGame()
    }

    override fun onResume() {
        super.onResume()
        controller.resumeGame()
    }

    override fun onDestroy() {
        super.onDestroy()

        controller.pauseGame()
        saveGame()

    }

    private fun saveGame() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())

        // remove non-serializable objects from model
        model.player1FlagBitmap = null
        model.player2FlagBitmap = null
        model.ballBitmap = null

        // write model to file
        val fos = this.openFileOutput("storedGame.obj", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(model)
        os.close()
        fos.close()


        with(sharedPreferences.edit()) {
            putBoolean(getString(R.string.preference_file_key), true)
            commit()
        }
    }
}
