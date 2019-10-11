package chip8.util

class Chip8Util {

    companion object {
        fun toByte(data: Int): Int = data and 0xFF

        fun toWord(data: Int): Int = data and 0xFF_FF

        fun toWord(highByte: Int, lowByte: Int): Int {
            return ((highByte shl 8) and 0xFF00) or (lowByte and 0xFF)
        }
    }
}