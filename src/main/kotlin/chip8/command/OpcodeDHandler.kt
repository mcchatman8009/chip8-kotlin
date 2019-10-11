package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.video.VideoDisplayProcessingUnit

class OpcodeDHandler(val cpu: Chip8Cpu, val videoDisplayProcessingUnit: VideoDisplayProcessingUnit) : OpcodeHandler {
    override fun execute(opcodeWordData: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}