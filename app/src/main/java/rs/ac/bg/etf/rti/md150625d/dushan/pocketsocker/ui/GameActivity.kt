package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui

import android.arch.lifecycle.ViewModelProviders
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
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.GameImageView

import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class GameActivity : AppCompatActivity() {

    private lateinit var model: GameViewModel
    private lateinit var controller: GameController
    private lateinit var gestureDetector: GestureDetectorCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        model = ViewModelProviders.of(this).get(GameViewModel::class.java)
        model.flags = assets.list("image_flags")

        if (intent != null) { // intent shouldn't be null here
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

            model.ballBitmap = BitmapFactory.decodeResource(resources, R.drawable.soccer_ball)

            controller = GameController(model, gameImageView, this)
        }
        gestureDetector = GestureDetectorCompat(this, GameGestureDetector(controller))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // enter full screen gaming mode(sticky immersive)
        if (hasFocus) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN or
                    SYSTEM_UI_FLAG_HIDE_NAVIGATION

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}
