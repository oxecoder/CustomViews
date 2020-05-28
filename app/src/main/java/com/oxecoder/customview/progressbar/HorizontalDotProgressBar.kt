package com.oxecoder.customview.progressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation

class HorizontalDotProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // Paint is used to define how to draw onto the View
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dotRadius = 12
    private val dotGap = 50
    private val dotCount = 5

    private var progressPosition = 0

    private val colorProgressStart = Color.parseColor("#CECECE")
    private val colorProgressFinish = Color.parseColor("#D792FF")

    override fun onDraw(canvas: Canvas) {
        // Canvas is used to define what you draw onto the View
        super.onDraw(canvas)
        createProgressDots(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = (dotGap * dotCount)
        val height = dotRadius * 2
        setMeasuredDimension(width, height)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startProgress()
    }

    private fun createProgressDots(canvas: Canvas) {
        for (i in 0 until dotCount) {
            if (i < progressPosition) {
                paint.color = colorProgressFinish
                canvas.drawCircle(12f + (i * dotGap), 12f, dotRadius.toFloat(), paint)
            } else {
                paint.color = colorProgressStart
                canvas.drawCircle(12f + (i * dotGap), 12f, dotRadius.toFloat(), paint)
            }
        }
    }

    private fun startProgress() {
        val progressAnimation = ProgressAnimation()
        progressAnimation.duration = 150
        progressAnimation.repeatCount = Animation.INFINITE
        progressAnimation.interpolator = DecelerateInterpolator()
        progressAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                progressPosition++
                if (progressPosition > dotCount) {
                    progressPosition = 0
                }
            }

            override fun onAnimationEnd(p0: Animation?) {}
            override fun onAnimationStart(p0: Animation?) {}
        })
        startAnimation(progressAnimation)
    }

    inner class ProgressAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            invalidate()
        }
    }
}