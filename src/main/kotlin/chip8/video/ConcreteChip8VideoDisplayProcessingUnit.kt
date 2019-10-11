package chip8.video

class ConcreteChip8VideoDisplayProcessingUnit : Chip8VideoDisplayProcessingUnit {
    private val pixelData: Array<Array<Boolean>> = Array(Chip8VideoDisplayProcessingUnit.SCREEN_WIDTH) {
        Array(Chip8VideoDisplayProcessingUnit.SCREEN_HEIGHT) { false }
    }

    override fun setPixel(x: Int, y: Int, enableColor: Boolean) {
        pixelData[x][y] = enableColor
    }

    override fun getPixel(x: Int, y: Int): Boolean {
        return pixelData[x][y]
    }

    override fun clearDisplay() {
        var x = 0
        while (x < Chip8VideoDisplayProcessingUnit.SCREEN_WIDTH) {
            var y = 0
            while (y < Chip8VideoDisplayProcessingUnit.SCREEN_HEIGHT) {
                setPixel(x, y, false)
                y++
            }
            x++
        }
    }
}