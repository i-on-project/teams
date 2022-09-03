package pt.isel.ion.teams.tags

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Tag resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class TagsService(val jdbi: Jdbi) {

    fun getAllTagsWithPaging(repoId: Int, pageSize: Int, pageIndex: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java)
                .getAllTagsWithPaging(repoId, pageSize + 1, pageIndex * pageSize)
        }

    fun getAllTags(repoId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).getAllTags(repoId)
        }

    fun getAllTagsWithRepoAndView(delId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).getAllTagsWithRepoAndView(delId)
        }

    fun getTag(repoId: Int, tagId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).getTag(repoId, tagId)
        }

    fun createTag(tagDbWrite: TagDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).createTag(tagDbWrite)
        }

    fun updateTag(tagDbUpdate: TagDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).updateTag(tagDbUpdate)
        }

    fun deleteTag(tagId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(TagsDAO::class.java).deleteTag(tagId)
        }
}