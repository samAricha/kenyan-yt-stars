package presentation.home_screen


object Utils {
    val channelCategories = listOf(
        Category(title = "All", id = 0),
        Category(title = "Podcasts", id = 1),
        Category(title = "Playlisters",  id = 2),
        Category(title = "Comedy",  id = 3),
    )

}

data class Category(
    val title: String = "",
    val id: Int = -1,
)