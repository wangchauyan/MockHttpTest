package idv.chauyan.mockhttp.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import idv.chauyan.mockhttp.R


class CircleImageView : android.support.v7.widget.AppCompatImageView {


    var bitmap: Bitmap? = null

    var paint = Paint().apply {
        isAntiAlias = true
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    /**
     * setup avatar_url from outside for getting user avatar
     */
    fun setImageURL(url:String) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.place_holder)
                .into(this)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        loadBitmap()

        if (bitmap == null) return

        val circleCenter = Math.min(canvas!!.width, canvas.height) / 2.0f
        canvas.drawCircle(circleCenter, circleCenter, circleCenter, paint)
    }

    /**
     * load bitmap from contained drawable object
     */
    private fun loadBitmap() {
        if (drawable == null) return
        bitmap = drawableToBitmap(drawable)
        updateShader()
    }

    /**
     * convert drawable object to bitmap object
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null
        if (drawable is BitmapDrawable) drawable.bitmap

        try {
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            return bitmap
        }
        catch (e :Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * create bitmap shader via passing loaded bitmap from self-owned drawable object
     */
    private fun updateShader() {
        if (bitmap == null) return

        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = Math.min(width, height) * 1.0f / Math.min(bitmap!!.width, bitmap!!.height) * 1.0f

        val matrix = Matrix()
        matrix.setScale(scale, scale)
        shader.setLocalMatrix(matrix)

        paint.setShader(shader)
    }
}
