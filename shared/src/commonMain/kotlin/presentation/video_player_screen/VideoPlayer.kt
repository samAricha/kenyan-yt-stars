package presentation.video_player_screen


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen


data class VideoPlayerScreen(private val modifier: Modifier, private val url: String) : Screen {
    @Composable
    override fun Content() {
//        VideoPlayer(modifier, url)
        TrialVideoPlayer(modifier, "0yOg5LaIoGQ")
    }
}

@Composable
expect fun VideoPlayer(modifier: Modifier, url: String)


@Composable
expect fun TrialVideoPlayer(modifier: Modifier, videoId: String)