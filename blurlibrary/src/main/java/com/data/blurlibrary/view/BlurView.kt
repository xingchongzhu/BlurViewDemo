package com.data.blurlibrary

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.widget.ImageView


 class BlurView : ImageView {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,-1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr,-1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init(){
        setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public fun setImageBimtap(bitmap : Bitmap, ratio : Int){
        BlurUtil.getBlurBitmap(
                context,
                bitmap,
                ratio)
        { bitmap -> setImageBitmap(bitmap)}
    }
}
