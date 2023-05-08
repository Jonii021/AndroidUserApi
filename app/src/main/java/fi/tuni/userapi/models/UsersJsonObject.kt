package fi.tuni.userapi.models
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UsersJsonObject(var users: MutableList<User>? = null)
