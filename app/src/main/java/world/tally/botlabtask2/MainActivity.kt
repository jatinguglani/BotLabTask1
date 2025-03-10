package world.tally.botlabtask2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import org.opencv.android.OpenCVLoader
import world.tally.botlabtask2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a text view

        val textfield = TextView (this)

        textfield.layoutParams = RelativeLayout.LayoutParams (
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        textfield.setTextColor(Color.parseColor ("#000000"))
        textfield.gravity = Gravity.CENTER
        textfield.x = 250f
        textfield.y = 1000f

        setContentView (textfield)

        // cheak whether OpenCV is initialised

        if (IsOpenCVInitialized()) {

            Log.d ("OpenCVInitialisation", "OpenCV loaded successfully")
            Toast.makeText (this, "OpenCV initialization Succeed!", Toast.LENGTH_LONG).show ()
            textfield.text = "OpenCV initialization Succeed!"

        } else {

            Log.d ("OpenCVInitialisation", "OpenCV initialization failed!")
            Toast.makeText (this, "OpenCV initialization failed!", Toast.LENGTH_LONG).show ()
            textfield.text = "OpenCV initialization failed!"
        }

    }

    /**
     * A native method that is implemented by the 'botlabtask2' native library,
     * which is packaged with this application.
     */
    external fun IsOpenCVInitialized (): Boolean

    companion object {
        // Used to load the 'botlabtask2' library on application startup.
        init {
            System.loadLibrary("botlabtask2")
            System.loadLibrary("opencv_java4")
        }
    }
}