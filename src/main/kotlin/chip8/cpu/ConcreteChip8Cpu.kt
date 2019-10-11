package chip8.cpu

import chip8.command.Chip8CommandHandler
import chip8.util.Chip8Util
import chip8.video.VideoDisplayProcessingUnit
import java.util.*

class ConcreteChip8Cpu : Chip8Cpu {

    // The 8 bit registers V0-VF
    private val registers: Array<Int> = Array(16) { 0x0 }
    // The 16 bit program counter
    private var programCounter = 0x0
    // The 16 bit address register
    private var addressRegister = 0x0
    // The 16 bit stack
    private val stack = ArrayDeque<Int>()
    private val memory = Array(0xFFF) { 0x0 }
    private val commandHandler = Chip8CommandHandler(this)

    override fun connectToVideoDisplayProcessingUnit(videoDisplayProcessingUnit: VideoDisplayProcessingUnit) {
        commandHandler.setVideoDisplayProcessingUnit(videoDisplayProcessingUnit)
    }

    override fun setRegisterByteValue(register: CpuByteRegister, value: Int) {
        registers[register.registerNumber] = Chip8Util.toByte(value)
    }

    override fun getByteRegisterValue(register: CpuByteRegister): Int {
        return Chip8Util.toByte(registers[register.registerNumber])
    }

    override fun getRegisterByteSymbolByNumber(registerNumber: Int): CpuByteRegister {
        return CpuByteRegister.SYMBOL_ARRAY[registerNumber]
    }

    override fun push(word: Int) {
        stack.push(programCounter)
    }

    override fun pop(): Int =
        stack.pop()

    override fun setRegisterWordValue(register: CpuWordRegister, value: Int) {
        when (register) {
            CpuWordRegister.I -> {
                addressRegister = Chip8Util.toWord(value)
            }
            CpuWordRegister.PC -> {
                programCounter = Chip8Util.toWord(value)
            }
        }
    }

    override fun getWordRegisterValue(register: CpuWordRegister): Int {
        return when (register) {
            CpuWordRegister.I -> addressRegister
            CpuWordRegister.PC -> programCounter
        }
    }

    override fun executeSingleInstruction(): Int {
        val opcodeWordData = getOpcodeWordData()
        return commandHandler.executeCommandFromOpcodeData(opcodeWordData = opcodeWordData)

    }

    override fun executeForNumberOfCycles(numberOfCycles: Int) {
        var cycles = 0

        while (cycles < numberOfCycles) {
            cycles += executeSingleInstruction()
        }
    }

    override fun writeByteToMemory(address: Int, byte: Int) {
        memory[address] = Chip8Util.toByte(byte)
    }

    override fun readByteFromMemory(address: Int): Int =
        Chip8Util.toByte(memory[address])

    private fun getOpcodeWordData(): Int {
        val pc = programCounter

        //
        // Read the opco bytes as big endian
        //
        val highByte = memory[pc]
        val lowByte = memory[pc + 1]

        return Chip8Util.toWord(highByte = highByte, lowByte = lowByte)
    }
}