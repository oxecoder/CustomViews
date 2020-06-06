package com.oxecoder.customview.dragadditem

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.oxecoder.customview.R

class DragToAddView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int = 0) :
    FrameLayout(context, attrs, defStyleAttrs), CoordinatorLayout.AttachedBehavior, View.OnTouchListener {

    interface OnAddListener {
        fun onAddEvent()
    }

    private var onAddListener: OnAddListener? = null

    private val orgAlpha = 0.4f
    private val touchAlpha = 1.0f

    private var orgHeight = 0f
    private var dragHeightMax = 0f
    private val dragMax = 500f

    private val dragBufferForIcon = 80f
    private val dragProgressSpeed = 2.5f

    private var shouldAdd = false

    private val paint = Paint()

    private var downY = 0f
    private var lp: ViewGroup.LayoutParams? = null

    private val icon by lazy { findViewById<ImageView>(R.id.ic_plus) }
    private val iconDrawable: Drawable by lazy { ContextCompat.getDrawable(context, R.drawable.ic_plus)!! }

    init {
        addView(inflate(context, R.layout.view_drag_to_add, null))
        setOnTouchListener(this)
    }

    fun setOnAddListener(listener: OnAddListener) {
        onAddListener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (orgHeight == 0.0f) {
            // set orgHeight on initial inflation of view
            orgHeight = h.toFloat()
            dragHeightMax = orgHeight.plus(dragMax)
            lp = layoutParams
            this.alpha = orgAlpha
        }

//        container.background = ContextCompat.getDrawable(context, R.drawable.gradient_grey)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onTouchView(event.rawY)
            }

            MotionEvent.ACTION_MOVE -> {
                val diff = downY - event.rawY
                if (diff >= 0) {
                    onDragView(diff)
                }
            }

            MotionEvent.ACTION_UP -> {
                onReleaseView()
            }
        }
        return true
    }

    private fun onTouchView(eventRawY: Float) {
        // on touch  of view set position of Y in order to drag and expand view
        // set alpha to touchAlpha for better visibility
        downY = eventRawY
        this.alpha = touchAlpha
    }

    private fun onDragView(dragDiff: Float) {
        // on drag expand height of view
        // if drag is larger than viewHeightMax do not expand view
        // adjust plus icon color : show progress
        lp?.height = orgHeight.plus(dragDiff).toInt()
        lp?.let {
            if (it.height < dragHeightMax) {
                layoutParams = it
                val bitmap = getProgressingPlusBitmap(dragDiff)
                icon.setImageBitmap(bitmap)
            }
        }
    }

    private fun onReleaseView() {
        // upon release reset downY position
        // and return to original height
        // and return to original transparency
        // and return plus icon to original bitmap
        // check to see if should add event is called
        downY = 0f
        lp?.height = orgHeight.toInt()
        lp?.apply { layoutParams = this }
        this.alpha = orgAlpha
        icon.setImageBitmap(iconDrawable.toBitmap())

        if(shouldAdd){
            shouldAdd = false
            onAddListener?.onAddEvent()
        }
    }

    private fun getProgressingPlusBitmap(dragDiff: Float): Bitmap {
        val iconBitmap = iconDrawable.toBitmap()
        val canvas = Canvas(iconBitmap)
        paint.color = Color.GREEN
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

        val progress = iconBitmap.height - dragDiff.div(dragProgressSpeed) + dragBufferForIcon
        shouldAdd = progress <= 0.0f

        canvas.drawRect(
            0f,
            iconBitmap.height - dragDiff.div(dragProgressSpeed) + dragBufferForIcon,
            iconBitmap.width.toFloat(),
            iconBitmap.height.toFloat(),
            paint
        )

        return iconBitmap
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        onTouch(this, event)
        return false
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return HideBottomViewOnScrollBehavior<View>()
    }

}