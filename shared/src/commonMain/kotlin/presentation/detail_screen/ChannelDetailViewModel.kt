package presentation.detail_screen

import data.dto.ChannelPlaylistDto
import data.dto.YtChannelDto
import data.dto.YtChannelVideoDto
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
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
import utils.apis.ApiUrl
import utils.apis.ApiUtils


data class DetailScreenUiState(
    val playlists: List<ChannelPlaylistDto> = emptyList(),
//    val selectedCategory: String? = null
)

@Serializable
data class ChannelPlaylistRequest(val channelId: String)



class ChannelDetailViewModel(private val channelId: String): ViewModel() {

    private val _uiState = MutableStateFlow<DetailScreenUiState>(DetailScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _isChannelDetailsSyncing = MutableStateFlow(false)
    val isChannelDetailsSyncing: StateFlow<Boolean> = _isChannelDetailsSyncing

    val channelDetailsUrl = ApiUrl.GetChannelDetailsUrl
    val completeChannelDetailsUrl = ApiUtils.generateApiUrl(channelDetailsUrl)

    private val httpClient : HttpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    init {
        updateChannelPlaylist()
    }

    fun updateChannelPlaylist(){
        viewModelScope.launch {
            try {
                _isChannelDetailsSyncing.value = true

                val channelPlaylists = getChannelPlaylists(channelId = channelId)
                _uiState.update {
                    it.copy(playlists = channelPlaylists)
                }
            }catch (e :Exception){
                println("<<<<${e.message}>>>")
            }finally {
                _isChannelDetailsSyncing.value = false
            }

        }
    }



    private suspend fun getChannelVideos(): List<YtChannelVideoDto> {
        val channelVideos = httpClient
            .get("https://5de6-41-81-8-47.ngrok-free.app/api/getChannel")
            .body<List<YtChannelVideoDto>>()

        return channelVideos
    }

    private suspend fun getChannelPlaylists(channelId: String): List<ChannelPlaylistDto> {

        val playlistUrl = completeChannelDetailsUrl
        val channelPlaylistRequest = ChannelPlaylistRequest(channelId)
        val response  =  withContext(Dispatchers.IO) {
            httpClient.post(playlistUrl) {
                contentType(ContentType.Application.Json)
                setBody(channelPlaylistRequest)
            }.body<List<ChannelPlaylistDto>>()
        }

        println("inside vm------->$response")
        return response
    }


}