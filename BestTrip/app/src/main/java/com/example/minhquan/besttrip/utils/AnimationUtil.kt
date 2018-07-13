package com.example.minhquan.besttrip.utils


import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log

import android.view.View
import kotlinx.android.synthetic.main.activity_route.view.*

/**
 * Animation for moving search box to avoid being overlapped by keyboard
 */
fun View.upBox(context: Context) {

    Log.d("Animation", "Trigger up")
    val objectAnimator = ObjectAnimator.ofFloat(search_box, "y", search_box.y, convertDpToPixel(100f,context))
    objectAnimator.duration = 650
    objectAnimator.start()
}

/**
 * Animation for moving search box to return origin location
 */
fun View.downBox(context: Context) {
    Log.d("Animation", "Trigger down")
    val objectAnimator = ObjectAnimator.ofFloat(search_box, "y", search_box.y, -convertDpToPixel(100f,context))
    objectAnimator.duration = 650
    objectAnimator.start()
}

/**
 * Animation for focusing on fab button: Expand button
 */
fun View.expandFab(context: Context) {
    if (btnFab.visibility == View.INVISIBLE)
        btnFab.visibility = View.VISIBLE

}






private fun View.centerX(): Int {
    return Math.round(x + width / 2)
}

private fun View.centerY(): Int {
    return Math.round(y + height / 2)
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi / 160f)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px      A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi / 160f)
}



