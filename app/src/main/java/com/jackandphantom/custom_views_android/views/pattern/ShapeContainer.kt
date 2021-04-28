package com.example.customviewimple.views.patterns

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

class ShapeContainer(context: Context, attributeSet: AttributeSet): FrameLayout(context, attributeSet) {

    private val patternList = arrayListOf<Animator>()

    private val animatorSet = AnimatorSet()

   init {
       animatorSet.interpolator = LinearInterpolator()
       var distance=150f
       for (i in 1..4) {
           val view = CircularPattern(context, attributeSet)
           view.increaseDistance(distance)
           addView(view)
           //patternList.add(view.animateScale(500L-(10 * i)))
           distance += 60f

           val scaleXAnimator = ObjectAnimator.ofFloat(view, "ScaleX", 0f, 1f)
           scaleXAnimator.repeatCount = ObjectAnimator.INFINITE
           scaleXAnimator.repeatMode = ObjectAnimator.REVERSE
           scaleXAnimator.startDelay = i * 500L
           scaleXAnimator.duration = 3000L
           patternList.add(scaleXAnimator)
           val scaleYAnimator = ObjectAnimator.ofFloat(view, "ScaleY", 0F,1f)
           scaleYAnimator.repeatCount = ObjectAnimator.INFINITE
           scaleYAnimator.repeatMode = ObjectAnimator.REVERSE
           scaleYAnimator.startDelay = i * 500L
           scaleYAnimator.duration = 3000L
           patternList.add(scaleYAnimator)

       }
      // animatorSet.playTogether(patternList)
       animatorSet.playTogether(patternList)
       animatorSet.start()

   }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

}