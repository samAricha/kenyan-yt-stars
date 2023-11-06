package presentation.video_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
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
import data.dto.ChannelVideoDto
import data.dto.PlaylistVideoDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.parametersOf
import org.koin.compose.rememberKoinInject
import org.koin.core.parameter.parametersOf
import presentation.video_player_screen.VideoPlayerScreen
import ui.widgets.CircularProgressIndicator

class VideoListScreen(val channelId : String) : Screen {
    @Composable
    override fun Content() {

        println("video list screen")
        val videoListViewModel = getViewModel(Unit, viewModelFactory {
            VideoListViewModel(channelId = channelId)
        })
//        val videoListViewModel = rememberKoinInject<VideoListViewModel> { parametersOf(channelId) }


        val isSyncing by videoListViewModel.isSyncing.collectAsState()


        videoListViewModel.updateChannelVideos()

        val uiState by videoListViewModel.uiState.collectAsState()
        println("debugging======>>>>${uiState}")
        val navigator = LocalNavigator.current
        val videoBaseUrl = "https://www.youtube.com/watch?v="


        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box {
                    LazyColumn {
//            println("Lazy column channel vidoe id ====> ${uiState.playlistVideos[0]}")
                        items(uiState.channelVideos) { channelVideo ->
                            VideoListItem(
                                channelVideo = channelVideo,
                                onItemClick = {
                                    if (navigator != null) {
                                        println("channel vidoe id ====> ${channelVideo.videoId}")
                                        navigator.push(
                                            VideoPlayerScreen(
                                                modifier = Modifier,
                                                videoId = channelVideo.videoId,
                                                url = videoBaseUrl + channelVideo.videoId
                                            )
                                        )
                                    }
                                })
                        }

                    }
                }

                if (isSyncing) {
                    CircularProgressIndicator()

                }
            }
            }

    }

    @Composable
    fun VideoListItem(
        channelVideo: ChannelVideoDto,
        onItemClick: (ChannelVideoDto) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onItemClick(channelVideo) },
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                KamelImage(
                    asyncPainterResource(data = channelVideo.highQualityThumbnail),
                    contentDescription = channelVideo.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(16f/9f)// Adjust the width as needed (e.g., 50% of the parent width)
                )

                Text(
                    text = channelVideo.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

    }
}