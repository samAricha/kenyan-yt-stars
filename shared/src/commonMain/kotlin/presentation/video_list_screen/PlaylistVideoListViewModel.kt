package presentation.video_list_screen

import data.dto.ChannelVideoDto
import data.dto.PlaylistVideoDto
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import presentation.playlist_videos_screen.PlaylistVideosRequest
import utils.apis.ApiUrl
import utils.apis.ApiUtils


data class PlaylistVideosScreenUiState(
    val playlistVideos: List<PlaylistVideoDto> = emptyList(),
//    val selectedCategory: String? = null
)

@Serializable
data class PlaylistVideoListRequest(val playlistId: String)


class PlaylistVideoListViewModel(val playlistId: String) : ViewModel(){

    private val _uiState = MutableStateFlow<PlaylistVideosScreenUiState>(PlaylistVideosScreenUiState())
    val uiState = _uiState.asStateFlow()

    val channelVideosUrl = ApiUrl.GetChannelVideosUrl
    val completeChannelVideosUrl = ApiUtils.generateApiUrl(channelVideosUrl)

    val playlistVideosUrl = ApiUrl.GetPlaylistVideosUrl
    val completePlaylistVideosUrl = ApiUtils.generateApiUrl(playlistVideosUrl)

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing




    private val httpClient : HttpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    init {
        _isSyncing.value = true
        updateChannelVideos()
        println("Video list view model init function")
    }

    override fun onCleared() {
        httpClient.close()
    }



    fun updateChannelVideos(){
        println("inside updateChannelVideos part1,,,,,,,,")

        viewModelScope.launch(Dispatchers.IO) {
            println("inside updatePlaylistVideos,,,,,,,,")

            try {
                _isSyncing.value = true
                val channelVideos = getChannelVideos(playlistId = playlistId)
                println("vidoes view model videos,,,,,,,,${channelVideos}")
                _uiState.update {
                    it.copy(playlistVideos = channelVideos)
                }
            }catch (e:Exception){
                println("Exception =====> ${e}")
            }finally {
                println("finally =====> ${uiState.value.playlistVideos}")
                _isSyncing.value = false

            }

        }
    }

    private suspend fun getChannelVideos(playlistId: String) : List<PlaylistVideoDto>{
        println("inside channel videos--=======>>>>")

        val playlistVideosUrl = completePlaylistVideosUrl
        val playlistVideosRequest = PlaylistVideoListRequest(playlistId)

        val response  =  withContext(Dispatchers.IO) {
            httpClient.post(playlistVideosUrl) {
                contentType(ContentType.Application.Json)
                setBody(playlistVideosRequest)
            }.body<List<PlaylistVideoDto>>()
        }


        println("inside gottedChannelVideos------->${response}")
        return response

    }

}