package chip8.video

interface VideoDisplayProcessingUnit {
    /**
     * Color a given pixel at the x and y coordinate.
     * There are only two colors to choose from in the chip8
     * So when color is true we choose to show the pixel, and
     * when it's false we don't show the pixel.
     */
    fun setPixel(x: Int, y: Int, color: Boolean)
}