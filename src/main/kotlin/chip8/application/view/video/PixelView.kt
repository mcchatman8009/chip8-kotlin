package chip8.application.view.video

import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.image.WritablePixelFormat
import tornadofx.*
import java.nio.IntBuffer

class PixelView : Fragment() {
    private var baseWidth: Int = 0
    private var baseHeight: Int = 0
    private var image: WritableImage? = null
    private var imageView: ImageView? = null
    private var pixelBuffer: IntBuffer? = null
    private var writablePixelFormat = WritablePixelFormat.getIntArgbInstance()


    override val root = stackpane {}

    fun initialize(baseWidth: Int, baseHeight: Int, isPreserveRatio: Boolean = true) {
        this.baseWidth = baseWidth
        this.baseHeight = baseHeight

        pixelBuffer = IntBuffer.allocate(baseWidth * baseHeight)

        image = WritableImage(baseWidth, baseHeight)
        imageView = ImageView(image)

        imageView?.isPreserveRatio = isPreserveRatio
        imageView?.viewport
        this.add(imageView!!)
    }

    fun setHeight(height: Int) {
        imageView?.fitHeight = height.toDouble()
    }

    fun setWidth(width: Int) {
        imageView?.fitWidth = width.toDouble()
    }

    fun writePixel(x: Int, y: Int, r: Int, g: Int, b: Int) {
        writablePixelFormat.setArgb(pixelBuffer, x, y, baseWidth, COLORS[r][g][b])
    }

    fun commitBufferChanges() {
        val pixelWriter = image!!.pixelWriter
        pixelWriter.setPixels(0, 0, baseWidth, baseHeight, writablePixelFormat, pixelBuffer, baseWidth)
    }

    companion object {
        private val COLORS =
            Array(256) { r -> Array(256) { g -> Array(256) { b -> (0xFF shl 24) or (r shl 16) or (g shl 8) or b } } }
    }
}
