package pt.isel.ion.teams.teams

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.repos.RepoService
import pt.isel.ion.teams.repos.toCompactOutput

@RestController
@RequestMapping(Uris.Teams.MAIN_PATH)
class TeamsController(
    val teamsService: TeamsService,
    val reposService: RepoService
) {

    @GetMapping
    fun getAllTeamsOfOrganization(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classroomId: Int
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
        .body(
            CollectionModel(pageIndex, pageSize)
                .toTeamsSirenObject(
                    teamsService.getAllTeamsOfClassroom(pageSize, pageIndex, classroomId).map { it.toCompactOutput() },
                    orgId,
                    classroomId
                )
        )


    @GetMapping(Uris.Teams.Team.PATH)
    fun getTeam(
        @PathVariable orgId: Int,
        @PathVariable classroomId: Int,
        @PathVariable teamId: Int
    ): ResponseEntity<Any> {
        val team = teamsService.getTeam(teamId).toOutput()
        val repos = reposService.getAllReposByTeam(teamId).map { it.toCompactOutput() }

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(team.toTeacherSirenObject(repos, orgId, classroomId))
    }

    @PostMapping
    fun createTeam(
        @RequestBody team: TeamsInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val createdTeam = teamsService.createTeam(team.toDb())

        return ResponseEntity
            .created(Uris.Teams.Team.make(orgId, classId, createdTeam.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdTeam)
    }


    @PutMapping(Uris.Teams.Team.PATH)
    fun updateTeam(@PathVariable teamId: Int, @RequestBody team: TeamsUpdateModel) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(teamsService.updateTeam(team.toDb(teamId)).toOutput())

    @DeleteMapping(Uris.Classrooms.Classroom.PATH)
    fun deleteClassroom(@PathVariable teamId: Int): ResponseEntity<Any> {
        teamsService.deleteTeam(teamId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}