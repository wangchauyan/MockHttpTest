package idv.chauyan.mockhttp.model

/**
 * data class for getting user list from github - api.github.com/users
 */
data class User(
    val login: String,
    val avatar_url: String,
    val site_admin: Boolean,
    val loadingMore: Boolean
)
