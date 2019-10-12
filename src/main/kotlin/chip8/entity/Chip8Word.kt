package chip8.entity

inline class Chip8Word(private val originalWord: Int) {
    val value: Int
        get() = originalWord and 0xFF_FF

    val highOrderByte: Chip8Byte
        get() = Chip8Byte((originalWord and 0xFF_00) shr 8)

    val lowOrderOrderByte: Chip8Byte
        get() = Chip8Byte(originalWord and 0xFF)

    fun addTo(value: Int): Chip8Word = Chip8Word(originalWord + value)
}