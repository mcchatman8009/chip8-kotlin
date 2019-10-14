package chip8.cpu

import chip8.command.Chip8CommandHandler
import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word
import chip8.input.Chip8InputProcessingUnit
import chip8.sound.Chip8SoundTimer
import chip8.timer.Chip8Timer
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
    private val memory = Array(0x1_00_00) { 0x0 }
    private val commandHandler = Chip8CommandHandler(this)

    override fun initialize() {
        commandHandler.initialize()
        setRegisterWordValue(CpuWordRegister.PC, Chip8Word(0x200))
        setRegisterWordValue(CpuWordRegister.I, Chip8Word(0x0))


        (0 until 16).forEach {
            val symbol = getRegisterByteSymbolByNumber(it)
            setRegisterByteValue(symbol, Chip8Byte(0))
        }

        (0 until 0xFF_FF).forEach {
            writeByteToMemory(Chip8Word(it), Chip8Byte(0))
        }

        (0 until Chip8VideoDisplayProcessingUnit.CHARACTER_SPRITES.size).forEachIndexed { index, data ->
            writeByteToMemory(Chip8Word(index), Chip8Byte(data))
        }
    }

    override fun connectToInputProcessingUnit(inputProcessingUnit: Chip8InputProcessingUnit) {
        commandHandler.setInputProcessingUnit(inputProcessingUnit)
    }

    override fun connectToSoundTimer(timer: Chip8SoundTimer) {
        commandHandler.setSoundTimer(timer)
    }

    override fun connectToTimer(timer: Chip8Timer) {
        commandHandler.setTimer(timer)
    }

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

        return
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

        programCounter += 2

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