package presentation.home_screen

import data.dto.YtChannelDto
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.apis.ApiUrl
import utils.apis.ApiUtils

data class HomeScreenUiState(
    val images: List<YtChannelDto> = emptyList(),
//    val selectedCategory: String? = null
)


class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _podcastUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState())
    val podcastUiState: StateFlow<HomeScreenUiState> = _podcastUiState.asStateFlow()

    private val _playlistersUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState())
    val playlistersUiState: StateFlow<HomeScreenUiState> = _playlistersUiState.asStateFlow()

    private val _comedyUiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState())
    val comedyUiState: StateFlow<HomeScreenUiState> = _comedyUiState.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing

    val channelsUrl = ApiUrl.GetChannelsUrl
    val completeChannelsUrl = ApiUtils.generateApiUrl(channelsUrl)




    private val httpClient : HttpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    init {
        updateImages()
    }

    override fun onCleared() {
        httpClient.close()
    }


    fun updateImages(){
        viewModelScope.launch {
            try {
                _isSyncing.value = true
                val images = getImages()
                _uiState.update {
                    it.copy(images = images)
                }
            }catch (e: Exception){

            } finally {
                _isSyncing.value = false
            }

        }
    }

    fun updatePodcastList(){
        try {
            _isSyncing.value = true
            viewModelScope.launch {
                val podcastLists = getPodcasts()
                _podcastUiState.update {
                    it.copy(images = podcastLists)
                }
                _uiState.update {
                    it.copy(images = _podcastUiState.value.images)
                }
            }

        }catch (e: Exception){

        } finally {
            _isSyncing.value = false
        }
    }

    fun updateComedyList(){
        try {
            _isSyncing.value = true
            viewModelScope.launch {
                val comedyList = getComedies()
                _comedyUiState.update {
                    it.copy(images = comedyList)
                }
                _uiState.update {
                    it.copy(images = _comedyUiState.value.images)
                }
            }

        }catch (e: Exception){

        } finally {
            _isSyncing.value = false
        }


    }

    fun updatePlaylistersList(){
        try {
            _isSyncing.value = true
            viewModelScope.launch {
                val playlistersList = getPlaylisters()
                _playlistersUiState.update {
                    it.copy(images = playlistersList)
                }
                _uiState.update {
                    it.copy(images = _playlistersUiState.value.images)
                }
            }

        }catch (e: Exception){

        } finally {
            _isSyncing.value = false
        }
    }


    private suspend fun getImages(): List<YtChannelDto> {
        val images = httpClient
            .get(completeChannelsUrl)
            .body<List<YtChannelDto>>()
        return images
    }

    private suspend fun getPodcasts(): List<YtChannelDto> {
        val podcastList = httpClient
            .get(completeChannelsUrl)
            .body<List<YtChannelDto>>()
        return podcastList
    }

    private suspend fun getComedies(): List<YtChannelDto> {
        val comedyList = httpClient
            .get(completeChannelsUrl)
            .body<List<YtChannelDto>>()
        return comedyList
    }

    private suspend fun getPlaylisters(): List<YtChannelDto> {
        val playlistersList = httpClient
            .get(completeChannelsUrl)
            .body<List<YtChannelDto>>()
        return playlistersList
    }


}