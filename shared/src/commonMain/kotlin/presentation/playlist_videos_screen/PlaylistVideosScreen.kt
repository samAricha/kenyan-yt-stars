package presentation.playlist_videos_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import data.dto.ChannelPlaylistDto
import data.dto.PlaylistVideoDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.detail_screen.ChannelDetailViewModel
import presentation.detail_screen.ChannelPlaylistItem
import presentation.video_player_screen.VideoPlayerScreen

class PlaylistVideosScreen(val playlistId : String) : Screen {
    @Composable
    override fun Content() {

        val playlistVideosViewModel = getViewModel(Unit, viewModelFactory {
            PlaylistVideosViewModel(playlistId = playlistId)
        })
        val uiState by playlistVideosViewModel.uiState.collectAsState()
        val navigator = LocalNavigator.current
        val videoBaseUrl = "https://www.youtube.com/watch?v="

        LazyColumn {

            items(uiState.playlistVideos) { playlistVideo ->
                PlaylistVideosItem(
                    playlistVideo = playlistVideo,
                    onItemClick = {
                        if (navigator != null) {
                            navigator.push(
                                VideoPlayerScreen(
                                    modifier = Modifier,
                                    url = videoBaseUrl + playlistVideo.videoId
                                )
                            )
                        }
                    })
            }

        }

    }

    @Composable
    fun PlaylistVideosItem(
        playlistVideo: PlaylistVideoDto,
        onItemClick: (PlaylistVideoDto) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onItemClick(playlistVideo) },
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                KamelImage(
                    asyncPainterResource(data = playlistVideo.maxresThumbnail),
                    contentDescription = playlistVideo.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(16f/9f)// Adjust the width as needed (e.g., 50% of the parent width)
                )

                Text(
                    text = playlistVideo.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

    }
}