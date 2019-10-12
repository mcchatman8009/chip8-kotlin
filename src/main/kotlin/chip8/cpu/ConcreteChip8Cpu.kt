package chip8.cpu

import chip8.command.Chip8CommandHandler
import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word
import chip8.util.Chip8Util
import chip8.video.Chip8VideoDisplayProcessingUnit
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
    private val memory = Array(0x1000) { 0x0 }
    private val commandHandler = Chip8CommandHandler(this)

    override fun connectToVideoDisplayProcessingUnit(chip8VideoDisplayProcessingUnit: Chip8VideoDisplayProcessingUnit) {
        commandHandler.setVideoDisplayProcessingUnit(chip8VideoDisplayProcessingUnit)
    }

    override fun setRegisterByteValue(register: CpuByteRegister, byte: Chip8Byte) {
        registers[register.registerNumber] = byte.value
    }

    override fun getByteRegisterValue(register: CpuByteRegister): Chip8Byte {
        return Chip8Byte(registers[register.registerNumber])
    }

    override fun getRegisterByteSymbolByNumber(registerNumber: Int): CpuByteRegister {
        return CpuByteRegister.SYMBOL_ARRAY[registerNumber]
    }

    override fun push(word: Chip8Word) {
        stack.push(word.value)
    }

    override fun pop(): Chip8Word =
        Chip8Word(stack.pop())

    override fun setRegisterWordValue(register: CpuWordRegister, word: Chip8Word) {
        when (register) {
            CpuWordRegister.I -> {
                addressRegister = word.value
            }
            CpuWordRegister.PC -> {
                programCounter = word.value
            }
        }
    }

    override fun getWordRegisterValue(register: CpuWordRegister): Chip8Word {
        return when (register) {
            CpuWordRegister.I -> Chip8Word(addressRegister)
            CpuWordRegister.PC -> Chip8Word(programCounter)
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

    override fun writeByteToMemory(address: Chip8Word, byte: Chip8Byte) {
        memory[address.value] = byte.value
    }

    override fun readByteFromMemory(address: Chip8Word): Chip8Byte =
        Chip8Byte(memory[address.value])

    private fun getOpcodeWordData(): Chip8Word {
        val pc = programCounter

        //
        // Read the opco bytes as big endian
        //
        val highByte = memory[pc]
        val lowByte = memory[pc + 1]

        return Chip8Util.toWord(highByte = highByte, lowByte = lowByte)
    }

    override fun writeWordToMemory(address: Chip8Word, byte: Chip8Word) {
        writeByteToMemory(address, byte.highOrderByte)
        writeByteToMemory(address.addTo(1), byte.lowOrderOrderByte)
    }

    override fun readWordFromMemory(address: Chip8Word): Chip8Word {
        val pc = programCounter

        //
        // Read the opco bytes as big endian
        //
        val highByte = memory[pc]
        val lowByte = memory[pc + 1]

        return Chip8Util.toWord(highByte = highByte, lowByte = lowByte)
    }
}