package chip8.system

interface Chip8System {
    fun loadRom(romBytes: ByteArray)

    fun initialize()

    fun runForNumberOfCpuCycles(cycles: Int)

    fun getPixelAt(x: Int, y: Int): Boolean

    fun setRenderCallback(renderFunction: (x: Int, y: Int, enableColor: Boolean) -> Unit)
}