package pt.isel.ion.teams.teams

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.CollectionModel
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.students.StudentsService
import pt.isel.ion.teams.students.toCompactOutput

@RestController
@RequestMapping(Uris.Teams.MAIN_PATH)
class TeamsController(
    val teamsService: TeamsService, val studentsService: StudentsService
) {

    @GetMapping
    fun getAllTeamsOfOrganization(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @PathVariable orgId: Int,
        @PathVariable classId: Int
    ) = ResponseEntity.ok().contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE)).body(
            CollectionModel(pageIndex, pageSize).toTeamsSirenObject(
                    teamsService.getAllTeamsOfClassroom(pageSize, pageIndex, classId).map { it.toCompactOutput() },
                    orgId,
                    classId
                )
        )


    @GetMapping(Uris.Teams.Team.PATH)
    fun getTeam(
        @PathVariable orgId: Int, @PathVariable classId: Int, @PathVariable teamId: Int
    ): ResponseEntity<Any> {
        val team = teamsService.getTeam(teamId, classId).toOutput()
        val students = studentsService.getAllStudentsByTeam(teamId, 10, 0).map { it.toCompactOutput() }

        //TODO Detect if user is student or teacher

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(team.toTeacherSirenObject(students, orgId, classId))
    }

    @PostMapping
    fun createTeam(
        @RequestBody team: TeamsInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
    ): ResponseEntity<Any> {
        val createdTeam = teamsService.createTeam(team.toDb(classId)).toOutput()

        return ResponseEntity.created(Uris.Teams.Team.make(orgId, classId, createdTeam.id))
            .contentType(MediaType.APPLICATION_JSON).body(createdTeam)
    }

    @PutMapping(Uris.Teams.Team.PATH)
    fun updateTeam(
        @PathVariable classId: Int, @PathVariable teamId: Int, @RequestBody team: TeamsUpdateModel
    ) = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(teamsService.updateTeam(team.toDb(teamId)).toOutput())

    @DeleteMapping(Uris.Teams.Team.PATH)
    fun deleteTeam(@PathVariable teamId: Int): ResponseEntity<Any> {
        teamsService.deleteTeam(teamId)

        return ResponseEntity.ok().body(null)
    }
}