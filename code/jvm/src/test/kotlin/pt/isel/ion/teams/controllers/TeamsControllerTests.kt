package pt.isel.ion.teams.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE
import pt.isel.ion.teams.teams.TeamsOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class TeamsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllTeamsTest() {
        assertNotNull(client)

        client
            .get(Uris.Teams.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("team") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("team") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.state") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("classroom") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun getTeamTest() {
        assertNotNull(client)

        client
            .get(Uris.Teams.Team.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("team") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.cid") { isNumber() }
                jsonPath("$.properties.state") { isString() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("student") }
                jsonPath("$.entities[0].properties.number") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("update-team") }
                jsonPath("$.actions[1].name") { value("delete-team") }
                jsonPath("$.actions[2].name") { value("create-note") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("classroom") }
                jsonPath("$.links[3].rel") { value("repos") }
                jsonPath("$.links[4].rel") { value("notes") }
                jsonPath("$.links[5].rel") { value("logout") }
            }
    }

    @Test
    fun getTeamNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Teams.Team.make(1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun postUpdateDeleteTeamTest() {
        assertNotNull(client)

        //First we post a new resource
        val result = client
            .post(Uris.Teams.make(1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"Test team\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("Test team") }
                jsonPath("$.cid") { isNumber() }
                jsonPath("$.state") { isString() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdTeam: TeamsOutputModel = mapper.readValue(string, TeamsOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Teams.Team.make(1,1,createdTeam.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"Test update${createdTeam.id}\", \"state\": \"inactive\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("Test update${createdTeam.id}") }
                jsonPath("$.state") { value("inactive") }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Teams.Team.make(1,1,createdTeam.id))
            .andExpect {
                status { isOk() }
            }
    }
}