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
import pt.isel.ion.teams.deliveries.DeliveryOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class DeliveriesControllerTests {
    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun `getAllDeliveriesTest`() {
        assertNotNull(client)

        client
            .get(Uris.Deliveries.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("delivery") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

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
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("organizations") }
                jsonPath("$.links[2].rel") { value("classrooms") }
                jsonPath("$.links[3].rel") { value("deliveries") }
                jsonPath("$.links[4].rel") { value("home") }
                jsonPath("$.links[5].rel") { value("logout") }
            }
    }

    @Test
    fun `getDelivery`() {
        assertNotNull(client)

        client
            .get(Uris.Deliveries.Delivery.make(1,1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("delivery") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.date") { isString() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("tag") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.date") { isString() }
                jsonPath("$.entities[0].properties.repoId") { isNumber() }
                jsonPath("$.entities[0].properties.teamId") { isNumber() }
                jsonPath("$.entities[0].properties.delId") { isNumber() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("update-delivery") }
                jsonPath("$.actions[1].name") { value("delete-delivery") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("deliveries") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun `getDeliveryNotFound`() {
        assertNotNull(client)

        client
            .get(Uris.Deliveries.Delivery.make(1,1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun `postUpdateDeleteDelivery`() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Deliveries.make(1,1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"phase 3\"," +
                        "    \"date\": \"2022-05-09 23:59:59\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("phase 3") }
                jsonPath("$.date") { value("2022-05-09 23:59:59") }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdDel: DeliveryOutputModel = mapper.readValue(string, DeliveryOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Deliveries.Delivery.make(1,1,1, createdDel.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"name\": \"updateTest\","+
                        "    \"date\": \"2022-06-09 23:59:59\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.name") { value("updateTest") }
                jsonPath("$.date") { value("2022-06-09 23:59:59") }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Deliveries.Delivery.make(1,1,1, createdDel.id))
            .andExpect {
                status { isOk() }
            }
    }
}