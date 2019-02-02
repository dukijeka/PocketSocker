package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import android.graphics.Rect
import android.support.v4.view.GestureDetectorCompat
import android.view.GestureDetector
import android.view.MotionEvent
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.GameImageView
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class GameController(private val model: GameViewModel,
                     private val gameImageView: GameImageView) {

    var turn: Turn = Turn.PLAYER1 // default
    private var state: State = State.SELECTION

    init {
        gameImageView.controller = this
        gameImageView.model = model

        // start refreshThread
        RefreshThread(model, this).start()
    }

    fun sizeChanged() {
        placeFigures()
    }

    private fun placeFigures() {
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

        gameImageView.invalidate()
    }

    fun selectPlayer(touch: Rect) {
        if (state != State.SELECTION) {
            return
        }

        // find the touched figure
        for (figure in model.figures) {
            if (figure !is Player) {
                return
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

        model.selectedPlayer!!.speedX = velocityX.toInt() / 80
        model.selectedPlayer!!.speedY = velocityY.toInt() / 80
        state = State.SELECTION


        turn = if (turn == Turn.PLAYER1) {
            Turn.PLAYER2
        } else {
            Turn.PLAYER1
        }
    }

    fun refreshGameView() {
        gameImageView.invalidate()
    }
}