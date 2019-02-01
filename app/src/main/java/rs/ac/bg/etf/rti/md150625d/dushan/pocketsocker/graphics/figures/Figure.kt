package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures



import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

abstract class Figure : ShapeDrawable {
    var x: Int
        set(value) {
            field = value
            // refresh bounds
            bounds = Rect(x, y, x + model.PLAYER_SIZE, y + model.PLAYER_SIZE)
        }
    var y: Int
        set(value) {
            field = value
            // refresh bounds
            bounds = Rect(x, y, x + model.PLAYER_SIZE, y + model.PLAYER_SIZE)
        }
    var speedX: Int
    var speedY: Int
    var model: GameViewModel

    constructor(x: Int = 0, y: Int = 0, speedX: Int = 0, speedY: Int = 0, model: GameViewModel)
            : super() {
        this.model = model
        this.x = x
        this.y = y
        this.speedX = speedX
        this.speedY = speedY


        // initiate bounds
        bounds = Rect(x, y, x + model.PLAYER_SIZE, y + model.PLAYER_SIZE)
    }


}