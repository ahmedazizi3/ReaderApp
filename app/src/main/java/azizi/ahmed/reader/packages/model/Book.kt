package azizi.ahmed.reader.packages.model

data class Book(
    val items: List<Item> = emptyList(),
    val kind: String? = null,
    val totalItems: Int = 0
)
