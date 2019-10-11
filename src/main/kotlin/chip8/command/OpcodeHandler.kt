package chip8.command

import chip8.entity.Chip8Word

interface OpcodeHandler {
    fun execute(opcodeWordData: Chip8Word): Int
}