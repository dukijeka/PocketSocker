package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers.GameController
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel


class GameImageView : AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    var controller : GameController? = null
    var model : GameViewModel? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (model == null || canvas == null) {
            return
        }

        for (figure in model!!.figures) {
            figure.draw(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (model == null) {
            return
        }

        // TODO: explain this mess

        model!!.canvasHeight = h
        model!!.canvasWidth = w

        if (w != 0 && h != 0 && oldw == 0 && oldh == 0) {
                controller?.sizeChanged()
        }

        controller?.addGoals()

    }
}