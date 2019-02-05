package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels

import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Ball
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Figure
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.graphics.figures.Player
import java.io.Serializable
import java.util.concurrent.CopyOnWriteArrayList

class GameViewModel : Serializable {
    // these are changed automatically depending on pixel density
    @get: Synchronized @set: Synchronized
    var PLAYER_SIZE = 200
    @get: Synchronized @set: Synchronized
    var BALL_SIZE = 100
    @get: Synchronized @set: Synchronized
    var GOAL_HEIGHT = 300
    @get: Synchronized @set: Synchronized
    var GOAL_WIDTH = 50

    @get: Synchronized @set: Synchronized
    var flags: Array<String>? = null

    var player1FlagNumber = 0
    var player2FlagNumber = 0

    @get: Synchronized @set: Synchronized
    var player1FlagBitmap : Bitmap? = null
    @get: Synchronized @set: Synchronized
    var player2FlagBitmap : Bitmap? = null

    @get: Synchronized @set: Synchronized
    var ballBitmap : Bitmap? = null

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

    @get: Synchronized @set: Synchronized
    var ball: Ball? = null

    @get: Synchronized @set: Synchronized
    var player1Score: Int = 0

    @get: Synchronized @set: Synchronized
    var player2Score: Int = 0

    @get: Synchronized @set: Synchronized
    var timeLeft: Int = 60

    @get: Synchronized @set: Synchronized
    var goalLimit: Int = 10

    @get: Synchronized @set: Synchronized
    var timeLimited = false

    @get: Synchronized @set: Synchronized
    var timeLeftToMove = 10

    val timePerMove = 5

    var loadedModel = false

    @get: Synchronized @set: Synchronized
    var isPlayer1Computer: Boolean = false // default

    @get: Synchronized @set: Synchronized
    var isPlayer2Computer: Boolean = false // default

    @get: Synchronized @set: Synchronized
    var maxSpeed = 800

}