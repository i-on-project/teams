package pt.isel.ion.teams.repos

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos")
class RepoController(val repoService: RepoService) {

    @GetMapping
    fun getAllReposByRTeam(@PathVariable teamId: Int): List<RepoOutputModel> =
        repoService.getAllReposByTeam(teamId).map { it.toOutput() }

    @GetMapping("/{repoId}")
    fun getRepo(@PathVariable repoId: Int): RepoOutputModel =
        repoService.getRepo(repoId).toOutput()

    @PostMapping
    //TODO: create repo on github
    fun createRepo(@RequestBody repo: RepoInputModel) =
        repoService.createRepo(repo.toDb())

    @PutMapping("/{repoId}")
    fun updateRepo(@PathVariable repoId: Int, @RequestBody repo: RepoUpdateModel): Int =
        repoService.updateRepo(repo.toDb(repoId))
}