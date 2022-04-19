package pt.isel.ion.teams.tags

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class TagsService(val jdbi: Jdbi) {

    fun getAllTags(teamId: Int) = jdbi.onDemand(TagsDAO::class.java).getAllTags(teamId)

    fun createTag(tagDbWrite: TagDbWrite) = jdbi.onDemand(TagsDAO::class.java).createTag(tagDbWrite)

    fun updateTag(tagDbUpdate: TagDbUpdate) = jdbi.onDemand(TagsDAO::class.java).updateTag(tagDbUpdate)
}