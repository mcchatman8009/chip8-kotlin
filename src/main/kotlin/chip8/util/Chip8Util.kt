package chip8.util

import chip8.cpu.Chip8Cpu
import chip8.entity.Chip8Byte
import chip8.entity.Chip8Word

class Chip8Util {

    companion object {
        fun getRegisterByteValueFromNumber(cpu: Chip8Cpu, registerNumber: Int): Chip8Byte {
            val symbol = cpu.getRegisterByteSymbolByNumber(registerNumber)
            return cpu.getByteRegisterValue(symbol)
        }

        fun setRegisterByteValueFromNumber(cpu: Chip8Cpu, registerNumber: Int, byte: Chip8Byte) {
            val symbol = cpu.getRegisterByteSymbolByNumber(registerNumber)
            cpu.setRegisterByteValue(symbol, byte)
        }

        fun toWord(highByte: Int, lowByte: Int): Chip8Word =
            Chip8Word(((highByte shl 8) and 0xFF00) or (lowByte and 0xFF))
    }
}