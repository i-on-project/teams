package pt.isel.ion.teams.common

import org.springframework.web.util.UriTemplate

object Uris {

    object Organizations {

        const val PATH = "api/orgs"

        object SingleOrganization {
            const val PATH = "/{id}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }

    }
}