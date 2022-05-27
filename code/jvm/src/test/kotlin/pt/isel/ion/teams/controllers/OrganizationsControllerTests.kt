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
import pt.isel.ion.teams.organizations.OrganizationOutputModel
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllOrganizationsTest() {
        assertNotNull(client)

        client
            .get(Uris.Organizations.make()) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
                cookie(Cookie("number","86951"))
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("organization") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("organization") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.description") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("create-organization") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("logout") }
            }
    }

    @Test
    fun getOrganizationTest() {
        assertNotNull(client)

        client
            .get(Uris.Organizations.Organization.make(1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("organization") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.description") { isString() }
                jsonPath("$.properties.githubUri") { isString() }
                jsonPath("$.properties.avatarUri") { isString() }

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
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("create-classroom") }
                jsonPath("$.actions[1].name") { value("update-organization") }
                jsonPath("$.actions[2].name") { value("delete-organization") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("github") }
                jsonPath("$.links[3].rel") { value("logout") }
                jsonPath("$.links[4].rel") { value("classrooms") }
                jsonPath("$.links[5].rel") { value("organizations") }
            }
    }

    @Test
    fun getOrganizationNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Organizations.Organization.make(1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun postUpdateDeleteOrganizationTest() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Organizations.make()) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\" : \"testOrg1\",\"description\" : \"testDescription1\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("testOrg1") }
                jsonPath("$.description") { value("testDescription1") }
                jsonPath("$.githubUri") { isString() }
                jsonPath("$.avatarUri") { isString() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdOrg: OrganizationOutputModel = mapper.readValue(string, OrganizationOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Organizations.Organization.make(createdOrg.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\" : \"testUpdatedOrg${createdOrg.id}\",\"description\" : \"testUpdatedDescription\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("testUpdatedOrg${createdOrg.id}") }
                jsonPath("$.description") { value("testUpdatedDescription") }
                jsonPath("$.githubUri") { isString() }
                jsonPath("$.avatarUri") { isString() }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Organizations.Organization.make(createdOrg.id))
            .andExpect {
                status { isOk() }
            }
    }
}