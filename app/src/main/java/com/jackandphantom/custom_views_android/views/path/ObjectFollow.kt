package com.jackandphantom.custom_views_android.views.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ObjectFollow(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    val path = Path()
    val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10f
        pathEffect = CornerPathEffect(50f)
    }

    val hand1Paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10f
        pathEffect = CornerPathEffect(50f)
    }


    val hand2Paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10f
        pathEffect = CornerPathEffect(50f)
    }


    val headPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10f
        pathEffect = CornerPathEffect(50f)
    }


    val eyePaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 10f
        pathEffect = CornerPathEffect(50f)
    }

    private var pathLength = 0f
    private var hand1Length = 0f
    private var hand2Length = 0f
    private var headLength = 0f
    private var eyeLength = 0f


    private var pathProgress = 0f
    private var hand1Progress = 0f
    private var hand2Progress = 0f
    private var headProgress = 0f
    private var eyeProgress = 0f

    private val initialMoveX = 350f
    private var initialMoveY = 0f

    private val handPath1 = Path()
    private val handPath2 = Path()
    private val headPath = Path()
    private val eyePath = Path()

    private val cornerPathEffect = CornerPathEffect(50f)

    var hand1Active = false
    var hand2Active = false
    var headActive = false
    var ioActive = false

    private val ioPath = Path()
    private val ioPaint = Paint().apply {
        color  = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 10f
    }

   private var ioLength = 0f
   private var ioProgress = 0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initialMoveY = height / 2f


        path.moveTo(initialMoveX, initialMoveY)
        path.lineTo(initialMoveX + 450f ,height/2f)
        path.lineTo(initialMoveX + 450f, height/2f+ 400f)

        path.lineTo(initialMoveX + 350f, initialMoveY + 400f)
        path.lineTo(initialMoveX + 350f, initialMoveY + 700f)
        path.lineTo(initialMoveX + 250f, initialMoveY + 700f)
        path.lineTo(initialMoveX + 250f, initialMoveY + 400f)
        path.lineTo(initialMoveX + 120f, initialMoveY + 400f)

        path.lineTo(initialMoveX + 120f, initialMoveY + 700f)
        path.lineTo(initialMoveX + 20f, initialMoveY + 700f)
        path.lineTo(initialMoveX + 20f, initialMoveY + 400f)


        path.lineTo(initialMoveX - 80f, initialMoveY + 400f)
        path.lineTo(initialMoveX - 80f, initialMoveY)
        path.lineTo(initialMoveX , initialMoveY)


        //Hand Path 1 draw
        drawHandPath1()
        drawHandPath2()
        drawHeadPath()
        drawEyePath()
        drawIOPath()


        val pathMeasure = PathMeasure(headPath, false)
        val pathMeasure1 = PathMeasure(path, false)
        val pathMeasure2 = PathMeasure(handPath2, false)
        val pathMeasure3 = PathMeasure(handPath1, false)
        val pathMeasure4 = PathMeasure(eyePath, false)
        val pathMeasure5 = PathMeasure(ioPath, false)
        pathLength = pathMeasure1.length

        hand1Length = pathMeasure3.length
        hand2Length = pathMeasure2.length
        headLength = pathMeasure.length
        eyeLength = pathMeasure4.length
        ioLength = pathMeasure5.length

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        canvas.drawPath(path, paint)
        if (hand1Active)
        canvas.drawPath(handPath1, hand1Paint)
        if (hand2Active)
        canvas.drawPath(handPath2, hand2Paint)
        if (headActive) {
            canvas.drawPath(headPath, headPaint)
            canvas.drawPath(eyePath, eyePaint)
        }
        if (ioActive)
        canvas.drawPath(ioPath, ioPaint)

    }


    fun setPercentage(percentage: Float) {
        this.pathProgress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(pathLength, pathLength), pathLength - pathLength * pathProgress)
        paint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setHand1Percentage (percentage: Float) {
        this.hand1Progress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(hand1Length, hand1Length), hand1Length - hand1Length * hand1Progress)
        hand1Paint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setHand2Percentage (percentage: Float) {
        this.hand2Progress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(hand2Length, hand2Length), hand2Length - hand2Length * hand2Progress)
        hand2Paint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setHeadPercentage (percentage: Float) {
        this.headProgress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(headLength, headLength), headLength - headLength * headProgress)
        headPaint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setEyePercentage (percentage: Float) {
        this.eyeProgress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(eyeLength, eyeLength), eyeLength - eyeLength * eyeProgress)
        eyePaint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setParty (percentage: Float) {
        this.ioProgress = percentage
        val pathEffect = DashPathEffect(floatArrayOf(ioLength, ioLength), ioLength - ioLength * ioProgress)
        ioPaint.pathEffect = pathEffect
        invalidate()
    }

    fun drawHandPath1() {
        handPath1.moveTo(initialMoveX - 130f, initialMoveY - 30f)
        handPath1.lineTo(initialMoveX - 130f, initialMoveY + 350)
        handPath1.lineTo(initialMoveX - 230f, initialMoveY + 350)
        handPath1.lineTo(initialMoveX - 230f, initialMoveY -30f)
        handPath1.close()

    }

    fun drawHandPath2() {
        handPath2.moveTo(initialMoveX + 490f, initialMoveY - 30f)
        handPath2.lineTo(initialMoveX + 490f, initialMoveY + 350)
        handPath2.lineTo(initialMoveX + 590f, initialMoveY + 350)
        handPath2.lineTo(initialMoveX + 590f, initialMoveY -30f)
        handPath2.close()

    }

    fun drawHeadPath() {
        val rectF = RectF(initialMoveX - 90f, initialMoveY - 300f,
            initialMoveX + 450f,
             initialMoveY+ 250f)
        headPath.arcTo(rectF, 0f, -180f)
        headPath.close()
        headPath.moveTo(initialMoveX , initialMoveY - 230f)
        headPath.lineTo(initialMoveX - 100f, initialMoveY - 350f)
        headPath.moveTo(initialMoveX + 350f, initialMoveY - 240f)
        headPath.lineTo(initialMoveX + 420f, initialMoveY - 370f)

    }

    fun drawEyePath() {
        eyePath.addCircle(initialMoveX + 50, initialMoveY - 150, 25f, Path.Direction.CW)
        eyePath.addCircle(initialMoveX + 300, initialMoveY - 150, 25f, Path.Direction.CW)

    }

    fun drawIOPath() {
        val rectF = RectF(initialMoveX + 50f, 400f, initialMoveX + 100f, 600f)
        ioPath.addRect(rectF, Path.Direction.CW)
        ioPath.addCircle(initialMoveX + 250f, 500f, 100f, Path.Direction.CCW)
    }

}