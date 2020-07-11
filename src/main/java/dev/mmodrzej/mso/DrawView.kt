package dev.mmodrzej.mso

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "DrawView"

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint = Paint()
    private var path = Path()
    private val paths = ArrayList<Path>()
    private val paints = ArrayList<Paint>()
    private var isDrawing = false
    private var color = Color.GREEN

    private var prevX = 0f
    private var prevY = 0f


    init {
        paint.apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 10f
            isAntiAlias = true
        }
    }

    fun setColor(color: Int) {
        this.color = color
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touch(x, y)
            MotionEvent.ACTION_MOVE -> drag(x, y)
            MotionEvent.ACTION_UP -> release(x, y)
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for((index, path) in paths.withIndex()) {
            canvas.drawPath(path, paints[index])
        }
        if(isDrawing) {
            canvas.drawPath(path, paint)
        }
    }

    private fun touch(x: Float, y: Float) {
        Log.d(TAG, "ACTION_DOWN")
        isDrawing = true
        paint.color = color
        paints.add(paint)

        path = Path()
        path.moveTo(x, y)
        prevX = x
        prevY = y
    }

    private fun drag(x: Float, y: Float) {
        Log.d(TAG, "ACTION_MOVE")
        path.quadTo(prevX, prevY, (x + prevX) / 2, (y + prevY) / 2)
        prevX = x
        prevY = y
    }

    private fun release(x: Float, y: Float) {
        Log.d(TAG, "ACTION_UP")
        isDrawing = false
        path.lineTo(x, y)
        paths.add(path)

        paint = Paint()
        paint.apply {
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 10f
            isAntiAlias = true
        }
    }



}