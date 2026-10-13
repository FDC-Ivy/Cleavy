package com.cleavy.cleavy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cleavy.cleavy.ui.theme.CleavyTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleavyTheme {
                // to not overlap with status bar and gesture bar/nav buttons of the screen
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .safeDrawingPadding()
                ) {
                    CleavyApp()
                }
            }
        }
    }
}