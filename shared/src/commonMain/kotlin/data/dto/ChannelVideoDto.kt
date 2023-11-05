package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChannelVideoDto(
    val highQualityThumbnail: String,
    val title: String,
    val videoId: String
)