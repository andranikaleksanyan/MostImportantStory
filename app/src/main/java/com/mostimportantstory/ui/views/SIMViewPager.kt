package com.mostimportantstory.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

/**
 * Created by Karo.Hovhannisyan on 04, June, 2020
 **/

class SwipeLockableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    init {
        postInitViewPager()
    }

    private var swipeEnabled = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (swipeEnabled) {
            true -> super.onTouchEvent(event)
            false -> false
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return when (swipeEnabled) {
            true -> super.onInterceptTouchEvent(event)
            false -> false
        }
    }

    fun setSwipePagingEnabled(swipeEnabled: Boolean) {
        this.swipeEnabled = swipeEnabled
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        var newHeightMeasureSpec = heightMeasureSpec

        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            var height = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val h = child.measuredHeight
                if (h > height) height = h
            }
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    private var mScroller: ScrollerCustomDuration? = null

    private fun postInitViewPager() {
        try {
            val scroller = ViewPager::class.java.getDeclaredField("mScroller")
            scroller.isAccessible = true
            val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true

            mScroller = ScrollerCustomDuration(
                context,
                interpolator.get(null) as Interpolator
            )
            scroller.set(this, mScroller)
        } catch (e: Exception) {
        }
    }
    fun setScrollDurationFactor(scrollFactor: Double) {
        mScroller?.setScrollDurationFactor(scrollFactor)
    }
}

// class for change scroll animation duration

class ScrollerCustomDuration(context: Context?, interpolator: Interpolator) :
    Scroller(context, interpolator) {
    private var mScrollFactor = 1.0

    fun setScrollDurationFactor(scrollFactor: Double) {
        mScrollFactor = scrollFactor
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * mScrollFactor).toInt())
    }

}