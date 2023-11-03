package presentation.home_screen


object Utils {
    val channelCategories = listOf(
        Category(title = "Podcasts", id = 0),
        Category(title = "Playlisters",  id = 1),
        Category(title = "Comedy",  id = 2),
    )

}

data class Category(
    val title: String = "",
    val id: Int = -1,
)