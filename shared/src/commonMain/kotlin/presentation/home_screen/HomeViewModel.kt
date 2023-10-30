package presentation.home_screen

import data.model.YtChannelDto
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

data class HomeScreenUiState(
    val images: List<YtChannelDto> = emptyList(),
//    val selectedCategory: String? = null
)


class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

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
            val images = getImages()

            _uiState.update {
                it.copy(images = images)
            }
        }
    }


    private suspend fun getImages(): List<YtChannelDto> {
        val images = httpClient
            .get("https://c9e5-197-183-255-76.ngrok-free.app/api/getChannel")
            .body<List<YtChannelDto>>()

        return images
    }


}