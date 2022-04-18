package pt.isel.ion.teams.common

import org.springframework.web.util.UriTemplate

object Uris {

    object Organizations {

        const val PATH = "api/orgs"

        object Organization {
            const val PATH = "/{orgId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Classrooms {

        const val PATH = "/api/orgs/{orgId}/classrooms"

        object Classroom {
            const val PATH = "/{classroomId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Teams {
        const val PATH = "/api/orgs/{orgId}/classrooms/{classroomId}/teams"

        object Team {
            const val PATH = "/{teamId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Repos {
        const val PATH = "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos"

        object Repo {
            const val PATH = "/{repoId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Assignments {
        const val PATH = "/api/orgs/{orgId}/classrooms/{classId}/assignments"

        object Assignment {
            const val PATH = "/{assignmentId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Students {
        const val PATH = "api/orgs/{orgId}/classrooms/{classId}"

        object FromClassroom {
            const val PATH = "/students"
        }

        object FromTeam {
            const val PATH = "teams/{teamsId}/students"
        }

        object Student {
            const val PATH = "/students/{number}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Notes {
        const val PATH = "/api/orgs/{orgId}/classrooms/{classroomId}/teams/{teamId}/notes"

        object Note {
            const val PATH = "/{noteId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }

    object Requests {
        const val PATH = "api/orgs/{orgId}/classrooms/{classroomId}/requests"

        object Request {
            const val PATH = "/{teamId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("id" to id))
        }
    }
}