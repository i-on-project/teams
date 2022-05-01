package pt.isel.ion.teams.common

import org.springframework.web.util.UriTemplate

object Uris {
    object Home {
        const val PATH = "api/home"
    }

    object Logout {
        const val PATH = "api/logout"
    }

    object Organizations {
        const val MAIN_PATH = "api/orgs"

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Organization {
            const val PATH = "/{orgId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(id: Int) = TEMPLATE.expand(mapOf("orgId" to id))
        }
    }

    object Classrooms {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int) = TEMPLATE.expand(mapOf("orgId" to orgId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Classroom {
            const val PATH = "/{classroomId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, id: Int) = TEMPLATE.expand(mapOf("orgId" to orgId, "classroomId" to id))
        }
    }

    object Teams {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classId}/teams"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int) = TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Team {
            const val PATH = "/{teamId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, id: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to id))
        }
    }

    object Repos {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int, teamId: Int) =
            TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Repo {
            const val PATH = "/{repoId}"
            private val TEMPLATE = UriTemplate(PATH)
            fun make(orgId: Int, classId: Int, teamId: Int, id: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId, "repoId" to id))
        }
    }

    object Assignments {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classId}/assignments"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int) = TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId))

        private const val PAGE_PATH = "$MAIN_PATH?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Assignment {
            const val PATH = "/{assignmentId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, id: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "assignmentId" to id))
        }
    }

    object Students {
        const val MAIN_PATH = "api/orgs/{orgId}/classrooms/{classId}"

        object FromClassroom {
            const val PATH = "/students"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make (orgId: Int, classId: Int) = TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId))

            private const val PAGE_PATH = "${PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
            private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
            fun makePage(pageIndex: Int, pageSize: Int) =
                PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))
        }

        //TODO ver isto
        object FromTeam {
            const val PATH = "/teams/{teamId}/students"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make (orgId: Int, classId: Int, teamId: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId))

            private const val PAGE_PATH = "${PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
            private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
            fun makePage(pageIndex: Int, pageSize: Int) =
                PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))
        }

        object Student {
            const val PATH = "/students/{number}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, number: Int) =
                TEMPLATE.expand(mapOf( "orgId" to orgId, "classId" to classId,"number" to number))
        }
    }

    object Notes {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/notes"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int, teamId: Int) =
            TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Note {
            const val PATH = "/{noteId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, teamId: Int, id: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId, "noteId" to id))
        }
    }

    object Requests {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classroomId}/requests"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int) =
            TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Request {
            const val PATH = "/{teamId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, id: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to id))
        }
    }

    object Tags {
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classroomId}/teams/{teamId}/repos/{repoId}/tags"
        private val TEMPLATE = UriTemplate(MAIN_PATH)
        fun make (orgId: Int, classId: Int, teamId: Int, repoId: Int) =
            TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "teamId" to teamId, "repoId" to repoId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Tag{
            const val PATH = "/{tagId}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, teamId: Int, repoId: Int, id: Int) =
                TEMPLATE.expand(mapOf(
                    "orgId" to orgId,
                    "classId" to classId,
                    "teamId" to teamId,
                    "repoId" to repoId,
                    "tagId" to id
                ))
        }
    }

    object Account {
        const val PATH = "/api/account"

        //TODO finish this URI
    }

    object Teachers{
        const val MAIN_PATH = "/api/orgs/{orgId}/classrooms/{classId}/teachers"
        private val TEMPLATE = UriTemplate(Notes.MAIN_PATH)
        fun make (orgId: Int, classId: Int) =
            TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId))

        private const val PAGE_PATH = "${MAIN_PATH}?pageIndex={pageIndex}&pageSize={pageSize}"
        private val PAGE_TEMPLATE = UriTemplate(PAGE_PATH)
        fun makePage(pageIndex: Int, pageSize: Int) =
            PAGE_TEMPLATE.expand(mapOf("pageIndex" to pageIndex, "pageSize" to pageSize))

        object Teacher{
            const val PATH = "/{number}"
            private val TEMPLATE = UriTemplate(MAIN_PATH + PATH)
            fun make(orgId: Int, classId: Int, number: Int) =
                TEMPLATE.expand(mapOf("orgId" to orgId, "classId" to classId, "number" to number))
        }
    }

}