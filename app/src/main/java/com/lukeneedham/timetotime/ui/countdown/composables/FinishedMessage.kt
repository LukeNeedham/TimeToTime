package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.lukeneedham.timetotime.ui.theme.mutedText

@Composable
fun FinishedMessage(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Text(
            text = "Have a fantastic day!",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Restart?",
            fontSize = 20.sp,
            color = mutedText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
