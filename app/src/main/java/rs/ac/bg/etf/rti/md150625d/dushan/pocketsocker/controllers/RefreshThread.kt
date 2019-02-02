package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import android.R
import android.support.v4.content.ContextCompat
import android.graphics.PorterDuffColorFilter
import android.graphics.ColorFilter
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Goal


class RefreshThread(var model: GameViewModel, var controller: GameController)
    : Thread() {

    private var isRunning = true
    private var lastTime: Long? = null

    override fun run() {
        super.run()
        lastTime = System.currentTimeMillis()
        while (!interrupted()) {
            val timePassedinSeconds:Double =
                ((System.currentTimeMillis() - lastTime!!)).toDouble()
            if (isRunning) {
                markPlayersOnTurn()
                handleCollisions()
                // update positions for all figures
                for (figure in model.figures) {
                    figure.x =
                        (figure.x + figure.speedX * timePassedinSeconds/100).toInt()
                    figure.y =
                        (figure.y + figure.speedY * timePassedinSeconds/100).toInt()
                }

                controller.refreshGameView()
            }
            lastTime = System.currentTimeMillis()
            Thread.sleep(5)
        }
    }

    private fun markPlayersOnTurn() {
        val turn = controller.turn

        for (figure in model.figures) {

            if (figure is Player) {
                // shade players if it's not their turn
                if (figure.type == PlayerType.PLAYER1 && turn != Turn.PLAYER1) {
                    val filter = PorterDuffColorFilter(
                        0x99000000.toInt(),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    figure.paint.colorFilter = filter
                    continue
                } else if (figure.type == PlayerType.PLAYER1 && turn == Turn.PLAYER1) {
                    figure.paint.colorFilter = null
                    continue
                }

                if (figure.type == PlayerType.PLAYER2 && turn != Turn.PLAYER2) {
                    val filter = PorterDuffColorFilter(
                        0x99000000.toInt(),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    figure.paint.colorFilter = filter
                    continue
                } else if (figure.type == PlayerType.PLAYER2 && turn == Turn.PLAYER2){
                    figure.paint.colorFilter = null
                    continue
                }
            }
        }

    }

    private fun handleCollisions() {
        for ((i, figure) in model.figures.withIndex()) {
            if (figure is Goal) {
                continue
            }
            // border collisions
            if (figure.bounds.contains(0, figure.y)
                || figure.bounds.contains(model.canvasWidth, figure.y)) {
                figure.speedX = -figure.speedX
            }

            if (figure.bounds.contains(figure.x, 0)
                || figure.bounds.contains(figure.x, model.canvasHeight)) {
                figure.speedY = -figure.speedY
            }

            // football player collisions
            for ((j, figure2) in model.figures.withIndex()) {
                if (figure2 is Goal) {
                    continue
                }
                if (i < j && intersects(figure.bounds, figure2.bounds)) {
                    // change directions(speed signs) only if balls are moving towards each other

                    // calculate distance
                    var currentDistance: Double =
                        Math.sqrt(Math.pow((figure.x - figure2.x).toDouble(), 2.0)
                                + Math.pow((figure.y - figure2.y).toDouble(), 2.0))

                    // apply speeds(simulated time = 0.1s)
                    val simulatedTime = 0.1
                    val newX1 = (figure.x + figure.speedX * simulatedTime).toInt()
                    val newY1 = (figure.y + figure.speedY * simulatedTime).toInt()

                    val newX2 = (figure2.x + figure2.speedX * simulatedTime).toInt()
                    val newY2 = (figure2.y + figure2.speedY * simulatedTime).toInt()

                    // calculate new distance
                    var newDistance: Double =
                        Math.sqrt(Math.pow((newX1 - newX2).toDouble(), 2.0)
                                + Math.pow((newY1 - newY2).toDouble(), 2.0))

                    val elasticityFactor = 0.7

                    if (newDistance < currentDistance) {
//                        figure.speedX = (-figure.speedX * elasticityFactor
//                                - figure2.speedX * elasticityFactor).toInt()
//                        figure.speedY = (-figure.speedY * elasticityFactor
//                                - figure2.speedY * elasticityFactor).toInt()
//
//                        figure2.speedX = (-figure2.speedX * elasticityFactor
//                                - figure.speedX * elasticityFactor).toInt()
//                        figure2.speedY = (-figure2.speedY * elasticityFactor
//                                - figure.speedY * elasticityFactor).toInt()


                        figure.speedX = (-figure.speedX - figure2.speedX )
                        figure.speedY = (-figure.speedY - figure2.speedY )

                        figure2.speedX = (-figure2.speedX - figure.speedX )
                        figure2.speedY = (-figure2.speedY - figure.speedY )

                        figure.speedX = (elasticityFactor * figure.speedX).toInt()
                        figure.speedY = (elasticityFactor* figure.speedY).toInt()
                        figure2.speedX = (elasticityFactor * figure2.speedX).toInt()
                        figure2.speedY = (elasticityFactor * figure2.speedY).toInt()

                    }


                }
            }
        }
    }

    private fun intersects(bound1: Rect, bound2: Rect): Boolean{
        // calculate distance between centers
        val distance =
            Math.sqrt(
                Math.pow(
                    (bound1.exactCenterX() - bound2.exactCenterX()).toDouble(), 2.0
                )
                +
                Math.pow(
                    (bound1.exactCenterY() - bound2.exactCenterY()).toDouble(), 2.0
                )
            )

        return distance <= bound1.width() / 2 + bound2.width() / 2
    }

    fun pauseGame() {
        isRunning = false
    }

    fun resumeGame(){
        isRunning = true
    }
}