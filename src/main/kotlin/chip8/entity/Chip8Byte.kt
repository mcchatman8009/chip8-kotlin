package chip8.entity

inline class Chip8Byte(private val originalByte: Int) {
    val value: Int
        get() = originalByte and 0xFF

    val highOrderNibble: Int
        get() = (((originalByte and 0xFF) shr 4) and 0xF)

    val lowOrderNibble: Int
        get() = ((originalByte and 0xFF) and 0xF)
}