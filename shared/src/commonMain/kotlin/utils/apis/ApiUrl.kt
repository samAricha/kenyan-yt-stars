package utils.apis

sealed class ApiUrl {
    data class GetUserDataUrl(val userId: Int) : ApiUrl()
    data class GetPostUrl(val postId: Int) : ApiUrl()
    object GetChannelsUrl : ApiUrl()
    object GetPodcastsUrl : ApiUrl()
    object GetComediesUrl : ApiUrl()
    object GetPlaylistersUrl : ApiUrl()
    object GetChannelDetailsUrl : ApiUrl()
    object GetChannelVideosUrl : ApiUrl()
    object GetPlaylistVideosUrl : ApiUrl()
    data class CustomUrl(val path: String) : ApiUrl()
}
