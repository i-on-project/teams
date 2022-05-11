package pt.isel.ion.teams.controllers

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.servlet.function.RequestPredicates.contentType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE
import java.awt.PageAttributes
import javax.management.Query.value

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationsControllerTest {

    // We need to use field injection because construction is done by JUnit and not Spring context
    @Autowired
    private lateinit var client: MockMvc

    @Test
    fun `getAllOrganizationsTest`() {
        assertNotNull(client)

        client
            .get("/api/orgs") {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
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
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("organization") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.description") { isString() }
                jsonPath("$.entities[0].links") { isArray() }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
            }
    }

    @Test
    fun `getOrganization`() {
        assertNotNull(client)

        client
            .get("/api/orgs/1") {
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
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("classroom") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.description") { isString() }
                jsonPath("$.entities[0].properties.schoolYear") { isString() }
                jsonPath("$.entities[0].links") { isArray() }

                //Actions
                jsonPath("$.actions") { isArray() }

                //Links
                jsonPath("$.links") { isArray() }
            }
    }

    @Test
    fun `getOrganizationNotFound`() {
        assertNotNull(client)

        client
            .get("/api/orgs/1000")
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun `postDeleteOrganization`() {
        assertNotNull(client)

        //First we post a new resource
        client
            .post("/api/orgs"){
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\" : \"testOrg1\",\"description\" : \"testDescription1\"}"
            }
            .andExpect {
                status { isCreated() }
                content().string("Hello from the greeting service")
            }
    }
}