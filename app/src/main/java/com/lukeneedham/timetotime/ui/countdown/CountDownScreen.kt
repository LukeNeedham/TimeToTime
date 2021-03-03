package com.lukeneedham.timetotime

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lukeneedham.timetotime.ui.countdown.Clock
import com.lukeneedham.timetotime.ui.countdown.CountDownViewModel
import com.lukeneedham.timetotime.ui.countdown.FinishedMessage
import com.lukeneedham.timetotime.ui.countdown.TimeEditButton
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
