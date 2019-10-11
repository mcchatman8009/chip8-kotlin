package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.video.VideoDisplayProcessingUnit

class Chip8CommandHandler(private val cpu: Chip8Cpu) {
    private val handlerTable: Array<OpcodeHandler> = Array(16) { Opcode0Handler(cpu) }

    fun setVideoDisplayProcessingUnit(vdp: VideoDisplayProcessingUnit) {
        handlerTable[0xD] = OpcodeDHandler(cpu, vdp)
    }

    init {
        handlerTable[0x0] = Opcode0Handler(cpu)
        handlerTable[0x1] = Opcode1Handler(cpu)
        handlerTable[0x2] = Opcode2Handler(cpu)
        handlerTable[0x3] = Opcode3Handler(cpu)
        handlerTable[0x4] = Opcode4Handler(cpu)
        handlerTable[0x5] = Opcode5Handler(cpu)
        handlerTable[0x6] = Opcode6Handler(cpu)
        handlerTable[0x7] = Opcode7Handler(cpu)
        handlerTable[0x8] = Opcode8Handler(cpu)
        handlerTable[0x9] = Opcode9Handler(cpu)
        handlerTable[0xA] = OpcodeAHandler(cpu)
        handlerTable[0xB] = OpcodeBHandler(cpu)
        handlerTable[0xC] = OpcodeCHandler(cpu)
        handlerTable[0xE] = OpcodeEHandler(cpu)
        handlerTable[0xF] = OpcodeFHandler(cpu)
    }

    fun executeCommandFromOpcodeData(opcodeWordData: Int): Int {
        val handlerCode = (opcodeWordData and 0xF000) shr 12

        val handler = handlerTable[handlerCode]

        return handler.execute(opcodeWordData)
    }
}