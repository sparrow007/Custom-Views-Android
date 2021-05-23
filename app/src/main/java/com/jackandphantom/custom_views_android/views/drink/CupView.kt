package com.jackandphantom.custom_views_android.views.drink

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import kotlin.math.cos

class CupView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){

    private val topBar1Paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 15f
        pathEffect = CornerPathEffect(20f)

    }

    private val bottlePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 15f
        pathEffect = CornerPathEffect(30f)
    }

    private val strawPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
        //pathEffect = CornerPathEffect(30f) . yes
    }

    private val topBar2Paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 15f
        pathEffect = CornerPathEffect(20f)
    }

    private val wavePaint = Paint().apply {
        color = Color.parseColor("#006bdc")
        style = Paint.Style.FILL
    }
    private var mainPath = Path()

    private val topBar2Path = Path()
    private val topBar1Path = Path()
    private val bottlePath = Path()
    private val strawPath = Path()

    private val wavePath = Path()
    private var progress = 0
    private var φ = 0f
    private var w = 0f
    private var performAnimation: Boolean = true
    private var progressWaveX = 320f
    private var progressWidth = 0


    private var strawPathLength = 0f
    private var strawPathProgress = 0f

    private var topBar1PathLength = 0f
    private var topBar1PathProgress = 0f
    private var topBar2PathLength = 0f
    private var topBar2PathProgress = 0f
    private var bottlePathLength = 0f
    private var bottlePathProgress = 0f
    private var wavePathProgress = 0f
    private var wavePathLength = 0f

    var strawActive = false
    var top1BarActive = false
    var top2BarActive = false
    var bodyActive = false
    var waveAnimation = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        strawPath.moveTo(480f, 250f)
        strawPath.lineTo(520f, 150f)
        strawPath.lineTo(650f, 100f)
        strawPath.lineTo(640f, 130f)
        strawPath.lineTo(540f, 170f)
        strawPath.lineTo(510f, 250f)

        topBar1Path.moveTo(340f, 300f)
        topBar1Path.lineTo(360f, 250f)
        topBar1Path.lineTo(630f, 250f)
        topBar1Path.lineTo(660f, 300f)

        topBar2Path.moveTo(300f, 300f)
        topBar2Path.lineTo(700f, 300f)
        topBar2Path.lineTo(730f, 340f)
        topBar2Path.lineTo(270f, 340f)
        topBar2Path.close()

        bottlePath.moveTo(340f, 340f)
        bottlePath.lineTo(370f, 950f)
        bottlePath.lineTo(600f, 950f)
        bottlePath.lineTo(650f, 340f)

        mainPath.addPath(strawPath)
        mainPath.addPath(topBar1Path)
        mainPath.addPath(topBar2Path)
        mainPath.addPath(bottlePath)
        this.w = (2f * Math.PI / width).toFloat()

        progress =  height / 5 - 15


        topBar1PathLength = PathMeasure(topBar1Path, false).length
        topBar2PathLength = PathMeasure(topBar2Path, false).length
        strawPathLength = PathMeasure(strawPath, false).length
        bottlePathLength = PathMeasure(bottlePath, false).length

        wavePathLength = PathMeasure(topBar1Path, false).length

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        canvas.translate(0f, height/5f)

        wavePath.reset()
        /***
         * Draw wave path for showing the waves
         */
        φ -= 0.2f
        var y = 0f
        wavePath.moveTo(370f, height / 2f - 160f)
        /**
         * Loop for creating wave effect
         */
        var x = 20
        while (x <= 340 ) {
            y = (15 * cos((w * x + φ).toDouble())).toFloat() + progress
            wavePath.lineTo( x+progressWaveX, y)
            x += 30
        }

        wavePath.lineTo(605f, height / 2f-160)
        if (waveAnimation)
            canvas.drawPath(wavePath, wavePaint)

        if (bodyActive)
            canvas.drawPath(bottlePath, bottlePaint)
        if (strawActive)
            canvas.drawPath(strawPath, strawPaint)
        if (top1BarActive)
            canvas.drawPath(topBar1Path, topBar1Paint)
        if (top2BarActive)
            canvas.drawPath(topBar2Path, topBar2Paint)

        /**
         * Use this for perform the animation when all the required path
         * is drawn on the screen
         */
        if (waveAnimation)
            postInvalidateDelayed(30)
    }

    fun setTop1Progress(progress: Float) {
        this.topBar1PathProgress = progress
        val pathEffect = DashPathEffect(floatArrayOf(topBar1PathLength, topBar1PathLength), topBar1PathLength - topBar1PathLength * topBar1PathProgress)
        val cornerPathEffect = CornerPathEffect(20f)
        topBar1Paint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setTop2Progress(progress: Float) {
        this.topBar2PathProgress = progress
        val pathEffect = DashPathEffect(floatArrayOf(topBar2PathLength, topBar2PathLength), topBar2PathLength - topBar2PathLength * topBar2PathProgress)
        val cornerPathEffect = CornerPathEffect(30f)
        topBar2Paint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setBottleProgress(progress: Float) {
        this.bottlePathProgress = progress
        val pathEffect = DashPathEffect(floatArrayOf(bottlePathLength, bottlePathLength), bottlePathLength - bottlePathLength * bottlePathProgress)
        val cornerPathEffect = CornerPathEffect(30f)
        bottlePaint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    fun setStrawProgress(progress: Float) {
        this.strawPathProgress = progress
        val pathEffect = DashPathEffect(floatArrayOf(strawPathLength, strawPathLength), strawPathLength - strawPathLength * strawPathProgress)
        val cornerPathEffect = CornerPathEffect(15f)
        strawPaint.pathEffect = ComposePathEffect(pathEffect, cornerPathEffect)
        invalidate()
    }

    /**
     * It uses for the animation of the wave effect another changes
     */
    fun setProgress(progress: Int) {
        this.progress = (height / 2 - 170) - progress
        if (progress >= (height / 12) && progressWidth < 340) {
            this.progressWidth += 1
            this.progressWaveX
        }
        invalidate()
    }
    /**
     * Added the animation of this view
     */
    fun animation() {
        val objectAnimate = ObjectAnimator.ofInt(this, "progress", 0,  120)
        objectAnimate.duration = 4000
        objectAnimate.interpolator = AccelerateInterpolator()
        objectAnimate.start()

    }

}