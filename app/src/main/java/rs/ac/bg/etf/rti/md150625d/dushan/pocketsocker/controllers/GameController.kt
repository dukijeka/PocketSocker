package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import android.graphics.Rect
import android.media.MediaPlayer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.GameImageView
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Ball
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Goal
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui.GameActivity
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.util.concurrent.CopyOnWriteArrayList




class GameController(private val model: GameViewModel,
                     private val gameImageView: GameImageView, private var activity: GameActivity) {

    @get: Synchronized @set: Synchronized
    var turn: Turn = Turn.PLAYER1 // default

    private var mediaPlayerKick: MediaPlayer? = MediaPlayer.create(activity, R.raw.kick)
    private var mediaPlayerApplause: MediaPlayer? = MediaPlayer.create(activity, R.raw.applause)

    var refreshThread : RefreshThread? = null
    var gameTimer: GameTimer? = null

    private var state: State = State.SELECTION

    init {
        gameImageView.controller = this
        gameImageView.model = model

        // start refreshThread
        refreshThread = RefreshThread(model, this)

        refreshThread?.start()

        // start timer
        gameTimer = GameTimer(this, model)

        gameTimer?.start()
    }

    fun sizeChanged() {
        placeFigures()
    }

    private fun placeFigures() {
        //don't reset figures if we're loading saved game
        if (model.loadedModel) {
            model.loadedModel = false
            updateScore()
            return
        }
        // delete any previous figures by removing them from the model
        model.figures = CopyOnWriteArrayList()

        // add player1 football players
        val team1Player1 =
            Player(PlayerType.PLAYER1, model,
                (0.20 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.1 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team1Player1)

        val team1Player2 =
            Player(PlayerType.PLAYER1, model,
                (0.35 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.5 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team1Player2)

        val team1Player3 =
            Player(PlayerType.PLAYER1, model,
                (0.20 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.9 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team1Player3)

        // add player 2 footbalPlayers
        val team2Player1 =
            Player(PlayerType.PLAYER2, model,
                (0.80 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.1 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team2Player1)

        val team2Player2 =
            Player(PlayerType.PLAYER2, model,
                (0.65 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.5 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team2Player2)

        val team2Player3 =
            Player(PlayerType.PLAYER2, model,
                (0.80 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.9 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.add(team2Player3)

        // add ball
        val ball = Ball(model, model.canvasWidth / 2 - model.BALL_SIZE / 2,
            model.canvasHeight / 2 - model.BALL_SIZE / 2)
        model.ball = ball
        model.figures.add(ball)

        addGoals()

        updateScore()
        updateTime()
    }

    fun addGoals() {
        // remove all goals from figures
        for (figure in model.figures) {
            if (figure is Goal) {
                model.figures.remove(figure)
            }
        }

        // add goals
        val goal1 = Goal(model, 0, model.canvasHeight / 2 - model.GOAL_HEIGHT / 2)
        model.figures.add(goal1)

        val goal2 = Goal(model, model.canvasWidth - model.GOAL_WIDTH,
            model.canvasHeight / 2 - model.GOAL_HEIGHT / 2)
        model.figures.add(goal2)
        gameImageView.invalidate()
    }

    fun selectPlayer(touch: Rect) {
        if (state != State.SELECTION) {
            return
        }

        // find the touched figure
        for (figure in model.figures) {
            if (figure !is Player) {
                continue
            }

            if (touch.intersect(figure.bounds)) {
                if (
                    turn == Turn.PLAYER1 && figure.type == PlayerType.PLAYER1
                    || turn == Turn.PLAYER2 && figure.type == PlayerType.PLAYER2
                ) {
                    model.selectedPlayer = figure
                    state = State.MOVEMENT
                    return
                }
            }
        }

    }

    fun moveSelectedPlayer(velocityX: Float, velocityY: Float) {
        if (model.selectedPlayer == null || state == State.SELECTION) {
            return
        }

        model.selectedPlayer!!.speedX = velocityX.toInt() / 50
        model.selectedPlayer!!.speedY = velocityY.toInt() / 50
        state = State.SELECTION


        switchPlayers()
        model.timeLeftToMove = model.timePerMove
    }

    private fun switchPlayers() {
        turn = if (turn == Turn.PLAYER1) {
            Turn.PLAYER2
        } else {
            Turn.PLAYER1
        }
    }

    fun refreshGameView() {
        gameImageView.invalidate()
    }

    fun reportGoal() {
        activity.runOnUiThread {
            Toast.makeText(activity, "Goal", Toast.LENGTH_SHORT).show()
        }

        if (model.ball != null && model.ball!!.x <= 0) {
            model.player2Score++
        } else {
            model.player1Score++
        }

        playApplause()

        placeFigures()
        model.timeLeftToMove = model.timePerMove
    }

    fun gameOverTimeUp() {
        activity.runOnUiThread {
            activity.finishGame()
        }
    }

    fun timeUp() {
        switchPlayers()
        model.timeLeftToMove = model.timePerMove
    }

    private fun updateScore() {
        activity.runOnUiThread {
            val text = "SCORE: " + model.player1Score + " - " + model.player2Score
            activity.resultTextView.text = text

        }
    }

    fun updateTime() {
        activity.runOnUiThread {
            val totalTimeLeft = "TIME: " + model.timeLeft
            activity.timeTextView.text = totalTimeLeft

            val timeLeftToMove = "" + model.timeLeftToMove
            activity.timeLeftTextView.text = timeLeftToMove
        }
    }

    fun pauseGame() {
        refreshThread?.pauseGame()

        // we don't want timer to call our gameOverTimeUp()
        // method, se we unregister ourselves before cancelling it
        gameTimer?.paused = true

        gameTimer?.cancel()
        gameTimer = null
    }



    fun resumeGame() {
        if (gameTimer != null) {
            return
        }
        gameTimer = GameTimer(this, model)
        gameTimer?.start()
        refreshThread?.resumeGame()
    }

    fun playApplause() {

            mediaPlayerApplause?.start()

    }

    fun playKick() {
            mediaPlayerKick?.start()

    }


}