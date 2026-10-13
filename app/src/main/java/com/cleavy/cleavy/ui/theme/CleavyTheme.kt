package com.cleavy.cleavy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CleavyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}