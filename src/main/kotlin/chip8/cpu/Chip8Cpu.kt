package chip8.cpu

import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word
import chip8.input.Chip8InputProcessingUnit
import chip8.sound.Chip8SoundTimer
import chip8.timer.Chip8Timer
import chip8.video.Chip8VideoDisplayProcessingUnit

interface Chip8Cpu {
    /**
     * Sets the value of a register
     */
    fun setRegisterWordValue(register: CpuWordRegister, word: Chip8Word)

    /**
     * Gets the register value
     */
    fun getWordRegisterValue(register: CpuWordRegister): Chip8Word

    /**
     * Sets the value of a register
     */
    fun setRegisterByteValue(register: CpuByteRegister, byte: Chip8Byte)

    /**
     * Gets the register value
     */
    fun getByteRegisterValue(register: CpuByteRegister): Chip8Byte

    /**
     * This is useful if you would like to find out one of the Vx register symbols
     */
    fun getRegisterByteSymbolByNumber(registerNumber: Int): CpuByteRegister

    /**
     * Pushes WORD (NNN) value onto the stack
     */
    fun push(word: Chip8Word)

    /**
     * Pops a Word off the stack
     */
    fun pop(): Chip8Word

    /**
     * Executes a single instruction and returns the number of cpu cycles taken
     */
    fun executeSingleInstruction(): Int

    /**
     * Executes cpu instructions until the numberOfCycles has been completed
     */
    fun executeForNumberOfCycles(numberOfCycles: Int)

    fun writeByteToMemory(address: Chip8Word, byte: Chip8Byte)

    fun writeWordToMemory(address: Chip8Word, byte: Chip8Word)

    fun readByteFromMemory(address: Chip8Word): Chip8Byte

    fun readWordFromMemory(address: Chip8Word): Chip8Word

    fun initialize()

    /**
     * Connect the CPU to the Video  Display Processing Unit
     */
    fun connectToVideoDisplayProcessingUnit(chip8VideoDisplayProcessingUnit: Chip8VideoDisplayProcessingUnit)

    fun connectToInputProcessingUnit(inputProcessingUnit: Chip8InputProcessingUnit)

    fun connectToSoundTimer(timer: Chip8SoundTimer)

    fun connectToTimer(timer: Chip8Timer)
}