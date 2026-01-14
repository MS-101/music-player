package com.example.musicplayer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.navigation.Screen

@Composable
fun SongsScreen(
    navController: NavController
) {
    BaseScreen(navController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Songs",
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                {
                    navController.navigate(
                        Screen.SongDetail.passId(1)
                    )
                }
            ) {
                Text("Song Detail")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    SongsScreen(
        rememberNavController()
    )
}