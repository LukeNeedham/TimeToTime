/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lukeneedham.timetotime.ui.countdown.composables

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
