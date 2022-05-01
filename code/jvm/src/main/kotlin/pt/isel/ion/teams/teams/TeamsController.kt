package pt.isel.ion.teams.teams

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris

@RestController
@RequestMapping(Uris.Teams.MAIN_PATH)
class TeamsController(val teamsService: TeamsService) {

    @GetMapping
    fun getAllTeamsOfOrganization(@PathVariable teamId: Int) = teamsService.getAllTeamsOfOrganization(teamId)

    @GetMapping(Uris.Teams.Team.PATH)
    fun getTeam(@PathVariable teamId: Int) = teamsService.getTeam(teamId)

    @PostMapping
    fun createTeam(@RequestBody team: TeamsInputModel)
            = teamsService.createTeam(team.toDb())

    @PutMapping(Uris.Teams.Team.PATH)
    fun updateTeam(@PathVariable teamId: Int, @RequestBody team: TeamsUpdateModel) =
        teamsService.updateTeam(team.toDb(teamId))
}