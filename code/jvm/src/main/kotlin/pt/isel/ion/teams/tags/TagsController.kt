package pt.isel.ion.teams.tags

import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Tags.MAIN_PATH)
class TagsController(val tagsService: TagsService) {

    @GetMapping
    fun getAllTags(@PathVariable teamId: Int): List<TagOutputModel> =
        tagsService.getAllTags(teamId).map { it.toOutput() }

    @PostMapping
    @GetGeneratedKeys
    fun createTag(@PathVariable teamId: Int, @BindBean tag: TagInputModel) =
        tagsService.createTag(tag.toDb(teamId))

    @PutMapping(Uris.Tags.Tag.PATH)
    @GetGeneratedKeys
    fun updateTag(@PathVariable tagId: Int,@PathVariable teamId: Int, @BindBean tag: TagUpdateModel) =
        tagsService.updateTag(tag.toDb(tagId,teamId))
}