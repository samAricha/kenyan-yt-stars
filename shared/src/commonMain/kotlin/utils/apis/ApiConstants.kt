package utils.apis

object ApiConstants {
    const val BASE_URL = "https://adb6-41-89-128-5.ngrok-free.app/api/"
    const val ALL_CHANNELS_ENDPOINT = "channels/getAllChannel"
    const val ALL_PODCASTS_ENDPOINT = "channels/getPodcastChannels"
    const val ALL_COMEDIANS_ENDPOINT = "channels/getComedyChannels"
    const val ALL_PLAYLISTERS_ENDPOINT = "channels/getPlaylistChannels"
    const val CHANNEL_VIDEOS_ENDPOINT = "channels/getChannelVideos"
    const val CHANNEL_DETAILS_ENDPOINT = "playlists/getChannelPlaylists"
    const val PLAYLIST_VIDEOS_ENDPOINT = "playlists/getPlaylistVideos"
    const val USER_DATA_ENDPOINT = "user/{userId}"
    const val POST_ENDPOINT = "post/{postId}"
    const val LIST_OF_POSTS_ENDPOINT = "posts"
}
