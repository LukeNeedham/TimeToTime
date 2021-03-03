package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukeneedham.timetotime.R

/** The percentage of available height the background image should occupy */
private const val baseImageHeightPercent = 0.7f

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
fun Clock(seconds: Long?, isFinished: Boolean, onPinch: () -> Unit) {
    val secondsText = seconds?.toString() ?: ""

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Pinchable { scale ->
            if (scale != 1f) {
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
                        contentDescription = "A tall clock tower. It has a big clock face in the middle",
                    )
                    PercentageVerticalPositioner(
                        textTopOffsetPercent,
                        textAvailableHeightPercent
                    ) {
                        Crossfade(
                            targetState = isFinished,
                            animationSpec = TweenSpec(500)
                        ) { isFinished ->
                            if (isFinished) {
                                Image(
                                    painterResource(id = R.drawable.face),
                                    contentDescription = "A face with cool sunglasses",
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .padding(5.dp),
                                )
                            } else {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxHeight().aspectRatio(1f)
                                ) {
                                    Text(
                                        text = secondsText,
                                        fontSize = textSize,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
