package chip8.system

interface Chip8System {
    fun loadRom(romBytes: ByteArray)
    fun initialize()
    fun runSingleInstruction()
}