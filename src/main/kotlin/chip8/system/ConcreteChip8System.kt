package chip8.system

import chip8.cpu.Chip8Cpu
import chip8.cpu.CpuWordRegister
import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word

class ConcreteChip8System(private val cpu: Chip8Cpu) : Chip8System {

    override fun initialize() {
        cpu.setRegisterWordValue(CpuWordRegister.PC, Chip8Word(0x200))

        (0 until 16).forEach {
            val symbol = cpu.getRegisterByteSymbolByNumber(it)
            cpu.setRegisterByteValue(symbol, Chip8Byte(0))
        }

        (0 until 0x1_00_00).forEach {
            cpu.writeByteToMemory(Chip8Word(it), Chip8Byte(0))
        }
    }

    override fun runSingleInstruction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadRom(romBytes: ByteArray) {
        var address = 0x200
        var i = 0

        while (i < romBytes.size) {
            val addressWord = Chip8Word(address)
            val byte = Chip8Byte(romBytes[i].toInt())

            cpu.writeByteToMemory(addressWord, byte)
            i++
            address++
        }
    }

}