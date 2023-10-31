package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChannelPlaylistDto(
    val maxresThumbnail: String,
    val playlistId: String,
    val title: String
)