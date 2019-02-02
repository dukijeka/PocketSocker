package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.gestureDetection

import android.graphics.Rect
import android.view.GestureDetector
import android.view.MotionEvent
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers.GameController

class GameGestureDetector(val controller: GameController) : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent?): Boolean {
        if (e == null) {
            return false
        }

        // x and y are the coordinates of the center of the touch
        // assumed radius of the touch is 80
        controller.selectPlayer(
            Rect(
                (e.x - 40).toInt(),
                (e.y - 40).toInt(),
                (e.x + 40).toInt(),
                (e.y + 40).toInt()
            )
        )
        return true
    }

    override fun onFling(
        e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float)
            : Boolean {
        controller.moveSelectedPlayer(velocityX, velocityY)
        return super.onFling(e1, e2, velocityX, velocityY)
    }
}