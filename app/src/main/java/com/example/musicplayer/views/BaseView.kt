package com.example.musicplayer.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.layout.BottomBar
import com.example.musicplayer.components.layout.TopBar
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.view_models.PlayerViewModel

@Composable
fun BaseView(
    navController: NavController,
    playerViewModel: PlayerViewModel? = null,
    title: String? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if (title != null) {
                TopBar(navController, title)
            }
        },
        bottomBar = {
            BottomBar(navController, playerViewModel)
        }
    ) {
        innerPadding -> Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BaseViewPreview() {
    MusicPlayerTheme {
        BaseView(
            navController = rememberNavController(),
            title = "Title"
        ) {}
    }
}