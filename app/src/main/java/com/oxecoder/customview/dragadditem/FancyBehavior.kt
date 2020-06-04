package com.oxecoder.customview.dragadditem

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import timber.log.Timber

// Generic definition means that you can attach a FancyBehavior to any View class
class FancyBehavior<V : View> @JvmOverloads constructor(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<V>(context, attrs) {


    // can be used to store state of behavior. It is encouraged to build your behaviors as lightweight as possible
    override fun onSaveInstanceState(parent: CoordinatorLayout, child: V): Parcelable? {
        return super.onSaveInstanceState(parent, child)
    }

    // can be used to restore state of behavior. It is encouraged to build your behaviors as lightweight as possible
    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: V, state: Parcelable) {
        super.onRestoreInstanceState(parent, child, state)
    }


    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Timber.e("on nested scroll")
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }


}

