package idv.chauyan.mockhttp.model

/**
 * data class for getting user detail information from github - api.github.com/users/:username
 */
data class UserDetail(
    val avatar_url: String,
    val name: String,
    val bio: String,
    val login: String,
    val location: String,
    val blog: String,
    val site_admin: Boolean
)
