package chip8.application.view.chip8

import chip8.application.timer.FpsTimer
import chip8.application.view.video.PixelView
import chip8.system.ConcreteChip8System
import chip8.video.Chip8VideoDisplayProcessingUnit
import javafx.application.Platform
import tornadofx.*
import java.io.File

class Chip8View : View() {
    private val chip8System = ConcreteChip8System()
    private val fpsTimer = FpsTimer(20)
    private var pixelView: PixelView? = null

    override val root = borderpane {
        center {
            add<PixelView> {
                pixelView = this
                this.initialize(
                    Chip8VideoDisplayProcessingUnit.SCREEN_WIDTH,
                    Chip8VideoDisplayProcessingUnit.SCREEN_HEIGHT
                )
            }
        }
    }

    override fun onDock() {
        configureRendering()

        chip8System.initialize()

        adjustStage()
        loadRom()
        startFpsTimer()
    }

    private fun configureRendering() {
        chip8System.setRenderCallback { x, y, enableColor ->
            if (enableColor) {
                pixelView!!.writePixel(x, y, 255, 255, 255)
            } else {
                pixelView!!.writePixel(x, y, 0, 0, 0)
            }
        }
    }

    private fun adjustStage() {
        primaryStage.setOnShown {
            primaryStage.widthProperty().addListener { _, _, newVal ->
                pixelView!!.setWidth(newVal.toInt())
            }

            primaryStage.heightProperty().addListener { _, _, newVal ->
                pixelView!!.setHeight(newVal.toInt())
            }

            primaryStage.width = 400.0
            primaryStage.height = 300.0

        }
    }

    private fun loadRom() {
        val file = File("/Users/mchatman/Downloads/TETRIS.ch8")
//        val file = File("/Users/mchatman/Downloads/Maze.ch8")
//        val file = File("/Users/mchatman/Downloads/Particle.ch8")
//        val file = File("/Users/mchatman/Downloads/TICTAC.ch8")
//        val file = File("/Users/mchatman/Downloads/BREAKOUT.ch8")
//        val file = File("/Users/mchatman/Downloads/c8games/INVADERS")
        chip8System.loadRom(file.readBytes())

    }

    private fun startFpsTimer() {
        fpsTimer.startAndInvokeLoopWithRunnable {
            try {

                chip8System.runForNumberOfCpuCycles(100)
            } catch (e: Exception) {
               e.printStackTrace()
            }

            Platform.runLater {
                pixelView?.commitBufferChanges()
            }
        }
    }
}
