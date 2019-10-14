package chip8.sound

import chip8.entity.Chip8Byte

class ConcreteChip8SoundTimer(private val runnableSound: Runnable) : Chip8SoundTimer {
    private var counter: Int = 0

    override fun setTimerCounter(counter: Chip8Byte) {
        this.counter = counter.value
    }

    override fun getTimerCounter(): Chip8Byte {
        return Chip8Byte(counter)
    }

    override fun decrementTimer() {
        if (counter > 0) {
            counter--
        }
        if (counter > 0) {
           runnableSound.run()
        }
    }

    override fun initialize() {
        counter = 0
    }
}