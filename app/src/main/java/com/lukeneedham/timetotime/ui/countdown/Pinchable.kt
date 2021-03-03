package com.lukeneedham.timetotime.ui.countdown

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun Pinchable(child: @Composable (scale: Float) -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    Box(
        Modifier
            .transformable(state = state)
            .fillMaxSize()
    ) {
        child(scale)
    }
}
