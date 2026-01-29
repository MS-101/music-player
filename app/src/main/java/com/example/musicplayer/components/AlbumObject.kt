package com.example.musicplayer.components

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.models.Album
import com.example.musicplayer.navigation.View
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun AlbumObject(
    navController: NavController,
    album: Album
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(5.dp)
            .clickable {
                navController.navigate(
                    View.AlbumDetail.passId(album.id)
                )
            }
    ) {
        Row {
            if (album.thumbnail != null) {
                Image(
                    bitmap = album.thumbnail.asImageBitmap(),
                    contentDescription = "Album Image",
                    modifier = Modifier.width(100.dp).fillMaxHeight()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.album),
                    contentDescription = "Album Image",
                    modifier = Modifier.width(100.dp).fillMaxHeight()
                )
            }

            Column {
                Text(
                    text = album.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(5.dp)
                        .basicMarquee()
                )
                Text(
                    text = album.artist,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumObjectPreview() {
    MusicPlayerTheme {
        AlbumObject(
            rememberNavController(),
            Album(0, "".toUri(), "Title", "Artist", emptyList())
        )
    }
}