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

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lukeneedham.timetotime.ui.theme.buttonBackground

@Composable
fun TimeEditButton(
    iconId: Int,
    iconContentDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val targetAlpha = if (isEnabled) 1f else 0.5f
    val alpha by animateFloatAsState(targetAlpha, animationSpec = TweenSpec(200))

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
