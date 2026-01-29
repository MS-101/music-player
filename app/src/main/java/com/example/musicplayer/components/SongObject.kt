import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.musicplayer.R
import com.example.musicplayer.models.Song
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.utils.toMmSs

@Composable
fun SongObject(
    song: Song,
    onSongClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth().height(200.dp)
            .padding(24.dp)
            .clickable {
                onSongClick()
            }
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.music_note),
                contentDescription = "Song Image",
                modifier = Modifier.width(100.dp).fillMaxHeight()
            )

            Column {
                Text(
                    text = song.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(12.dp)
                        .basicMarquee()
                )
                Text(
                    text = song.artist,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = song.duration.toMmSs(),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongObjectPreview() {
    MusicPlayerTheme {
        SongObject(
            Song(0,"".toUri(),"Title","Artist",38000)
        ) {}
    }
}