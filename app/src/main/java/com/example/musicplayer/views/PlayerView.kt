package com.example.musicplayer.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
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
import com.example.musicplayer.utils.toMmSs
import com.example.musicplayer.view_models.PlayerState
import com.example.musicplayer.view_models.PlayerViewModel

@Composable
fun PlayerView(
    navController: NavController,
    viewModel: PlayerViewModel,

) {
    PlayerViewContent(
        navController = navController,
        viewModel = viewModel,
        state = viewModel.getState()
    )
}

@Composable
fun PlayerViewContent(
    navController: NavController,
    viewModel: PlayerViewModel? = null,
    state: PlayerState
) {
    BaseView(navController) {
        Column (
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
            if (state.song?.thumbnail != null) {
                Image(
                    bitmap = state.song.thumbnail.asImageBitmap(),
                    contentDescription = "Song Image",
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.song),
                    contentDescription = "Song Image",
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text(
                    text = state.song?.title ?: "N/A",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth().basicMarquee()
                )

                Text(
                    text = state.song?.artist ?: "N/A",
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(70.dp).padding(vertical = 10.dp)
            ) {

                Text(
                    state.songPosition.toMmSs(),
                    fontSize = 20.sp,
                )

                Slider(
                    value =
                        if (state.song != null)
                            state.songPosition.toFloat() / state.song.duration.toFloat()
                        else 0F,
                    onValueChange = {
                        if (state.song != null)
                            viewModel?.seekSong((it * state.song.duration).toLong())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Red,
                        activeTrackColor = Color.Red,
                        inactiveTrackColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    state.song?.duration?.toMmSs() ?: "00:00",
                    fontSize = 20.sp,
                )
            }

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().height(70.dp).padding(vertical = 10.dp)
            ) {
                if (state.shuffleModeEnabled) {
                    Image(
                        painter = painterResource(id = R.drawable.shuffle),
                        contentDescription = "Shuffle Queue (Enabled)",
                        colorFilter = ColorFilter.tint(Color.Red),
                        modifier = Modifier.clickable {
                            viewModel?.setShuffleMode(false)
                        }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.shuffle),
                        contentDescription = "Shuffle Queue (Disabled)",
                        colorFilter = ColorFilter.tint(Color.Gray),
                        modifier = Modifier.clickable {
                            viewModel?.setShuffleMode(true)
                        }
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.step_backward),
                    contentDescription = "Previous Song",
                    colorFilter = ColorFilter.tint(
                        if (state.hasPreviousSong) Color.Black else Color.Gray
                    ),
                    modifier = Modifier.clickable {
                        if (state.hasPreviousSong)
                            viewModel?.playPreviousSong()
                    }
                )

                if (state.isPlaying) {
                    Image(
                        painter = painterResource(id = R.drawable.pause),
                        contentDescription = "Pause Song",
                        modifier = Modifier.clickable {
                            viewModel?.pauseSong()
                        }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Resume Song",
                        modifier = Modifier.clickable {
                            viewModel?.resumeSong()
                        }
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.step_forward),
                    contentDescription = "Next Song",
                    colorFilter = ColorFilter.tint(
                        if (state.hasNextSong) Color.Black else Color.Gray
                    ),
                    modifier = Modifier.clickable{
                        if (state.hasNextSong)
                            viewModel?.playNextSong()
                    }
                )

                when (state.repeatMode) {
                    Player.REPEAT_MODE_OFF ->
                        Image(
                            painter = painterResource(id = R.drawable.repeat),
                            contentDescription = "Repeat Queue",
                            colorFilter = ColorFilter.tint(Color.Gray),
                            modifier = Modifier.clickable {
                                viewModel?.setRepeatMode(Player.REPEAT_MODE_ALL)
                            }
                        )

                    Player.REPEAT_MODE_ONE ->
                        Image(
                            painter = painterResource(id = R.drawable.repeat_one),
                            contentDescription = "Repeat Queue",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier.clickable {
                                viewModel?.setRepeatMode(Player.REPEAT_MODE_OFF)
                            }
                        )

                    Player.REPEAT_MODE_ALL ->
                        Image(
                            painter = painterResource(id = R.drawable.repeat),
                            contentDescription = "Repeat Queue",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier.clickable {
                                viewModel?.setRepeatMode(Player.REPEAT_MODE_ONE)
                            }
                        )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerViewPreview() {
    PlayerViewContent(
        rememberNavController(),
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