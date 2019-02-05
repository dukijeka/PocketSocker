package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures



import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel
import java.io.Serializable

abstract class Figure// initiate bounds
    (
    x: Int = 0,
    y: Int = 0,
    speedX: Int = 0,
    speedY: Int = 0,
    var model: GameViewModel,
    var boundWidth: Int,
    var boundHeight: Int
) : ShapeDrawable(), Serializable{

    var speedX: Int = speedX
        set(value) {
            val maxSpeed = model.maxSpeed
            if (value > 0) {
                field = if (value < maxSpeed) value else maxSpeed
            } else {
                field = if (value > -maxSpeed) value else -maxSpeed
            }
        }

    var speedY: Int = speedY
        set(value) {
            val maxSpeed = model.maxSpeed
            if (value > 0) {
                field = if (value < maxSpeed) value else maxSpeed
            } else {
                field = if (value > -maxSpeed) value else -maxSpeed
            }
        }

    @get: Synchronized @set:Synchronized
    var x: Int = x
        set(value) {
            field = value
            // refresh bounds
            bounds = Rect(x, y, x + boundWidth, y + boundHeight)
        }
    @get: Synchronized @set:Synchronized
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