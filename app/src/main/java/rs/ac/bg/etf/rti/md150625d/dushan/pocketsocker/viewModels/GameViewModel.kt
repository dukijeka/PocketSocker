package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Figure
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class GameViewModel: ViewModel() {
    // constants
    val PLAYER_SIZE = 200
    val BALL_SIZE = 100

    @get: Synchronized @set: Synchronized
    var flags: Array<String>? = null

    var player1FlagNumber = 0
    var player2FlagNumber = 0

    @get: Synchronized @set: Synchronized
    var player1FlagBitmap : Bitmap? = null
    @get: Synchronized @set: Synchronized
    var player2FlagBitmap : Bitmap? = null

    var player1Name = ""
    var player2Name = ""

    @get: Synchronized @set: Synchronized
    var figures: CopyOnWriteArrayList<Figure> = CopyOnWriteArrayList()

    @get: Synchronized @set: Synchronized
    var canvasHeight: Int = 0
    @get: Synchronized @set: Synchronized
    var canvasWidth: Int = 0

    @get: Synchronized @set: Synchronized
    var selectedPlayer: Player? = null

}