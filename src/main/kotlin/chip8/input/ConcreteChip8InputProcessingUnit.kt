package chip8.input

import chip8.cpu.Chip8Cpu
import chip8.cpu.CpuByteRegister
import chip8.entity.Chip8Byte

class ConcreteChip8InputProcessingUnit(private val cpu: Chip8Cpu) : Chip8InputProcessingUnit {
    private val buttonState = Array(16) { false }
    private var waitForButtonPress = false
    private var registerForKey: CpuByteRegister? = null

    override fun initialize() {
        var i = 0

        while (i < buttonState.size) {
            buttonState[i] = false
            i++
        }
    }

    override fun isCurrentKeyPressed(key: Int): Boolean {
        return buttonState[key]
    }

    override fun enableKeyPressForKey(key: Int) {
        if (waitForKeyPress() && registerForKey != null) {
            cpu.setRegisterByteValue(registerForKey!!, Chip8Byte(key))
        }

        buttonState[key] = true
    }

    override fun disableKeyPress(key: Int) {
        buttonState[key] = false
    }

    override fun triggerWaitForKeyPressAndStoreKeyInRegister(byteRegister: CpuByteRegister) {
        waitForButtonPress = true
        this.registerForKey = byteRegister
    }

    override fun waitForKeyPress(): Boolean {
        return waitForButtonPress
    }
}