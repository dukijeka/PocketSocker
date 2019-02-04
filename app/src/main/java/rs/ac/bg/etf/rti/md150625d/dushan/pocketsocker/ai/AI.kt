package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ai

import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers.GameController
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.PlayerType
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.util.*

abstract class AI (
    protected var model: GameViewModel,
    protected var controller: GameController,
    protected var myPlayerType: PlayerType
) {

    private var executionRunnable = Runnable {
        try {
            Thread.sleep(
                ( Random().nextInt(Math.abs(model.timePerMove - 2)).toLong() + 1 ) * 1000
            )

            var moveData = calculateMove()

            controller.selectPlayer(moveData.touch)
            controller.moveSelectedPlayer(moveData.veocityX, moveData.veocityY)
        } catch (e: InterruptedException) {

        }
    }

    private var thread: Thread = Thread(executionRunnable)

    fun playMove() {
        thread = Thread(executionRunnable)
        thread.start()
    }

    fun cancelMove() {
        thread.interrupt()
    }

    protected abstract fun calculateMove() : MoveData
}