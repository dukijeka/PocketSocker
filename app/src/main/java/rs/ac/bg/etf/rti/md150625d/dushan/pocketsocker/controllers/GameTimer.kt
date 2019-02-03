package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import android.os.CountDownTimer
import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class GameTimer(private var controller: GameController, var model: GameViewModel) :
    CountDownTimer((model.timeLeft * 1000).toLong(), 1000) {
    override fun onFinish() {
        controller.gameOverTimeUp()
    }

    override fun onTick(millisUntilFinished: Long) {
        model.timeLeft--
        model.timeLeftToMove--

        if(model.timeLeftToMove <= 0) {
            controller.timeUp()
        }

        controller.updateTime()
    }
}