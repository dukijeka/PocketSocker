package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures

import android.graphics.Canvas
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class Player(var type: PlayerType, model:GameViewModel ,x: Int = 0, y: Int = 0,
             speedX: Int = 0, speedY: Int = 0) : Figure(x, y, speedX, speedY, model,
             model.PLAYER_SIZE, model.PLAYER_SIZE
        ) {

    override fun draw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }

        when(type) {
            PlayerType.PLAYER1 -> {
                canvas!!.drawBitmap(model.player1FlagBitmap, null, bounds, paint)
            }

            PlayerType.PLAYER2 -> {
                canvas!!.drawBitmap(model.player2FlagBitmap, null, bounds, paint)
            }
        }
    }


}