package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.lukeneedham.timetotime.R

/** The percentage of available height the background image should occupy */
private const val baseImageHeightPercent = 0.6f

/**
 * Percentage offset from top of image, marking top of available text space
 */
private const val textTopOffsetPercent = 0.362f

/**
 * Percentage height of the image which may be given to text
 */
private const val textAvailableHeightPercent = 0.19f

private val baseSecondsRemainingTextSize = 40.sp

@Composable
fun Clock(seconds: Long?, onPinch: () -> Unit) {
    val secondsText = seconds?.toString() ?: ""

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Pinchable { scale ->
            if(scale != 1f) {
                // Scale has changed - so pinched
                onPinch()
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                val limitedScale = (baseImageHeightPercent * scale)
                    .coerceAtMost(1f)
                    .coerceAtLeast(0.3f)
                val textSize = baseSecondsRemainingTextSize * limitedScale
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(limitedScale)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "A big clock",
                    )
                    PercentageVerticalPositioner(
                        textTopOffsetPercent,
                        textAvailableHeightPercent
                    ) {
                        Text(
                            text = secondsText,
                            fontSize = textSize
                        )
                    }
                }
            }
        }
    }
}
