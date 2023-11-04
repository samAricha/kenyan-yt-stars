package presentation.video_list_screen

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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable


data class VideoListScreenUiState(
    val playlistVideos: List<PlaylistVideoDto> = emptyList(),
//    val selectedCategory: String? = null
)

@Serializable
data class VideoListRequest(val playlistId: String)


class VideoListViewModel(val playlistId: String) : ViewModel(){

    private val _uiState = MutableStateFlow<VideoListScreenUiState>(VideoListScreenUiState())
    val uiState = _uiState.asStateFlow()


    private val httpClient : HttpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    init {
        updatePlaylistVideos()
    }


    fun updatePlaylistVideos(){
        viewModelScope.launch {
            val playlistVideos = getPlaylistVideos(playlistId = playlistId)

            _uiState.update {
                it.copy(playlistVideos = playlistVideos)
            }
        }
    }

    private suspend fun getPlaylistVideos(playlistId: String) : List<PlaylistVideoDto>{
        val videoListsUrl = "https://c7f6-41-89-128-5.ngrok-free.app/api/playlists/getPlaylistVideos"
        val playlistVideosRequest = VideoListRequest(playlistId)

        val response  =  withContext(Dispatchers.IO) {
            httpClient.post(videoListsUrl) {
                contentType(ContentType.Application.Json)
                setBody(playlistVideosRequest)
            }.body<List<PlaylistVideoDto>>()
        }


        println("inside gpv------->${response}")
        return response

    }

}