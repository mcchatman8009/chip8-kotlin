package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.cpu.CpuByteRegister
import chip8.cpu.CpuWordRegister
import chip8.entity.Chip8Word
import chip8.input.Chip8InputProcessingUnit
import chip8.sound.Chip8SoundTimer
import chip8.timer.Chip8Timer
import chip8.video.Chip8VideoDisplayProcessingUnit

class Chip8CommandHandler(private val cpu: Chip8Cpu) {
    private val handlerTable: Array<OpcodeHandler> = Array(16) { Opcode1Handler(cpu) }
    private var vdp: Chip8VideoDisplayProcessingUnit? = null
    private var soundTimer: Chip8SoundTimer? = null
    private var timer: Chip8Timer? = null
    private var inputProcessingUnit: Chip8InputProcessingUnit? = null

    fun setInputProcessingUnit(inputProcessingUnit: Chip8InputProcessingUnit) {
        this.inputProcessingUnit = inputProcessingUnit
    }

    fun setSoundTimer(timer: Chip8SoundTimer) {
        this.soundTimer = timer
    }

    fun setTimer(timer: Chip8Timer) {
        this.timer = timer
    }

    fun setVideoDisplayProcessingUnit(vdp: Chip8VideoDisplayProcessingUnit) {
        this.vdp = vdp
    }

    fun initialize() {
        handlerTable[0x0] = Opcode0Handler(cpu, vdp!!)
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
        handlerTable[0xD] = OpcodeDHandler(cpu, vdp!!)
        handlerTable[0xE] = OpcodeEHandler(cpu, inputProcessingUnit!!)
        handlerTable[0xF] =
            OpcodeFHandler(
                cpu = cpu,
                inputProcessingUnit = inputProcessingUnit!!,
                timer = timer!!,
                soundTimer = soundTimer!!
            )

    }

    fun executeCommandFromOpcodeData(opcodeWordData: Chip8Word): Int {
        if (inputProcessingUnit!!.waitForKeyPress()) {
            return 1
        }

        //
        // Here we're grabbing the most significant nibble to figure out the opcode
        // 0xF0000
        //
        timer!!.decrementTimer()
        soundTimer!!.decrementTimer()

        val handlerCode = opcodeWordData.highOrderByte.highOrderNibble

        val handler = handlerTable[handlerCode]

        if (opcodeWordData.highOrderByte.highOrderNibble == 0x8) {
            debugOpcode(opcodeWordData)
        }

        return handler.execute(opcodeWordData)
    }

    private fun debugOpcode(opcodeWordData: Chip8Word) {
        val pc = cpu.getWordRegisterValue(CpuWordRegister.PC).value.toString(16)
        val addressRegister = cpu.getWordRegisterValue(CpuWordRegister.I).value.toString(16)
        val opcoCode = opcodeWordData.value.toString(16)

        val v0 = cpu.getByteRegisterValue(CpuByteRegister.V0).value.toString(16)
        val v1 = cpu.getByteRegisterValue(CpuByteRegister.V1).value.toString(16)
        val v2 = cpu.getByteRegisterValue(CpuByteRegister.V2).value.toString(16)
        val v3 = cpu.getByteRegisterValue(CpuByteRegister.V3).value.toString(16)
        val v4 = cpu.getByteRegisterValue(CpuByteRegister.V4).value.toString(16)
        val v5 = cpu.getByteRegisterValue(CpuByteRegister.V5).value.toString(16)
        val v6 = cpu.getByteRegisterValue(CpuByteRegister.V6).value.toString(16)
        val vd = cpu.getByteRegisterValue(CpuByteRegister.VD).value.toString(16)
        val ve = cpu.getByteRegisterValue(CpuByteRegister.VE).value.toString(16)

        println("OPCODE[$opcoCode] I[$addressRegister] PC[$pc] v0[$v0] v1[$v1] v2[$v2] v3[$v3] v4[$v4] v5[$v5] v6[$v6] vd[$vd] ve[$ve]")

    }
}