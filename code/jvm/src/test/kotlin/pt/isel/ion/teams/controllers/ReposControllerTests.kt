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
import pt.isel.ion.teams.repos.RepoOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class ReposControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun `getAllReposTest`() {
        assertNotNull(client)

        client
            .get(Uris.Repos.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("repo") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("repo") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.url") { isString() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("team") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun `getRepoTest`() {
        assertNotNull(client)

        client
            .get(Uris.Repos.Repo.make(1,1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("repo") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.url") { isString() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.assId") { isNumber() }
                jsonPath("$.properties.tid") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("tag") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.date") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("update-repo") }
                jsonPath("$.actions[1].name") { value("delete-repo") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("github") }
                jsonPath("$.links[3].rel") { value("tags") }
                jsonPath("$.links[4].rel") { value("repos") }
                jsonPath("$.links[5].rel") { value("team") }
                jsonPath("$.links[6].rel") { value("assignment") }
                jsonPath("$.links[7].rel") { value("logout") }
            }
    }

    @Test
    fun `getRepoNotFoundTest`() {
        assertNotNull(client)

        client
            .get(Uris.Repos.Repo.make(1,1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun `postUpdateDeleteRepoTest`() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Repos.make(1,1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"url\": \"https://github.com/create/test\"," +
                        "    \"name\": \"Grupo 13\"," +
                        "    \"assId\": 1}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.url") { value("https://github.com/create/test") }
                jsonPath("$.name") { value("Grupo 13") }
                jsonPath("$.assId") { value(1) }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdRepo: RepoOutputModel = mapper.readValue(string, RepoOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Repos.Repo.make(1,1,1,createdRepo.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"Grupo 45\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("Grupo 45") }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Repos.Repo.make(1,1,1,createdRepo.id))
            .andExpect {
                status { isOk() }
            }
    }
}