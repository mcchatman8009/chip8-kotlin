package chip8.rom

interface Chip8RomLoader {
    fun loadRom(filePath: String): ByteArray
}