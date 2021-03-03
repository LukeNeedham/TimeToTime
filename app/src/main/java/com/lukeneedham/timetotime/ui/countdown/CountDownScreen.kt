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
package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lukeneedham.timetotime.R
import com.lukeneedham.timetotime.ui.countdown.composables.Clock
import com.lukeneedham.timetotime.ui.countdown.composables.FinishedMessage
import com.lukeneedham.timetotime.ui.countdown.composables.TimeEditButton
import com.lukeneedham.timetotime.ui.theme.mutedText

@Composable
fun CountDownScreen(viewModel: CountDownViewModel = viewModel()) {
    val secondsRemaining: Long? by viewModel.secondsRemaining.observeAsState(null)
    val minusEnabled: Boolean by viewModel.minusEnabled.observeAsState(false)

    val adjustButtonText = viewModel.adjustBySeconds.toString()

    val showPinchHint: Boolean by viewModel.showPinchHint.observeAsState(true)
    val pinchHintTargetAlpha = if (showPinchHint) 1f else 0f
    val pinchHintAlpha by animateFloatAsState(pinchHintTargetAlpha, animationSpec = TweenSpec(1500))

    val isFinished = secondsRemaining == 0L

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Crossfade(targetState = isFinished, animationSpec = TweenSpec(500)) { isFinished ->
                if (isFinished) {
                    FinishedMessage(onClick = { viewModel.restart() })
                } else {
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
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(pinchHintAlpha)
        ) {
            Text(
                text = "Pinch the clock...",
                color = mutedText,
                fontSize = 20.sp
            )
        }

        Clock(secondsRemaining, isFinished) {
            viewModel.onClockPinch()
        }
    }
}
