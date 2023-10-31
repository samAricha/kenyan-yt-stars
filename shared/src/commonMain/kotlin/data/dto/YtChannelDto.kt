package data.dto

import kotlinx.serialization.Serializable


@Serializable
data class YtChannelDto(
    val channelId: String,
    val highThumbnailUrl: String,
    val title: String,
    val description: String
)


@Serializable
data class YtChannelVideoDto(
    val channelId: String,
    val highThumbnailUrl: String,
    val title: String,
    val description: String
)