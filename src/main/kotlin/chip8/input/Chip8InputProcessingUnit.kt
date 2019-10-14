package chip8.input

import chip8.cpu.CpuByteRegister

interface Chip8InputProcessingUnit {
    fun initialize()

    fun isCurrentKeyPressed(key: Int): Boolean

    fun enableKeyPressForKey(key: Int)

    fun disableKeyPress(key: Int)

    fun triggerWaitForKeyPressAndStoreKeyInRegister(byteRegister: CpuByteRegister)

    fun waitForKeyPress() : Boolean
}