package pt.isel.ion.teams.repos

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Repos.MAIN_PATH)
class RepoController(val repoService: RepoService) {

    @GetMapping
    fun getAllReposByRTeam(@PathVariable teamId: Int): List<RepoOutputModel> =
        repoService.getAllReposByTeam(teamId).map { it.toOutput() }

    @GetMapping(Uris.Repos.Repo.PATH)
    fun getRepo(@PathVariable repoId: Int): RepoOutputModel =
        repoService.getRepo(repoId).toOutput()

    @PostMapping
    //TODO: create repo on github
    fun createRepo(@RequestBody repo: RepoInputModel) =
        repoService.createRepo(repo.toDb())

    @PutMapping(Uris.Repos.Repo.PATH)
    fun updateRepo(@PathVariable repoId: Int, @RequestBody repo: RepoUpdateModel): Int =
        repoService.updateRepo(repo.toDb(repoId))
}