package com.data.blur.blurviewdemon.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat

@Suppress("DEPRECATION")
class ImageUtil {
    companion object {
        fun drawableToBitamp(context: Context, resid: Int): Bitmap {
            val drawable = context.getDrawable(resid)
            val w = drawable?.intrinsicWidth
            val h = drawable?.intrinsicHeight
            val config = if (drawable?.opacity != PixelFormat.OPAQUE)
                Bitmap.Config.ARGB_8888
            else
                Bitmap.Config.RGB_565
            var bitmap: Bitmap?  = Bitmap.createBitmap(w!!, h!!, config)
            val canvas = Canvas(bitmap!!)
            drawable?.setBounds(0, 0, w, h)
            drawable?.draw(canvas)
            return bitmap
        }
    }
}