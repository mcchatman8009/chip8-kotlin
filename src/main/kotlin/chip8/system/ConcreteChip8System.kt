package chip8.system

import chip8.cpu.ConcreteChip8Cpu
import chip8.input.ConcreteChip8InputProcessingUnit
import chip8.rom.ConcreteChip8RomLoader
import chip8.sound.ConcreteChip8SoundTimer
import chip8.timer.ConcreteChip8Timer
import chip8.video.ConcreteChip8VideoDisplayProcessingUnit

class ConcreteChip8System : Chip8System {
    private val cpu = ConcreteChip8Cpu()
    private val inputProcessingUnit = ConcreteChip8InputProcessingUnit(cpu)
    private val videoDisplayProcessingUnit = ConcreteChip8VideoDisplayProcessingUnit()
    private val romLoader = ConcreteChip8RomLoader(cpu)
    private val soundTimer = ConcreteChip8SoundTimer(Runnable {})
    private val timer = ConcreteChip8Timer()
    private var romLoaded = true

    override fun initialize() {
        romLoaded = false
        videoDisplayProcessingUnit.clearDisplay()
        timer.initialize()
        soundTimer.initialize()
        inputProcessingUnit.initialize()

        cpu.connectToInputProcessingUnit(inputProcessingUnit)
        cpu.connectToSoundTimer(soundTimer)
        cpu.connectToTimer(timer)
        cpu.connectToVideoDisplayProcessingUnit(videoDisplayProcessingUnit)
        cpu.initialize()
    }

    override fun loadRom(romBytes: ByteArray) {
        cpu.initialize()
        romLoader.loadRom(romBytes)
        romLoaded = true
    }

    override fun runForNumberOfCpuCycles(cycles: Int) {
        cpu.executeForNumberOfCycles(cycles)
    }

    override fun getPixelAt(x: Int, y: Int): Boolean =
        videoDisplayProcessingUnit.getPixel(x, y)

    override fun setRenderCallback(renderFunction: (x: Int, y: Int, enableColor: Boolean) -> Unit) {
        videoDisplayProcessingUnit.setRenderCallback(renderFunction)
    }
}