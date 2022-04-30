package pt.isel.daw.project.common.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI

const val APPLICATION_TYPE = "application"
const val SIREN_SUBTYPE = "vnd.siren+json"

const val SIREN_MEDIA_TYPE = "${APPLICATION_TYPE}/${SIREN_SUBTYPE}"

fun selfLink(uri: String) = SirenLink(rel = SirenRelations.SELF, href = URI(uri))

data class SirenLink(
    val rel: String,
    val href: URI,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenAction(

    val name: String,
    val title: String,
    val method: HttpMethod,
    val href: URI,

    @JsonSerialize(using = ToStringSerializer::class)
    val type: MediaType? = null,
    val possibilities: List<Possibility>? = null,
    val fields: List<Field>? = null
) {
    data class Field(
        val name: String,
        val type: String? = null,
    )

    data class Possibility(
        val name: String,
        val value: String? = null,
    )
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenEntity<T>(
    @JsonProperty("class") val clazz: List<String>? = null,
    val properties: T? =null,
    val entities: List<SubEntity>? = null,
    val actions: List<SirenAction>? = null,
    val links: List<SirenLink>? = null,
)

sealed class SubEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmbeddedEntity<T>(
    val rel: List<String>,
    @JsonProperty("class") val clazz: List<String>? = null,
    val properties: T? =null,
    val entities: List<SubEntity>? = null,
    val actions: SirenAction? = null,
    val links: List<SirenLink>? = null,
    val title: String? = null
) : SubEntity()