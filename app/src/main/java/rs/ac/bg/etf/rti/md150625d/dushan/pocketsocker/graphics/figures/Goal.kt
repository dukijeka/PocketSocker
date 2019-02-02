package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures

import android.graphics.Canvas
import android.graphics.Color
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class Goal(
    model: GameViewModel, x: Int = 0, y: Int = 0, speedX: Int = 0, speedY: Int = 0)
    : Figure(x, y, speedX, speedY, model, model.GOAL_WIDTH, model.GOAL_HEIGHT) {

    init {
        paint.color = Color.GRAY
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(bounds, paint)
    }

}