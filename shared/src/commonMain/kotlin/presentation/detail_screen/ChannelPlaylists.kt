package presentation.detail_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.dto.ChannelPlaylistDto
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ChannelPlaylistList(
    channelPlaylists: List<ChannelPlaylistDto>,
    onItemClick: (ChannelPlaylistDto) -> Unit
) {
    println("channel playlists>>>>${channelPlaylists}")
    Column {
        LazyColumn {
            item {
                Text("Channel Playlists")
                Spacer(modifier = Modifier.height(15.dp))
            }
            items(channelPlaylists) { playlist ->
                ChannelPlaylistItem(playlist = playlist, onItemClick = onItemClick)
            }
        }
    }



}

@Composable
fun ChannelPlaylistItem(
    playlist: ChannelPlaylistDto,
    onItemClick: (ChannelPlaylistDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(playlist) },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KamelImage(
                asyncPainterResource(data = playlist.maxresThumbnail),
                contentDescription = playlist.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(16f/9f)// Adjust the width as needed (e.g., 50% of the parent width)
            )

            Text(
                text = playlist.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

}