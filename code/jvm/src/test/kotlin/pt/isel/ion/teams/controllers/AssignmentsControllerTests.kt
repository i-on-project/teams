package pt.isel.ion.teams.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pt.isel.ion.teams.assignments.AssignmentOutputModel
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE

@SpringBootTest
@AutoConfigureMockMvc
class AssignmentsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun `getAllAssignmentsTest`() {
        assertNotNull(client)

        client
            .get(Uris.Assignments.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("assignment") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("assignment") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.releaseDate") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("organizations") }
                jsonPath("$.links[2].rel") { value("classrooms") }
                jsonPath("$.links[3].rel") { value("home") }
                jsonPath("$.links[4].rel") { value("logout") }
            }
    }

    @Test
    fun `getAssignment`() {
        assertNotNull(client)

        client
            .get(Uris.Assignments.Assignment.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("assignment") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.releaseDate") { isString() }
                jsonPath("$.properties.cid") { isNumber() }
                jsonPath("$.properties.description") { isString() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("delivery") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.date") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("update-assignment") }
                jsonPath("$.actions[1].name") { value("delete-assignment") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("classrooms") }
                jsonPath("$.links[2].rel") { value("assignments") }
                jsonPath("$.links[3].rel") { value("home") }
                jsonPath("$.links[4].rel") { value("logout") }
            }
    }

    @Test
    fun `getAssignmentNotFound`() {
        assertNotNull(client)

        client
            .get(Uris.Assignments.Assignment.make(1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun `postUpdateDeleteAssignment`() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Assignments.make(1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"releaseDate\": \"2022-06-06 14:00:00\"," +
                        "    \"description\": \"Develop an app for android with the folowing requirements: .....\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.releaseDate") { value("2022-06-06 14:00:00") }
                jsonPath("$.description") {
                    value("Develop an app for android with the folowing requirements: .....")
                }
                jsonPath("$.cid") { isNumber() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdAssignment: AssignmentOutputModel = mapper.readValue(string, AssignmentOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Assignments.Assignment.make(1,1,createdAssignment.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"releaseDate\": \"2022-02-22 22:22:22\"," +
                        "    \"description\": \"Test updated description\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.releaseDate") { value("2022-02-22 22:22:22") }
                jsonPath("$.description") { value("Test updated description") }
                jsonPath("$.cid") { isNumber() }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Assignments.Assignment.make(1,1,createdAssignment.id))
            .andExpect {
                status { isOk() }
            }
    }
}