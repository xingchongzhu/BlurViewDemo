package com.data.blur.blurviewdemon.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import kotlinx.coroutines.*

class BlurUtil {
    companion object {
        val TAG: String = "BlurUtil"
        const val maxRadius: Int = 24

        fun getBlurBitmap(context: Context, bitmap: Bitmap, ratio: Int, callback: (Bitmap?) -> Unit) {
            GlobalScope.async {
                if (ratio != 0) {
                    val width = (bitmap.width / ratio)
                    val height = (bitmap.height / ratio)
                    val scalBimap: Bitmap? = Bitmap.createScaledBitmap(bitmap,
                            width,
                            height,
                            false)
                    val blurBitmap: Bitmap? = blur(context, scalBimap, maxRadius)
                    Log.d(TAG, "getBlurBitmap ratio = " + ratio + " width = " + width + " height = " + height)
                    withContext(Dispatchers.Main) {
                        callback(blurBitmap)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(bitmap)
                    }
                }
            }
        }

        fun blur(context: Context, bitmap: Bitmap?, radius: Int): Bitmap? {
            var radius = radius
            if (bitmap == null) {
                return null
            }
            if (radius >= 25) {
                radius = 24
            }
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            //Instantiate a new Renderscript
            val rs = RenderScript.create(context)
            //Create an Intrinsic Blur Script using the Renderscript
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            if (bitmap == null) {
                return null
            }

            val fromBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val allIn = Allocation.createFromBitmap(rs, fromBitmap)
            val allOut = Allocation.createFromBitmap(rs, outBitmap)
            //Set the radius of the blur: 0 < radius <= 25
            blurScript.setRadius(radius.toFloat())
            //Perform the Renderscript
            blurScript.setInput(allIn)
            blurScript.forEach(allOut)
            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap)
            //recycle the original bitmap

            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            //After finishing everything, we destroy the Renderscript.
            rs.destroy()
            return outBitmap
        }
    }
}