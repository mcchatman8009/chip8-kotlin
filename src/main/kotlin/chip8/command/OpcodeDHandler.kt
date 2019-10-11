package chip8.command

import chip8.cpu.Chip8Cpu
import chip8.entity.Chip8Word
import chip8.video.Chip8VideoDisplayProcessingUnit

/**
 * The Opcode DXXX handles drawing a sprite at the coordinate (VX, VY). Where the sprite has a width of 8 pixels
 * and a height of N pixels.
 *
 * When VF is set to a 1 then the sprite pixels are inverted, meaning that pixels are flipped from set to unset
 */
class OpcodeDHandler(val cpu: Chip8Cpu, val chip8VideoDisplayProcessingUnit: Chip8VideoDisplayProcessingUnit) : OpcodeHandler {
    override fun execute(opcodeWordData: Chip8Word): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}