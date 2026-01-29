package com.example.musicplayer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.media3.common.Player
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.models.Song
import com.example.musicplayer.navigation.View
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.view_models.PlayerState
import com.example.musicplayer.view_models.PlayerViewModel

@Composable
fun PlayerPanel(
    navController: NavController,
    viewModel: PlayerViewModel
) {
    PlayerPanelContent(
        navController = navController,
        viewModel = viewModel,
        state = viewModel.getState()
    )
}

@Composable
fun PlayerPanelContent(
    navController: NavController,
    viewModel: PlayerViewModel? = null,
    state: PlayerState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(75.dp)
            .background(Color.Black).border(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier.weight(1f).clickable {
                navController.navigate(View.Player.route)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.music_note),
                contentDescription = "Song Image",
                modifier = Modifier.fillMaxHeight().background(Color.White)
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp)
            ) {
                Text(
                    text = state.song?.title ?: "N/A",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth().basicMarquee()
                )

                Text(
                    text = state.song?.artist ?: "N/A",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (state.isPlaying) {
            Image(
                painter = painterResource(id = R.drawable.pause),
                contentDescription = "Pause Song",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.padding(10.dp).clickable {
                    viewModel?.pauseSong()
                }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Resume Song",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.padding(10.dp).clickable {
                    viewModel?.resumeSong()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerPanelPreview() {
    MusicPlayerTheme {
        PlayerPanelContent(
            navController = rememberNavController(),
            state = PlayerState(
                Song(
                    0,
                    "".toUri(),
                    "Title",
                    "Artist",
                    38000
                ),
                10000,
                isPlaying = true,
                hasPreviousSong = true,
                hasNextSong = true,
                shuffleModeEnabled = false,
                repeatMode = Player.REPEAT_MODE_OFF
            ),
        )
    }
}
