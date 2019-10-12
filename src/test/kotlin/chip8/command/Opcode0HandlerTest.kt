package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.cpu.ConcreteChip8Cpu
import chip8.cpu.CpuWordRegister
import chip8.entity.Chip8Word
import chip8.video.ConcreteChip8VideoDisplayProcessingUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class Opcode0HandlerTest {

    @Test
    @DisplayName("0NNN Call  Calls RCA 1802 program at address NNN. Not necessary for most ROMs.")
    fun testCallOpcoCode() {
        val cpu = ConcreteChip8Cpu()
        cpu.connectToVideoDisplayProcessingUnit(ConcreteChip8VideoDisplayProcessingUnit())
        cpu.setRegisterWordValue(CpuWordRegister.PC, Chip8Word(0x0000))
        cpu.connectToVideoDisplayProcessingUnit(ConcreteChip8VideoDisplayProcessingUnit())

        call(cpu, 0x0444)

        val pc = cpu.getWordRegisterValue(CpuWordRegister.PC)

        Assertions.assertEquals(0x0444, pc.value)
    }

    @Test
    @DisplayName("00EE Flow return; Returns from a subroutine.")
    fun testReturn() {
        val cpu = ConcreteChip8Cpu()
        val vdp = ConcreteChip8VideoDisplayProcessingUnit()

        cpu.connectToVideoDisplayProcessingUnit(vdp)

        cpu.setRegisterWordValue(CpuWordRegister.PC, Chip8Word(0x0000))
        val expectedAddress = 0x888


        call(cpu, expectedAddress)

        cpu.writeWordToMemory(Chip8Word(0x2), Chip8Word(0x00EE))
        cpu.executeSingleInstruction()

        val pc = cpu.getWordRegisterValue(CpuWordRegister.PC)
        Assertions.assertEquals(expectedAddress, pc.value)
    }

    private fun call(cpu: Chip8Cpu, address: Int) {
        cpu.writeWordToMemory(Chip8Word(0x0), Chip8Word(address))
        cpu.executeSingleInstruction()
    }
}