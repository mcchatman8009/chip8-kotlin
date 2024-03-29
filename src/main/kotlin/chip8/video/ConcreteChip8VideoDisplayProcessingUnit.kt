package chip8.video


class ConcreteChip8VideoDisplayProcessingUnit : Chip8VideoDisplayProcessingUnit {
    private var renderFunction: (x: Int, y: Int, enableColor: Boolean) -> Unit =
        { x: Int, y: Int, enableColor: Boolean -> }

    override fun setRenderCallback(renderFunction: (x: Int, y: Int, enableColor: Boolean) -> Unit) {
        this.renderFunction = renderFunction
    }

    private val pixelData: Array<Array<Boolean>> = Array(Chip8VideoDisplayProcessingUnit.SCREEN_WIDTH) {
        Array(Chip8VideoDisplayProcessingUnit.SCREEN_HEIGHT) { true }
    }

    override fun setPixel(x: Int, y: Int, enableColor: Boolean) {
        pixelData[x][y] = enableColor
        renderFunction(x, y, enableColor)
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