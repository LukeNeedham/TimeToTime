package com.lukeneedham.timetotime

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lukeneedham.timetotime.ui.countdown.CountDownViewModel
import com.lukeneedham.timetotime.ui.theme.buttonBackground

/** The percentage of available height the background image should occupy */
private const val imageHeightPercent = 0.7f

/**
 * Percentage offset from top of image, marking top of available text space
 */
val textTopOffsetPercent = 0.362f

/**
 * Percentage height of the image which may be given to text
 */
val textAvailableHeightPercent = 0.19f

/** The weight of the bottom space - to make the weight sum add to 1 */
val textBottomOffsetPercent = 1f - textAvailableHeightPercent - textTopOffsetPercent


@Composable
fun TimeEditButton(
    iconId: Int,
    iconContentDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val alpha = if (isEnabled) 1f else 0.5f

    Row(
        modifier = modifier
            .background(
                color = buttonBackground.copy(alpha = alpha),
                shape = RoundedCornerShape(10.dp)
            )
            .alpha(alpha)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = iconId),
            contentDescription = iconContentDescription,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 30.sp
        )
    }
}

@Composable
fun CountDownScreen(viewModel: CountDownViewModel = viewModel()) {
    val secondsRemaining: Long? by viewModel.secondsRemaining.observeAsState(null)
    val minusEnabled: Boolean by viewModel.minusEnabled.observeAsState(false)

    val adjustButtonText = viewModel.adjustBySeconds.toString()
    val secondsRemainingText = if (secondsRemaining == null) "" else secondsRemaining.toString()

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                TimeEditButton(
                    R.drawable.icon_minus,
                    "Minus",
                    adjustButtonText,
                    modifier = Modifier.clickable(enabled = minusEnabled) {
                        viewModel.onMinusClick()
                    },
                    isEnabled = minusEnabled
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                TimeEditButton(
                    R.drawable.icon_plus,
                    "Plus",
                    adjustButtonText,
                    modifier = Modifier.clickable { viewModel.onPlusClick() },
                )
            }
        }

        // The actual timer display
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(imageHeightPercent)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = "A big clock",
                )
                PercentageVerticalPositioner(textTopOffsetPercent, textAvailableHeightPercent) {
                    Text(
                        text = secondsRemainingText,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PercentageVerticalPositioner(
    /** Percentage offset marking top of available space for [item] */
    topOffsetPercent: Float,
    /** Percentage height of the image which may be given to [item] */
    itemHeightPercent: Float,
    /** The composable to show in this position */
    item: @Composable () -> Unit
) {
    /** The weight of the bottom space - to make the weight sum add to 1 */
    val bottomOffsetPercent = 1f - itemHeightPercent - topOffsetPercent

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(topOffsetPercent))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(itemHeightPercent),
        ) {
            item()
        }
        Spacer(modifier = Modifier.weight(bottomOffsetPercent))
    }
}
