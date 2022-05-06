package pt.isel.ion.teams.repos

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.tags.TagsService
import pt.isel.ion.teams.tags.toCompactOutput
import pt.isel.ion.teams.teams.*

@RestController
@RequestMapping(Uris.Repos.MAIN_PATH)
class RepoController(
    val repoService: RepoService,
    val tagsService: TagsService
) {

    @GetMapping
    fun getAllReposByRTeam(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize)
                .toRepoSirenObject(
                    repoService.getAllReposByTeamWithPaging(pageSize, pageIndex, teamId).map { it.toCompactOutput() },
                    orgId,
                    classId,
                    teamId
                )
        )


    @GetMapping(Uris.Repos.Repo.PATH)
    fun getRepo(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
        @PathVariable repoId: Int
    ): ResponseEntity<Any> {
        val repo = repoService.getRepo(repoId)
        val assId = repo.assId
        val tags = tagsService.getAllTags(repoId).map { it.toCompactOutput() }

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(repo.toOutput().toTeacherSirenObject(tags, orgId, classId, teamId, assId))
    }

    @PostMapping
    fun createRepo(
        @RequestBody repo: RepoInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable teamId: Int,
    ): ResponseEntity<Any> {
        val createdRepo = repoService.createRepo(repo.toDb())

        return ResponseEntity
            .created(Uris.Repos.Repo.make(orgId, classId, teamId, createdRepo.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdRepo)
    }

    @PutMapping(Uris.Repos.Repo.PATH)
    fun updateRepo(
        @RequestBody repo: RepoUpdateModel,
        @PathVariable repoId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(repoService.updateRepo(repo.toDb(repoId)).toOutput())

    @DeleteMapping(Uris.Repos.Repo.PATH)
    fun deleteRepo(@PathVariable repoId: Int): ResponseEntity<Any> {
        repoService.deleteRepo(repoId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}