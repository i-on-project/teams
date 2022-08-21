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
import pt.isel.ion.teams.inviteCode.InviteLinksOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class InviteCodesControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllInviteLinksTest() {
        assertNotNull(client)

        client
            .get(Uris.InviteCodes.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("invite-link") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("invite-link") }
                jsonPath("$.entities[0].properties.code") { isString() }
                jsonPath("$.entities[0].properties.cid") { isNumber() }
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
    fun getInviteLinkTest() {
        assertNotNull(client)

        client
            .get(Uris.InviteCodes.InviteCode.make(1,1,"8b171ab5-2f09-4272-a607-f8fd68eeca31")) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("invite-link") }

                //Properties
                jsonPath("$.properties.code") { isString() }
                jsonPath("$.properties.cid") { isNumber() }

                //Entities
                jsonPath("$.entities") { doesNotExist() }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("create-team") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("logout") }
            }
    }

    @Test
    fun getOrganizationNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.InviteCodes.InviteCode.make(1,1,"DoesNotExist"))
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
            .post(Uris.InviteCodes.make(1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.code") { isString() }
                jsonPath("$.cid") { isNumber() }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdInviteLink: InviteLinksOutputModel = mapper.readValue(string, InviteLinksOutputModel::class.java)

        //Third we try to delete what we just posted
        client
            .delete(Uris.InviteCodes.InviteCode.make(1,1,createdInviteLink.code))
            .andExpect {
                status { isOk() }
            }
    }
}