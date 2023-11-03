package utils.apis

object ApiUtils {
    fun generateApiUrl(apiUrl: ApiUrl): String {
        return when (apiUrl) {
            is ApiUrl.GetUserDataUrl -> {
                ApiConstants.BASE_URL + ApiConstants.USER_DATA_ENDPOINT.replace("{userId}", apiUrl.userId.toString())
            }
            is ApiUrl.GetPostUrl -> {
                ApiConstants.BASE_URL + ApiConstants.POST_ENDPOINT.replace("{postId}", apiUrl.postId.toString())
            }
            is ApiUrl.GetChannelsUrl -> {
                ApiConstants.BASE_URL + ApiConstants.ALL_CHANNELS_ENDPOINT
            }
            is ApiUrl.GetChannelDetailsUrl -> {
                ApiConstants.BASE_URL + ApiConstants.CHANNEL_DETAILS_ENDPOINT
            }
            is ApiUrl.CustomUrl -> {
                ApiConstants.BASE_URL + apiUrl.path
            }
        }
    }
}
