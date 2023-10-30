import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import data.model.YtChannelDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.detail_screen.DetailScreen
import presentation.home_screen.HomeViewModel
import widgets.AppBar


@Composable
fun KenyaYtAppTheme(
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ){
        Scaffold (
            topBar = {
                AppBar()
            },
           content = {
               content()
           }
        )
    }
}

@Composable
fun App() {
    KenyaYtAppTheme {

        Navigator(HomeScreen())

    }
}

class HomeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        HomeView(navigator = navigator)
    }
}


@Composable
fun HomeView(navigator: Navigator?){
    val birdsViewModel = getViewModel(Unit, viewModelFactory {
        HomeViewModel()
    })
    ChannelsPage(birdsViewModel)
}



@Composable
fun ChannelsPage(viewModel: HomeViewModel){
    val uiState by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(uiState.images.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement =  Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    items(uiState.images ){
                        ChannelImageCell(it)
                    }
                }
            )
        }
    }
}


@Composable
fun ChannelImageCell(image: YtChannelDto){
    val navigator = LocalNavigator.current

    KamelImage(
        asyncPainterResource(
            data = image.highThumbnailUrl
        ),
        contentDescription = image.title,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                if (navigator != null) {
                    navigator.push(DetailScreen(channel = image))
                }
            }
    )
}

expect fun getPlatformName(): String