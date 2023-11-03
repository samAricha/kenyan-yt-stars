package presentation.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import data.dto.YtChannelDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.detail_screen.DetailScreen
import ui.widgets.CategoryItem
import ui.widgets.CircularProgressIndicator


class HomeScreen : Screen {
    @Composable
    override fun Content() {

        HomeView()

    }
}


@Composable
fun HomeView(){
    val homeViewModel = getViewModel(Unit, viewModelFactory {
        HomeViewModel()
    })
    val isSyncing by homeViewModel.isSyncing.collectAsState()

    var selectedCategory by remember { mutableStateOf(Utils.channelCategories[0]) }


    val collections: HomeScreenUiState = when (selectedCategory) {
        Utils.channelCategories[0] -> {
            homeViewModel.updatePodcastList()
            val podcastsState by homeViewModel.podcastUiState.collectAsState()
            podcastsState
        }
        Utils.channelCategories[1] -> {

            homeViewModel.updatePlaylistersList()
            val playlistersState by homeViewModel.playlistersUiState.collectAsState()
            playlistersState
        }
        Utils.channelCategories[2] -> {
            homeViewModel.updateComedyList()
            val comediesState by homeViewModel.comedyUiState.collectAsState()
            comediesState
        }
        else -> HomeScreenUiState() // Handle other categories as needed
    }


    Scaffold {
        Column(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            Box {
                Column {
                    LazyRow(Modifier.padding(vertical = 10.dp,)) {
                        item{
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        items(Utils.channelCategories) { category: Category ->
                            CategoryItem(
                                title = category.title,
                                selected = category == selectedCategory
                            ) {
                                selectedCategory = category
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                    //this is the main content containing our list
                    ChannelsPage(collections)
                }
                if (isSyncing) {
                    CircularProgressIndicator()
                }
            }
        }
    }



}



@Composable
fun ChannelsPage(uiState: HomeScreenUiState){
//    val uiState by viewModel.uiState.collectAsState()

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