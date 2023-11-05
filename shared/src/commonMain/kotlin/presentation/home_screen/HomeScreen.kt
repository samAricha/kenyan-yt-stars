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
import data.dto.YtChannelDto
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.rememberKoinInject
import presentation.detail_screen.DetailScreen
import presentation.video_list_screen.VideoListScreen
import ui.widgets.CategoryItem
import ui.widgets.CircularProgressIndicator



class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val homeViewModel = getViewModel(Unit, viewModelFactory {
            HomeViewModel()
        })
//        HomeView(homeViewModel)
        HomeView()
    }
}


@Composable
fun HomeView(){
    val homeViewModel = rememberKoinInject<HomeViewModel>()
    val isSyncing by homeViewModel.isSyncing.collectAsState()



    var selectedCategory by remember { mutableStateOf(Utils.channelCategories[0]) }
//    homeViewModel.updateImages()
    val uiState by homeViewModel.uiState.collectAsState()


    val collections: HomeScreenUiState = when (selectedCategory) {
        Utils.channelCategories[0] -> {
            homeViewModel.updateImages()
            val allChannelsUiState by homeViewModel.uiState.collectAsState()
            allChannelsUiState
        }
        Utils.channelCategories[1] -> {
            homeViewModel.updatePodcastList()
            val podcastsState by homeViewModel.podcastUiState.collectAsState()
            podcastsState
        }
        Utils.channelCategories[2] -> {
            homeViewModel.updatePlaylistersList()
            val playlistersState by homeViewModel.playlistersUiState.collectAsState()
            playlistersState
        }
        Utils.channelCategories[3] -> {
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
            Box (
                modifier = Modifier.fillMaxSize()
            ){
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
                    println("+++++++++>${ collections.images }<+++++++++")
                    ChannelsPage(uiState, selectedCategory)
                }
                if (isSyncing) {
                    CircularProgressIndicator()
                }
            }
        }
    }



}



@Composable
fun ChannelsPage(uiState: HomeScreenUiState, selectedCategory: Category){

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        println(">>>>>>>>>>>>${selectedCategory}----------")

        AnimatedVisibility(uiState.images.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement =  Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    items(uiState.images ){
                        ChannelImageCell(it, selectedCategory)
                    }
                }
            )
        }
    }
}


@Composable
fun ChannelImageCell(image: YtChannelDto, selectedCategory: Category){
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
                    if (selectedCategory.id != 2){
                        println(">>>>>>selected-category = ${selectedCategory.id}")
                        navigator.push(VideoListScreen(channelId = image.channelId))
                    }else{
                        println(">>>>>>selected-category = ${selectedCategory.id}")
                        navigator.push(DetailScreen(channel = image,selectedCategory =  selectedCategory))
                    }
                }

            }
    )
}