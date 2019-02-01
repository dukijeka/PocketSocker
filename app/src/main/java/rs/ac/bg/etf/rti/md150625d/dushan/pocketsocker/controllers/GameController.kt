package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.GameImageView
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.util.*

class GameController(private val model: GameViewModel,
                     private val gameImageView: GameImageView) {

    init {
        gameImageView.controller = this
        gameImageView.model = model

    }

    fun sizeChanged() {
        placeFigures()
    }

    private fun placeFigures() {
        // delete any previous figures by removing them from the model
        model.figures = LinkedList()

        // add player1 football players
        val team1Player1 =
            Player(PlayerType.PLAYER1, model,
                (0.20 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.1 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team1Player1)

        val team1Player2 =
            Player(PlayerType.PLAYER1, model,
                (0.35 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.5 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team1Player2)

        val team1Player3 =
            Player(PlayerType.PLAYER1, model,
                (0.20 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.9 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team1Player3)

        // add player 2 footbalPlayers
        val team2Player1 =
            Player(PlayerType.PLAYER2, model,
                (0.80 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.1 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team2Player1)

        val team2Player2 =
            Player(PlayerType.PLAYER2, model,
                (0.65 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.5 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team2Player2)

        val team2Player3 =
            Player(PlayerType.PLAYER2, model,
                (0.80 * model.canvasWidth).toInt() - model.PLAYER_SIZE / 2,
                (0.9 * model.canvasHeight).toInt() - model.PLAYER_SIZE / 2
            )
        model.figures.push(team2Player3)

        gameImageView.invalidate()
    }
}