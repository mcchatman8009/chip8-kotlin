package chip8.application

import chip8.application.view.chip8.Chip8View
import tornadofx.App
import tornadofx.launch

class Chip8App : App(Chip8View::class)

fun main(args: Array<String>) {
    launch<Chip8App>(args)
}

