package hu.bme.aut.guitarscaleviz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import hu.aut.bme.guitarscaleviz.model.Scale
import hu.bme.aut.guitarscaleviz.model.Fretboard

class MyView : View {
    private val p: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintColor = ResourcesCompat.getColor(resources, R.color.drawColor, null)
    private var fret: Fretboard? = null
    private var scale: Scale? = null

    constructor(context: Context?) : super(context) {
        init(null, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(attrs, 0, 0)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr, 0)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs, defStyleAttr, defStyleRes)
    }

    private fun init(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        p!!.color = ResourcesCompat.getColor(resources, R.color.drawColor, null)
        p.strokeWidth = 0F
        p.textSize = p.textSize * 3
        isFocusableInTouchMode = true
    }


    fun setFretAndScale(f: Fretboard?, s: Scale?) {
        fret = f
        scale = s
        fret!!.search(scale)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (fret != null){
            super.onDraw(canvas)
            p!!.color = ResourcesCompat.getColor(resources, R.color.drawColor, null)
            p.style = Paint.Style.FILL_AND_STROKE
            val width = width.toFloat()
            val height = height.toFloat()
            val realheight = height - 50
            val fretlength: Float = width / (fret!!.fretCount + 1)
            val r = width/68
            canvas.drawRect(fretlength, 50f, fretlength + 5, height - 50, p)
            canvas.drawLine(0f, 50f, width, 50f, p)
            canvas.drawLine(0f, realheight, width, realheight, p)
            for (i in 1 until fret!!.stringCount) {
                canvas.drawLine(
                    0f,
                    0 + 25 + realheight / fret!!.stringCount * i,
                    width,
                    0 + 25 + realheight / fret!!.stringCount * i,
                    p
                )
            }
            p.style = Paint.Style.FILL_AND_STROKE
            for (i in 1..fret!!.fretCount) {
                canvas.drawLine(0 + fretlength * i, 50f, 0 + fretlength * i, realheight, p)
                val s = i.toString()
                canvas.drawText(s, 25 + fretlength * i, 35f, p)
            }

            for (i in fret!!.stringCount - 1 downTo 0) {
                val str = fret?.getStringAtIdx(i)?.startingNote?.name.toString()
                p.color = ResourcesCompat.getColor(resources, R.color.drawColor, null)
                p.style = Paint.Style.FILL_AND_STROKE
                canvas.drawText(
                    str,
                    19f,
                    -30 + r / 2 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                    p
                )
                if (fret?.getStringAtIdx(i)?.noteAtIdx(0)?.inScale!!) {
                    if (fret!!.getStringAtIdx(i)!!.startingNote.isRootNote) {
                        p.color = ResourcesCompat.getColor(resources, R.color.drawColorStrong, null)
                        p.style = Paint.Style.STROKE
                        canvas.drawCircle(
                            19 + r / 2,
                            -30 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                            r,
                            p
                        )
                        p.style = Paint.Style.FILL_AND_STROKE
                        canvas.drawText(
                            str,
                            19f,
                            -30 + r / 2 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                            p
                        )
                    } else {
                        p.color = ResourcesCompat.getColor(resources, R.color.drawColor, null)
                        p.style = Paint.Style.STROKE
                        canvas.drawCircle(
                            19 + r / 2,
                            -30 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                            r,
                            p
                        )
                    }
                }
            }
            for (i in fret!!.stringCount - 1 downTo 0) {
                for (j in 1..fret!!.fretCount) {
                    if (fret!!.getStringAtIdx(i)!!.noteAtIdx(j).inScale) {
                        if (fret!!.getStringAtIdx(i)!!.noteAtIdx(j).isRootNote) {
                            p.color = ResourcesCompat.getColor(resources, R.color.drawColorStrong, null)
                            val s: String  = fret!!.getStringAtIdx(i)!!.noteAtIdx(j).name.toString()
                            p.style = Paint.Style.FILL_AND_STROKE
                            canvas.drawText(
                                s,
                                25 + fretlength * j,
                                -30 + r / 2 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                                p
                            )
                            p.style = Paint.Style.STROKE
                            canvas.drawCircle(
                                25 + r / 2 + fretlength * j,
                                -30 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                                r,
                                p
                            )
                        } else {
                            p.color = ResourcesCompat.getColor(resources, R.color.drawColor, null)
                            val s = fret!!.getStringAtIdx(i)!!.noteAtIdx(j).name.toString()
                            p.style = Paint.Style.FILL_AND_STROKE
                            canvas.drawText(
                                s,
                                25 + fretlength * j,
                                -30 + r / 2 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                                p
                            )
                            p.style = Paint.Style.STROKE
                            canvas.drawCircle(
                                25 + r / 2 + fretlength * j,
                                -30 + realheight / fret!!.stringCount * (fret!!.stringCount - i),
                                r,
                                p
                            )
                        }
                    }
                }
            }
        }

    }
}