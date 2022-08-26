package pt.isel.ion.teams.tags

import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.deliveries.DeliveriesService

@RestController
@RequestMapping(Uris.Tags.MAIN_PATH)
class TagsController(
    val tagsService: TagsService,
    val deliveryService: DeliveriesService
) {
    @GetMapping
    fun getAllTags(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable repoId: Int,
        @PathVariable teamId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize).toTagSirenObject(
                tagsService.getAllTagsWithPaging(teamId, pageSize, pageIndex).map { it.toCompactOutput() },
                orgId,
                classId,
                teamId,
                repoId
            )
        )

    @GetMapping(Uris.Tags.Tag.PATH)
    fun getTag(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
        @PathVariable repoId: Int,
        @PathVariable tagId: Int
    ): ResponseEntity<Any> {
        val tag = tagsService.getTag(repoId, tagId)
        val delivery = deliveryService.getDelivery(tag.delId)

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(tag.toOutput().toSirenObject(orgId, classId, teamId, repoId, delivery))
    }

    @PostMapping
    @GetGeneratedKeys
    fun createTag(
        @RequestBody tag: TagInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable repoId: Int,
        @PathVariable teamId: Int,
    ): ResponseEntity<Any> {
        val createdTag = tagsService.createTag(tag.toDb(repoId)).toOutput()

        return ResponseEntity
            .created(Uris.Tags.Tag.make(orgId, classId, repoId, teamId, createdTag.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdTag)
    }

    @PutMapping(Uris.Tags.Tag.PATH)
    @GetGeneratedKeys
    fun updateTag(
        @PathVariable tagId: Int,
        @RequestBody tag: TagUpdateModel
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(tagsService.updateTag(tag.toDb(tagId)).toOutput())

    @DeleteMapping(Uris.Tags.Tag.PATH)
    fun deleteTag(@PathVariable tagId: Int): ResponseEntity<Any> {
        tagsService.deleteTag(tagId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}