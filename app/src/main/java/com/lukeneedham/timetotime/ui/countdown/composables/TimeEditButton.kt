package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
