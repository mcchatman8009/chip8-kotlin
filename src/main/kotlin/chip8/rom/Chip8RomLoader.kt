package chip8.rom

interface Chip8RomLoader {
    fun loadRom(romBytes: ByteArray)
    companion object {
       const val START_ADDRESS = 0x200
    }
}