package presentation.detail_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import data.model.YtChannelDto
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class DetailScreen(private val channel: YtChannelDto) : Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {

            KamelImage(
                asyncPainterResource(
                    data = channel.highThumbnailUrl
                ),
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
}