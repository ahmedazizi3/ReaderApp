package azizi.ahmed.reader.packages.model

data class Item(
    val accessInfo: AccessInfo? = null,
    val etag: String? = null,
    val id: String = "",
    val kind: String? = null,
    val saleInfo: SaleInfo? = null,
    val searchInfo: SearchInfo? = null,
    val selfLink: String? = null,
    val volumeInfo: VolumeInfo = VolumeInfo()
)
