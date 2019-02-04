package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.audio

import android.media.MediaPlayer
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.R
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.ui.GameActivity

class GameMediaPlayer (activity: GameActivity) {
    private var mediaPlayerKick: MediaPlayer? = MediaPlayer.create(activity, R.raw.kick)
    private var mediaPlayerApplause: MediaPlayer? = MediaPlayer.create(activity, R.raw.applause)

    fun playApplause() {
        mediaPlayerApplause?.start()

    }

    fun playKick() {
        mediaPlayerKick?.start()
    }
}