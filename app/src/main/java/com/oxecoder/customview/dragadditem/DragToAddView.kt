package com.oxecoder.customview.dragadditem

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oxecoder.customview.R
import timber.log.Timber

var topMargin: Int = 0

class DragToAddView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int = 0) :
    FrameLayout(context, attrs, defStyleAttrs), CoordinatorLayout.AttachedBehavior, View.OnTouchListener {
    private var orgHeight = 0f
    private var dragHeightMax = 0f

    private var downY = 0f
    private var lp: ViewGroup.LayoutParams? = null
    private var view : View? = null

    init {
        addView(inflate(context, R.layout.view_drag_to_add, null))
        setOnTouchListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        this.post {
            orgHeight = height.toFloat()
            dragHeightMax = orgHeight.plus(500f)
            lp = layoutParams
            view = this
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = event.rawY
            }

            MotionEvent.ACTION_MOVE -> {
                val diff = downY - event.rawY
                if (diff >= 0) {
                    lp?.height = orgHeight.plus(diff).toInt()
                    lp?.let { if (it.height < dragHeightMax) layoutParams = it }
                }
            }

            MotionEvent.ACTION_UP -> {
                downY = 0f
                val p = layoutParams
                lp?.height = orgHeight.toInt()
                lp?.apply { layoutParams = this }
            }
        }
        return true
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        onTouch(this, event)
        return false
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return HideBottomViewOnScrollBehavior<View>()
    }

}