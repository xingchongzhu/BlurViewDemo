package blur.data.com.blureimageviewdemo

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.SeekBar
import com.data.blur.blurviewdemon.R
import com.data.blur.blurviewdemon.utils.ImageUtil
import com.data.blur.blurviewdemon.view.BlurView

class MainActivity : Activity(), SeekBar.OnSeekBarChangeListener{
    var blurView : BlurView? = null
    var bitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bitmap = ImageUtil.drawableToBitamp(this,R.drawable.source)
        initView()
    }

    private fun initView() {
        findViewById<SeekBar>(R.id.progress).setOnSeekBarChangeListener(this)
        blurView = findViewById<BlurView>(R.id.blur_image_view)
        bitmap?.let { blurView?.setImageBimtap(it,0) }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        bitmap?.let { blurView?.setImageBimtap(it,p1) }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}
