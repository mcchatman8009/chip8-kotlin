package chip8.timer

import chip8.entity.Chip8Byte

class ConcreteChip8Timer : Chip8Timer {
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
    }

    override fun initialize() {
        counter = 0
    }
}