package fi.tuni.userapi.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(var id: Int = 0,
                var firstName: String? = null,
                var lastName: String? = null,
                var phone :String? = null,
                var image: String? = null
)

