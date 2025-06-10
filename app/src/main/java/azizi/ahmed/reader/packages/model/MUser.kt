package azizi.ahmed.reader.packages.model

data class MUser(
    val userID: String = "",
    val displayName: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val isPremium: Boolean = false,
    val isDarkMode: Boolean = false,
    val isNotificationsEnabled: Boolean = true
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "userID" to userID,
            "displayName" to displayName,
            "email" to email,
            "imageUrl" to imageUrl,
            "isPremium" to isPremium,
            "isDarkMode" to isDarkMode,
            "isNotificationsEnabled" to isNotificationsEnabled
        )
    }
}
