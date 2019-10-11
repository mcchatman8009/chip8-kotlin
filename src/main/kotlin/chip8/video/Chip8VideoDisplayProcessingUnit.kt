package chip8.video

interface Chip8VideoDisplayProcessingUnit {
    /**
     * Color a given pixel at the x and y coordinate.
     * There are only two colors to choose from in the chip8
     * So when enableColor is true we choose to show the pixel, and
     * when it's false we don't show the pixel.
     */
    fun setPixel(x: Int, y: Int, enableColor: Boolean)

    fun getPixel(x: Int, y: Int): Boolean

    fun clearDisplay()

    companion object {
        const val SCREEN_WIDTH = 64
        const val SCREEN_HEIGHT = 32
    }
}