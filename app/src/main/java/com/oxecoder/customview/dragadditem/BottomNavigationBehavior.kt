package com.oxecoder.customview.dragadditem

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        Timber.e("change")
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        val count = (target as RecyclerView).adapter?.itemCount
        val position =
            ((target as RecyclerView).layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

        val foo = max(0f, min(child.height.toFloat(), child.translationY + dy))
        Timber.e(foo.toString())
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))

//        if (count?.minus(1) == position) {
//            Timber.e("bottom!")
//            child.translationY = max(0f, -1 * min(child.height.toFloat(), child.translationY + dy))
//        }else{
//            child.translationY = max(0f, -1 * min(child.height.toFloat(), child.translationY + dy))
//        }

    }
}