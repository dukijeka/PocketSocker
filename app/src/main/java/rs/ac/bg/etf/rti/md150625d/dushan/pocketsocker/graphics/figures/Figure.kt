package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures



import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

abstract class Figure// initiate bounds
    (
    x: Int = 0,
    y: Int = 0,
    var speedX: Int = 0,
    var speedY: Int = 0,
    var model: GameViewModel,
    var boundWidth: Int,
    var boundHeight: Int
) : ShapeDrawable() {

    var x: Int = x
        set(value) {
            field = value
            // refresh bounds
            bounds = Rect(x, y, x + boundWidth, y + boundHeight)
        }
    var y: Int = y
        set(value) {
            field = value
            // refresh bounds
            bounds = Rect(x, y, x + boundWidth, y + boundHeight)
        }

    init {
        this.x = x
        this.y = y
        bounds = Rect(x, y, x + boundWidth, y + boundHeight)
    }


}