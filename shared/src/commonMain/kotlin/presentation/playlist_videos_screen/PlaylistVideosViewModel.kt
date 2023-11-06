package presentation.playlist_videos_screen

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
import presentation.video_list_screen.VideoListRequest
import utils.apis.ApiUrl
import utils.apis.ApiUtils


data class PlaylistVideosScreenUiState(
    val playlistVideos: List<PlaylistVideoDto> = emptyList(),
//    val selectedCategory: String? = null
)

@Serializable
data class PlaylistVideosRequest(val playlistId: String)


class PlaylistVideosViewModel(val playlistId: String) : ViewModel(){

    private val _uiState = MutableStateFlow<PlaylistVideosScreenUiState>(PlaylistVideosScreenUiState())
    val uiState = _uiState.asStateFlow()

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
        updatePlaylistVideos()
    }


    fun updatePlaylistVideos(){
        println("inside updatePlaylistVideos part1,,,,,,,,")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isSyncing.value = true
                val playlistVideos = getPlaylistVideos(playlistId = playlistId)
                println("playlist videos ====>>>>> ${playlistVideos}")
                _uiState.update {
                    it.copy(playlistVideos = playlistVideos)
                }
                println("uistate videos ====>>>>> ${uiState.value}")
            }catch (e: Exception){
                println("Exception =====> ${e}")
            }finally {
                println("finally =====> ${uiState.value.playlistVideos}")
                _isSyncing.value = false
            }

        }
    }

    private suspend fun getPlaylistVideos(
        playlistId: String
    ) : List<PlaylistVideoDto>{
        println("Getting Playlist Videos")
        val videoListsUrl = completePlaylistVideosUrl
        val playlistVideosRequest = PlaylistVideosRequest("PLJcxGk1ibvxcY0yuBniKfaS7QlV-8JPPX")

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