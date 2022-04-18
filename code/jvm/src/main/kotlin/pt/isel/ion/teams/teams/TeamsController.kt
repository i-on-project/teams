package pt.isel.ion.teams.teams

import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.organizations.toDb

@RestController
@RequestMapping("/api/orgs/{teamId}/classrooms/{teamId}/teams")
class TeamsController(val teamsService: TeamsService) {

    @GetMapping
    fun getAllTeamsOfOrganization(@PathVariable teamId: Int) = teamsService.getAllTeamsOfOrganization(teamId)

    @GetMapping("/{teamId}")
    fun getTeam(@PathVariable teamId: Int) = teamsService.getTeam(teamId)

    @PostMapping
    fun createTeam(@RequestBody team: TeamsInputModel)
            = teamsService.createTeam(team.toDb())


    @PutMapping("/{teamId}")
    fun updateTeam(@PathVariable teamId: Int, @RequestBody team: TeamsUpdateModel) =
        teamsService.updateTeam(team.toDb(teamId))
}