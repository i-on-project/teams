package pt.isel.ion.teams.common.errors

import org.springframework.http.MediaType
import java.net.URI

data class ProblemJsonModel(
    val type: URI,
    val title: String? = null,
    val status: Int,
    val detail: String? = null,
) {
    companion object {
        val MEDIA_TYPE = MediaType.parseMediaType("application/problem+json")
    }
}
