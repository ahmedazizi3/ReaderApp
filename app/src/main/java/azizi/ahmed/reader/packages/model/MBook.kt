package azizi.ahmed.reader.packages.model

data class MBook(
    val bookID: String = "",
    val title: String = "",
    val author: String = "",
    val notes: String = "",
    val description: String = "",
    val coverImageUrl: String = "",
    val genre: String = "",
    val publicationDate: String = "",
    val isFavorite: Boolean = false,
    val rating: Double = 0.0,
    val pageCount: Int = 0
)
