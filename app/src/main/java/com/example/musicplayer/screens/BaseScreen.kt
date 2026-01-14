package com.example.musicplayer.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.NavPanel
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun BaseScreen(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavPanel(navController)
        }
    ) {
        innerPadding -> Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BaseScreenPreview() {
    MusicPlayerTheme {
        BaseScreen(
            navController = rememberNavController()
        ) {}
    }
}