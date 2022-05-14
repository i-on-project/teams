package pt.isel.ion.teams.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.EmptyDbReturnException
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE
import pt.isel.ion.teams.repos.RepoOutputModel
import pt.isel.ion.teams.teams.TeamsDbWrite
import pt.isel.ion.teams.teams.TeamsOutputModel
import pt.isel.ion.teams.teams.TeamsService

@SpringBootTest
@AutoConfigureMockMvc
class RequestsControllerTests() {

    @Autowired
    private lateinit var teamsService: TeamsService

    @Autowired
    private lateinit var client: MockMvc

    @Test
    fun getAllReposTest() {
        assertNotNull(client)

        client
            .get(Uris.Requests.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("request") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("request") }
                jsonPath("$.entities[0].properties.teamName") { isString() }
                jsonPath("$.entities[0].properties.cid") { isNumber() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }
                jsonPath("$.entities[0].links[1].rel") { value("team") }

                //Entity Actions
                jsonPath("\$.entities[0].actions") { isArray() }
                jsonPath("\$.entities[0].actions[0].name") { value("accept-request") }
                jsonPath("\$.entities[0].actions[1].name") { value("decline-request") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("classroom") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun `acceptTeamTest`() {
        assertNotNull(client)

        //Firstly we need to create a new team to then accept its request
        val createdTeam = teamsService.createTeam(TeamsDbWrite("Team to accept", 1))

        //We then accept the team through MockMVC with a PUT to the request
        client
            .put(Uris.Requests.Request.make(1, 1, createdTeam.id)) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isOk() }
            }

        //Finally, we verify that the team was successfully accepted
        val acceptedTeam = teamsService.getTeam(createdTeam.id, 1)
        assertEquals(acceptedTeam.id, createdTeam.id)
        assertEquals(acceptedTeam.name, createdTeam.name)
        assertEquals(acceptedTeam.state, "active")
    }

    @Test
    fun `declineTeamTest`() {
        assertNotNull(client)

        //Firstly we need to create a new team to then accept its request
        val createdTeam = teamsService.createTeam(TeamsDbWrite("Team to decline", 1))

        //We then accept the team through MockMVC with a PUT to the request
        client
            .delete(Uris.Requests.Request.make(1, 1, createdTeam.id)) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isOk() }
            }

        //Finally, we verify that the team was successfully declined
        assertThrows<EmptyDbReturnException> {
            teamsService.getTeam(createdTeam.id, 1)
        }
    }
}