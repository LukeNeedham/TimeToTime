package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
