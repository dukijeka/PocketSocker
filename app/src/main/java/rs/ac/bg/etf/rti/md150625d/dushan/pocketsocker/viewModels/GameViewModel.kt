package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Figure
import java.util.*

class GameViewModel: ViewModel() {
    // constants
    val PLAYER_SIZE = 200
    val BALL_SIZE = 100

    var flags: Array<String>? = null

    var player1FlagNumber = 0
    var player2FlagNumber = 0

    var player1FlagBitmap : Bitmap? = null
    var player2FlagBitmap : Bitmap? = null

    var player1Name = ""
    var player2Name = ""

    var figures: LinkedList<Figure> = LinkedList()

    var canvasHeight: Int = 0
    var canvasWidth: Int = 0

}