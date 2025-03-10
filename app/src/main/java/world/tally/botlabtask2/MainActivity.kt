package world.tally.botlabtask2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Camera
//import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import world.tally.botlabtask2.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnGrayscale: Button
    private lateinit var btnEdge: Button
    private lateinit var relativeLayout: RelativeLayout

    private var filterType = 0  // 0: Grayscale, 1: Edge Detection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout = RelativeLayout(this)
        relativeLayout.layoutParams = RelativeLayout.LayoutParams (
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        imageView = ImageView(this)
        imageView.setImageResource(R.drawable.pic1)
        imageView.layoutParams = RelativeLayout.LayoutParams (
            500,
            500
        )
        imageView.x = 300f
        imageView.y = 500f
        relativeLayout.addView(imageView)

        btnGrayscale = Button (this)
        btnGrayscale.text = "GrayScale"
        btnGrayscale.layoutParams = RelativeLayout.LayoutParams (
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        btnGrayscale.x = 400f
        btnGrayscale.y = 1200f
        relativeLayout.addView(btnGrayscale)
        btnGrayscale.setOnClickListener {
            filterType = 0
            startCamera()
        }

        btnEdge = Button (this)
        btnEdge.text = "Edge"
        btnEdge.layoutParams = RelativeLayout.LayoutParams (
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        btnEdge.x = 400f
        btnEdge.y = 1400f
        relativeLayout.addView(btnEdge)

        btnEdge.setOnClickListener {
            filterType = 1
            startCamera()
        }

        setContentView(relativeLayout)

    }

    private fun startCamera()
    {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                val bitmap = imageProxy.toBitmap()
                processImage(bitmap, filterType)
                runOnUiThread { imageView.setImageBitmap(bitmap) }
                imageProxy.close()
            }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun ImageProxy.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer
        val bytes = ByteArray(yBuffer.remaining())
        yBuffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


    /**
     * A native method that is implemented by the 'botlabtask1' native library,
     * which is packaged with this application.
     */
    external fun processImage(bitmap: Bitmap, filterType: Int)

    companion object {
        // Used to load the 'botlabtask2' library on application startup.
        init {
            System.loadLibrary("botlabtask2")
            System.loadLibrary("opencv_java4")
        }
    }
}