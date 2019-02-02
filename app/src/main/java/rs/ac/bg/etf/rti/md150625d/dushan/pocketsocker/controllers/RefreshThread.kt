package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.controllers

import rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.viewModels.GameViewModel

class RefreshThread(var model: GameViewModel, var controller: GameController)
    : Thread() {

    private var isRunning = true
    private var lastTime: Long? = null

    override fun run() {
        super.run()
        lastTime = System.currentTimeMillis()
        while (!interrupted()) {
            val timePassedinSeconds:Double =
                ((System.currentTimeMillis() - lastTime!!)).toDouble()
            if (isRunning) {
                handleCollisions()
                // update positions for all figures
                for (figure in model.figures) {
                    figure.x =
                        (figure.x + figure.speedX * timePassedinSeconds/100).toInt()
                    figure.y =
                        (figure.y + figure.speedY * timePassedinSeconds/100).toInt()
                }

                controller.refreshGameView()
            }
            lastTime = System.currentTimeMillis()
            Thread.sleep(5)
        }
    }

    private fun handleCollisions() {
        for ((i, figure) in model.figures.withIndex()) {
            // border collisions
            if (figure.bounds.contains(0, figure.y)
                || figure.bounds.contains(model.canvasWidth, figure.y)) {
                figure.speedX = -figure.speedX
            }

            if (figure.bounds.contains(figure.x, 0)
                || figure.bounds.contains(figure.x, model.canvasHeight)) {
                figure.speedY = -figure.speedY
            }

            // football player collisions
            for ((j, figure2) in model.figures.withIndex()) {
                if (i < j && figure.bounds.intersect(figure2.bounds)) {
                    // change directions(speed signs) only if balls are moving towards each other

                    // calculate distance
                    var currentDistance: Double =
                        Math.sqrt(Math.pow((figure.x - figure2.x).toDouble(), 2.0)
                                + Math.pow((figure.y - figure2.y).toDouble(), 2.0))

                    // apply speeds(simulated time = 0.1s)
                    val simulatedTime = 0.3
                    val newX1 = (figure.x + figure.speedX * simulatedTime).toInt()
                    val newY1 = (figure.y + figure.speedY * simulatedTime).toInt()

                    val newX2 = (figure2.x + figure2.speedX * simulatedTime).toInt()
                    val newY2 = (figure2.y + figure2.speedY * simulatedTime).toInt()

                    // calculate new distance
                    var newDistance: Double =
                        Math.sqrt(Math.pow((newX1 - newX2).toDouble(), 2.0)
                                + Math.pow((newY1 - newY2).toDouble(), 2.0))

                    val elasticityFactor = 0.9

                    if (newDistance < currentDistance) {
                        figure.speedX = (-figure.speedX * elasticityFactor
                                - figure2.speedX * elasticityFactor).toInt()
                        figure.speedY = (-figure.speedY * elasticityFactor
                                - figure2.speedY * elasticityFactor).toInt()

                        figure2.speedX = (-figure2.speedX * elasticityFactor
                                - figure.speedX * elasticityFactor).toInt()
                        figure2.speedY = (-figure2.speedY * elasticityFactor
                                - figure.speedY * elasticityFactor).toInt()
                    }


                }
            }
        }
    }

    fun pauseGame() {
        isRunning = false
    }

    fun resumeGame(){
        isRunning = true
    }
}