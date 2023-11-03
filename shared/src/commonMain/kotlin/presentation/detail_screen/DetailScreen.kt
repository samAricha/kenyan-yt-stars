package presentation.detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import data.dto.YtChannelDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.home_screen.HomeViewModel
import presentation.playlist_videos_screen.PlaylistVideosScreen
import presentation.video_player_screen.VideoPlayerScreen


data class DetailScreen(private val channel: YtChannelDto) : Screen {

    @Composable
    override fun Content() {

        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            val channelDetailViewModel = getViewModel(Unit, viewModelFactory {
                ChannelDetailViewModel(channelId = channel.channelId)
            })

            val uiState by channelDetailViewModel.uiState.collectAsState()
            val navigator = LocalNavigator.current
            val channelPlaylists = uiState.playlists

            println("detail-screen: ${uiState}")


            LazyColumn {
                item {
                    Box {
                        KamelImage(
                            asyncPainterResource(data = channel.highThumbnailUrl),
                            contentDescription = channel.title,
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                        Text(
                            text = channel.title,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Channel Playlists",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 24.sp, // Increase the font size to 24sp
                        color = Color.Black // Set the text color as needed
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
                items(uiState.playlists) { playlist ->
                    ChannelPlaylistItem(
                        playlist = playlist,
                        onItemClick = {
                            if (navigator != null) {
//                                navigator.push(
//                                    VideoPlayerScreen(
//                                        modifier = Modifier,
//                                        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//                                    )
//                                )
                                navigator.push(
                                    PlaylistVideosScreen(
                                        playlistId = playlist.playlistId
                                    )
                                )
                            }
                        })
                }

            }


        }

    }
}