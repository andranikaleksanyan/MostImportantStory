package com.mostimportantstory.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mostimportantstory.R
import kotlinx.android.synthetic.main.view_bubble.view.*

/**
 * Created by Karo.Hovhannisyan on 19, July, 2020
 **/
class BubbleMessageView : FrameLayout {

    companion object {
        private const val TYPE_DEFAULT = 0
        private const val TYPE_T_L = 2
        private const val TYPE_T_R = 4
        private const val TYPE_B_L = 1
        private const val TYPE_B_R = 3
    }

    private var type = TYPE_DEFAULT
    private var text: String = ""
    private var maxWidth: Float = 0F

    constructor(context: Context) :
            this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)

    @SuppressLint("Recycle", "CustomViewStyleable")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_bubble, this, true)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BubbleMessageView)
            if (a.hasValue(R.styleable.BubbleMessageView_bubble_type))
                drawBubble(a.getInt(R.styleable.BubbleMessageView_bubble_type, TYPE_DEFAULT))

            if (a.hasValue(R.styleable.BubbleMessageView_bubble_text))
                text = a.getString(R.styleable.BubbleMessageView_bubble_text).toString()

            if (a.hasValue(R.styleable.BubbleMessageView_bubble_maxWidth)) {
                maxWidth = a.getDimension(R.styleable.BubbleMessageView_bubble_maxWidth, 0F)
                tv_cont.maxWidth = maxWidth.toInt()
            }

            tv_cont.text = text
        }
    }

    fun setText(text: String) {
        tv_cont.text = text
    }

    fun drawBubble(type: Int) {
        when (type) {
            TYPE_T_R -> drawTopRightBubble()
            TYPE_T_L -> drawTopLeftBubble()
            TYPE_B_R -> drawBottomRightBubble()
            TYPE_B_L -> drawBottomLeftBubble()
            else -> drawDefaultBubble()
        }
    }

    private fun drawTopRightBubble() {
        t_r.setImageResource(R.drawable.puzzle_tr_t_r)
        t_l.setImageResource(R.drawable.puzzle_tr_t_l)
        b_l.setImageResource(R.drawable.puzzle_tr_b_l)
        b_r.setImageResource(R.drawable.puzzle_tr_b_r)
        r_line.setBackgroundResource(R.drawable.puzzle_tr_r_line)
        l_line.setBackgroundResource(R.drawable.puzzle_tr_l_line)
        t_line.setBackgroundResource(R.drawable.puzzle_tr_t_line)
        b_line.setBackgroundResource(R.drawable.puzzle_tr_b_line)
    }

    private fun drawTopLeftBubble() {
        t_r.setImageResource(R.drawable.puzzle_tl_t_r)
        t_l.setImageResource(R.drawable.puzzle_tl_t_l)
        b_l.setImageResource(R.drawable.puzzle_tl_b_l)
        b_r.setImageResource(R.drawable.puzzle_tl_b_r)
        r_line.setBackgroundResource(R.drawable.puzzle_tl_r_line)
        l_line.setBackgroundResource(R.drawable.puzzle_tl_l_line)
        t_line.setBackgroundResource(R.drawable.puzzle_tl_t_line)
        b_line.setBackgroundResource(R.drawable.puzzle_tl_b_line)
    }

    private fun drawBottomRightBubble() {
        t_r.setImageResource(R.drawable.puzzle_br_t_r)
        t_l.setImageResource(R.drawable.puzzle_br_t_l)
        b_l.setImageResource(R.drawable.puzzle_br_b_l)
        b_r.setImageResource(R.drawable.puzzle_br_b_r)
        r_line.setBackgroundResource(R.drawable.puzzle_br_r_line)
        l_line.setBackgroundResource(R.drawable.puzzle_br_l_line)
        t_line.setBackgroundResource(R.drawable.puzzle_br_t_line)
        b_line.setBackgroundResource(R.drawable.puzzle_br_b_line)
    }

    private fun drawBottomLeftBubble() {
        t_r.setImageResource(R.drawable.puzzle_bl_t_r)
        t_l.setImageResource(R.drawable.puzzle_bl_t_l)
        b_l.setImageResource(R.drawable.puzzle_bl_b_l)
        b_r.setImageResource(R.drawable.puzzle_bl_b_r)
        r_line.setBackgroundResource(R.drawable.puzzle_bl_r_line)
        l_line.setBackgroundResource(R.drawable.puzzle_bl_l_line)
        t_line.setBackgroundResource(R.drawable.puzzle_bl_t_line)
        b_line.setBackgroundResource(R.drawable.puzzle_bl_b_line)
    }

    private fun drawDefaultBubble() {
        t_r.setImageResource(R.drawable.puzzle_t_r)
        t_l.setImageResource(R.drawable.puzzle_t_l)
        b_l.setImageResource(R.drawable.puzzle_b_l)
        b_r.setImageResource(R.drawable.puzzle_b_r)
        r_line.setBackgroundResource(R.drawable.puzzle_r_line)
        l_line.setBackgroundResource(R.drawable.puzzle_l_line)
        t_line.setBackgroundResource(R.drawable.puzzle_t_line)
        b_line.setBackgroundResource(R.drawable.puzzle_b_line)
    }
}