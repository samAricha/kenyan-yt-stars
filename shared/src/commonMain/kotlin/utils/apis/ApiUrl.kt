package utils.apis

sealed class ApiUrl {
    data class GetUserDataUrl(val userId: Int) : ApiUrl()
    data class GetPostUrl(val postId: Int) : ApiUrl()
    object GetChannelsUrl : ApiUrl()
    data class CustomUrl(val path: String) : ApiUrl()
}
