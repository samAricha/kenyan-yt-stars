package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistVideoDto(
    val maxresThumbnail: String,
    val title: String,
    val videoId: String
)