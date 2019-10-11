package chip8.cpu

import chip8.video.VideoDisplayProcessingUnit

interface Chip8Cpu {
    /**
     * Sets the value of a register
     */
    fun setRegisterWordValue(register: CpuWordRegister, value: Int)

    /**
     * Gets the register value
     */
    fun getWordRegisterValue(register: CpuWordRegister): Int

    /**
     * Sets the value of a register
     */
    fun setRegisterByteValue(register: CpuByteRegister, value: Int)

    /**
     * Gets the register value
     */
    fun getByteRegisterValue(register: CpuByteRegister): Int

    /**
     * This is useful if you would like to find out one of the Vx register symbols
     */
    fun getRegisterByteSymbolByNumber(registerNumber: Int): CpuByteRegister

    /**
     * Pushes WORD (NNN) value onto the stack
     */
    fun push(word: Int)

    /**
     * Pops a Word off the stack
     */
    fun pop(): Int

    /**
     * Executes a single instruction and returns the number of cpu cycles taken
     */
    fun executeSingleInstruction(): Int

    /**
     * Executes cpu instructions until the numberOfCycles has been completed
     */
    fun executeForNumberOfCycles(numberOfCycles: Int)

    fun writeByteToMemory(address: Int, byte: Int)

    fun readByteFromMemory(address: Int): Int

    /**
     * Connect the CPU to the Video  Display Processing Unit
     */
    fun connectToVideoDisplayProcessingUnit(videoDisplayProcessingUnit: VideoDisplayProcessingUnit)
}