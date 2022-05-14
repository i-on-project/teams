package pt.isel.ion.teams.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pt.isel.ion.teams.classrooms.ClassroomOutputModel
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE

@SpringBootTest
@AutoConfigureMockMvc
class ClassroomsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllClassroomsTest() {
        assertNotNull(client)

        client
            .get(Uris.Classrooms.make(1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("classroom") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("classroom") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.description") { isString() }
                jsonPath("$.entities[0].properties.schoolYear") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }


                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("organization") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun getClassroomTest() {
        assertNotNull(client)

        client
            .get(Uris.Classrooms.Classroom.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("classroom") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.description") { isString() }
                jsonPath("$.properties.schoolYear") { isString() }

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
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("create-assignment") }
                jsonPath("$.actions[1].name") { value("delete-classroom") }
                jsonPath("$.actions[2].name") { value("update-classroom") }
                jsonPath("$.actions[3].name") { value("create-invite-link") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("github") }
                jsonPath("$.links[3].rel") { value("avatar") }
                jsonPath("$.links[4].rel") { value("organization") }
                jsonPath("$.links[5].rel") { value("assignments") }
                jsonPath("$.links[6].rel") { value("requests") }
                jsonPath("$.links[7].rel") { value("invite-links") }
                jsonPath("$.links[8].rel") { value("students") }
                jsonPath("$.links[9].rel") { value("logout") }
            }
    }

    @Test
    fun getClassroomNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Classrooms.Classroom.make(1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun postUpdateDeleteClassroomTest() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Classrooms.make(1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\" : \"testClass\"," +
                        "    \"description\" : \"testDescription\"," +
                        "    \"maxTeams\": 3," +
                        "    \"maxMembersPerTeam\": 4," +
                        "    \"repoURI\": \"repoURIexample\"," +
                        "    \"schoolYear\": \"2021/22\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("testClass") }
                jsonPath("$.description") { value("testDescription") }
                jsonPath("$.state") { value("active") }
                jsonPath("$.schoolYear") { value("2021/22") }
                jsonPath("$.githubURI") { isString() }
                jsonPath("$.schoolYear") { isString() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdClass: ClassroomOutputModel = mapper.readValue(string, ClassroomOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Classrooms.Classroom.make(1,createdClass.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\" : \"testUpdatedOrg${createdClass.id}\"," +
                        "    \"description\" : \"testUpdatedDescription\"," +
                        "    \"maxTeams\": 3," +
                        "    \"maxMembersPerTeam\": 4," +
                        "    \"schoolYear\": \"2021/22\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("testUpdatedOrg${createdClass.id}") }
                jsonPath("$.description") { value("testUpdatedDescription") }
                jsonPath("$.state") { value("active") }
                jsonPath("$.schoolYear") { value("2021/22") }
                jsonPath("$.githubURI") { isString() }
                jsonPath("$.schoolYear") { isString() }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Classrooms.Classroom.make(1,createdClass.id))
            .andExpect {
                status { isOk() }
            }
    }
}