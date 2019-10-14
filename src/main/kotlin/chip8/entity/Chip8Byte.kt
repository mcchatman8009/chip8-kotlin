package chip8.entity

inline class Chip8Byte(private val originalByte: Int) {
    val value: Int
        get() = originalByte and 0xFF

    val highOrderNibble: Int
        get() = (((originalByte and 0xFF) shr 4) and 0xF)

    val lowOrderNibble: Int
        get() = ((originalByte and 0xFF) and 0xF)

    fun getBit(bit: Int): Boolean {
        return (originalByte and (1 shl bit)) > 0
    }

    fun addTo(value: Int): Chip8Byte = Chip8Byte(originalByte + value)

    fun addTo(byte: Chip8Byte): Chip8Byte = Chip8Byte(value + byte.value)

    fun subtract(byte: Chip8Byte): Chip8Byte = Chip8Byte(value - byte.value)

    fun xor(byte: Chip8Byte): Chip8Byte = Chip8Byte(value xor byte.value)

    fun or(byte: Chip8Byte): Chip8Byte = Chip8Byte(value or byte.value)

    fun and(byte: Chip8Byte): Chip8Byte = Chip8Byte(value and byte.value)

    fun shiftRight(shiftBits: Int): Chip8Byte = Chip8Byte(value shr shiftBits)

    fun shiftLeft(shiftBits: Int): Chip8Byte = Chip8Byte(value shl shiftBits)
}