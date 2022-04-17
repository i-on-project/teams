package pt.isel.ion.teams.common

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.util.UriTemplate

object Uris {

    object Organizations {

        const val PATH = "api/orgs"

        object SingleOrganization {
            const val PATH = "/{orgId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Classrooms {

        const val PATH = "/api/orgs/{orgId}/classrooms"

        object SingleClassroom {
            const val PATH = "/{classroomId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Assignments {
        const val PATH = "/api/orgs/{orgId}/classrooms/{classId}/assignments"

        object SingleAssignment {
            const val PATH = "/{assignmentId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }
}