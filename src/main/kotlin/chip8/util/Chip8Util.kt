package chip8.util

import chip8.entity.Chip8Word

class Chip8Util {

    companion object {
        fun toWord(highByte: Int, lowByte: Int): Chip8Word =
            Chip8Word(((highByte shl 8) and 0xFF00) or (lowByte and 0xFF))
    }
}