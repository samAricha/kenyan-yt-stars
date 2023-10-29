package data.model

import kotlinx.serialization.Serializable


@Serializable
data class YtChannelDto(
    val channelId: String,
    val highThumbnailUrl: String,
    val title: String
)