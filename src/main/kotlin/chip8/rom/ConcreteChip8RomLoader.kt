package chip8.rom

import java.io.File

class ConcreteChip8RomLoader : Chip8RomLoader {
    override fun loadRom(filePath: String): ByteArray {
        val file = File(filePath)
        if (file.exists()) {
            return file.readBytes()
        }

        throw IllegalAccessError("File ${filePath} Not Found")
    }
}