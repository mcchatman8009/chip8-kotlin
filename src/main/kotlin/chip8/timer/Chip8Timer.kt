package chip8.timer

import chip8.entity.Chip8Byte

interface Chip8Timer {
    fun setTimerCounter(counter: Chip8Byte)
    fun getTimerCounter(): Chip8Byte
    fun decrementTimer()
}