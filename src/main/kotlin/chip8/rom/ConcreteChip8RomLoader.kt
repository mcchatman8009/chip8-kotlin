package chip8.rom

import chip8.cpu.Chip8Cpu
import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word


class ConcreteChip8RomLoader(private val cpu: Chip8Cpu) : Chip8RomLoader {
    override fun loadRom(romBytes: ByteArray) {
        var i = 0

        while (i < romBytes.size) {
            val data = Chip8Byte(romBytes[i].toInt())

            cpu.writeByteToMemory(Chip8Word(Chip8RomLoader.START_ADDRESS + i), data)
            i++
        }
    }
}