package pt.isel.ion.teams.tags

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class TagsService(val jdbi: Jdbi) {

    fun getAllTags(teamId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).getAllTags(teamId)
        }

    fun createTag(tagDbWrite: TagDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).createTag(tagDbWrite)
        }

    fun updateTag(tagDbUpdate: TagDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).updateTag(tagDbUpdate)
        }
}