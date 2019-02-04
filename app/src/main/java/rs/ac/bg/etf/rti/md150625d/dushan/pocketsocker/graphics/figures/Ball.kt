package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures

import android.graphics.*
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class Ball (
    model: GameViewModel, x: Int = 0, y: Int = 0, speedX: Int = 0, speedY: Int = 0)
    : Figure(
        x,
        y,
        speedX,
        speedY,
        model,
        model.BALL_SIZE,
        model.BALL_SIZE
    ) {


    override fun draw(canvas: Canvas) {
        if (canvas == null) {
            return
        }

        canvas.drawBitmap(model.ballBitmap, null, bounds, null)
//        canvas.drawCircle(
//            bounds.exactCenterX(), bounds.exactCenterY(), model.BALL_SIZE.toFloat() / 2
//            , paint)
    }
}

