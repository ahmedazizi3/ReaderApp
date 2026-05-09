package azizi.ahmed.reader.packages.model

data class AccessInfo(
    val accessViewStatus: String? = null,
    val country: String? = null,
    val embeddable: Boolean? = null,
    val epub: Epub? = null,
    val pdf: Pdf? = null,
    val publicDomain: Boolean? = null,
    val quoteSharingAllowed: Boolean? = null,
    val textToSpeechPermission: String? = null,
    val viewability: String? = null,
    val webReaderLink: String? = null
)
