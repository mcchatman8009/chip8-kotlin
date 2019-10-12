package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.cpu.CpuWordRegister
import chip8.entity.Chip8Word
import chip8.video.Chip8VideoDisplayProcessingUnit

class Opcode0Handler(
    private val cpu: Chip8Cpu,
    private val videoDisplayProcessingUnit: Chip8VideoDisplayProcessingUnit
) :
    OpcodeHandler {

    override fun execute(opcodeWordData: Chip8Word): Int {
        val internalOpCode = opcodeWordData.lowOrderOrderByte
        val lowNibble = internalOpCode.lowOrderNibble

        if (lowNibble == 0) {
            // Handle 00E0	Display	disp_clear()	Clears the screen.
            videoDisplayProcessingUnit.clearDisplay()
        } else if (lowNibble == 0xE) {
            // 00EE	Flow	return;	Returns from a subroutine.
            val address = cpu.pop()
            cpu.setRegisterWordValue(CpuWordRegister.PC, address)
        } else {
            // 0NNN	Call	Calls at address NNN
            cpu.push(cpu.getWordRegisterValue(CpuWordRegister.PC))
            cpu.setRegisterWordValue(CpuWordRegister.PC, opcodeWordData)

        }

        return 1
    }
}