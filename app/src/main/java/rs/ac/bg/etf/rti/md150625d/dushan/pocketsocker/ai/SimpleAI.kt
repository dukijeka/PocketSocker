package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ai

import android.graphics.Rect
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers.GameController
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Ball
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Goal
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.util.*

class SimpleAI(
    model: GameViewModel,
    controller: GameController,
    myPlayerType: PlayerType
) : AI(model, controller, myPlayerType) {

    override fun calculateMove(): MoveData {
        val turn = controller.turn
        var minDistancePlayer: Player? = null
        var minDistance = 9999999

        // safeguard
        if (model.ball == null) {
            return MoveData(Rect(0,0,0,0), 0f, 0f)
        }

        for (figure in model.figures) {
            if (figure is Player) {
                if (figure.type == myPlayerType) {
                    var distanceToBall = Math.sqrt(
                        Math.pow(
                            (figure.bounds.centerX() - model.ball!!.bounds.centerX())
                                .toDouble(), 2.0
                        )
                                +
                                Math.pow(
                                    (figure.bounds.centerY() - model.ball!!.bounds.centerY())
                                        .toDouble(), 2.0
                                )
                    )

                    if (distanceToBall < minDistance) {
                        minDistance = distanceToBall.toInt()
                        minDistancePlayer = figure
                    }
                }
            }
        } // end for

        // safeguard
        if (minDistancePlayer == null) {
            return MoveData(Rect(0,0,0,0), 0f, 0f)
        }

        var velocityX: Float =
            ((model.ball!!.bounds.centerX() - minDistancePlayer.bounds.centerX()) * 80)
                .toFloat()

        var velocityY: Float =
            ((model.ball!!.bounds.centerY() - minDistancePlayer.bounds.centerY()) * 80)
                .toFloat()

        for (figure in model.figures) {
            if (figure !is Goal && figure !is Ball && figure != minDistancePlayer
                && figure.y == minDistancePlayer.y ) {
                velocityY += 500f
                velocityX = 1500f
            }
        }

        val touchRadius = 100
        val touch = Rect(Rect(
            (minDistancePlayer.x - touchRadius),
            (minDistancePlayer.y - touchRadius),
            (minDistancePlayer.x + touchRadius),
            (minDistancePlayer.y + touchRadius)
        ))

        return MoveData(touch, velocityX, velocityY)
    }
}