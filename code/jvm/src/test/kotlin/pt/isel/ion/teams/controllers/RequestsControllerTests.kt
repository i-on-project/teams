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

@SpringBootTest
@AutoConfigureMockMvc
class RequestsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun `getAllReposTest`() {
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

    //TODO teste de accept: adicionar team e dar accept depois verificar

    //TODO teste de decline: adicionar team e dar decline depois verificar
}