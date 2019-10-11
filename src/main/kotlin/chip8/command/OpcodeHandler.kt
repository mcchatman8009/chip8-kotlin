package chip8.command

interface OpcodeHandler {
    fun execute(opcodeWordData: Int): Int
}