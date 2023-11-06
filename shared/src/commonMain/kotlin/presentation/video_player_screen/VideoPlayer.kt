package presentation.video_player_screen


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen


data class VideoPlayerScreen(
    private val modifier: Modifier,
    private val url: String,
    private val videoId: String
) : Screen {
    @Composable
    override fun Content() {
//        VideoPlayer(modifier, url)
        TrialVideoPlayer(modifier, videoId)
    }
}

@Composable
expect fun VideoPlayer(modifier: Modifier, url: String)


@Composable
expect fun TrialVideoPlayer(modifier: Modifier, videoId: String)