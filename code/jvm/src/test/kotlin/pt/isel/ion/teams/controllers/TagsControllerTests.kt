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
import pt.isel.ion.teams.repos.RepoOutputModel
import pt.isel.ion.teams.tags.TagOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class TagsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllTagsTest() {
        assertNotNull(client)

        client
            .get(Uris.Tags.make(1,1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("tag") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

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
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("repo") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun getTagTest() {
        assertNotNull(client)

        client
            .get(Uris.Tags.Tag.make(1,1,1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("tag") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.date") { isString() }

                //Entities
                jsonPath("$.entities") { doesNotExist() }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("delete-tag") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("tags") }
                jsonPath("$.links[3].rel") { value("delivery") }
                jsonPath("$.links[4].rel") { value("logout") }
            }
    }

    @Test
    fun getRepoNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Tags.Tag.make(1,1,1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun postUpdateDeleteTagTest() {
        assertNotNull(client)

        //First we post a new resource
        val result = client
            .post(Uris.Tags.make(1,1,1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"final\"," +
                        "    \"delId\": 1}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("final") }
                jsonPath("$.date") { isString() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdTag: TagOutputModel = mapper.readValue(string, TagOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Tags.Tag.make(1,1,1,1,createdTag.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"final 2.0\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("final 2.0") }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Tags.Tag.make(1,1,1,1,createdTag.id))
            .andExpect {
                status { isOk() }
            }
    }
}