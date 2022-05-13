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
import pt.isel.ion.teams.notes.NotesOutputModel

@SpringBootTest
@AutoConfigureMockMvc
class NotesControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    private var mapper = jacksonObjectMapper()

    @Test
    fun `getAllNotesTest`() {
        assertNotNull(client)

        client
            .get(Uris.Notes.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("note") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("note") }
                jsonPath("$.entities[0].properties.id") { isNumber() }
                jsonPath("$.entities[0].properties.date") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("team") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun `getNoteTest`() {
        assertNotNull(client)

        client
            .get(Uris.Notes.Note.make(1,1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("note") }

                //Properties
                jsonPath("$.properties.id") { isNumber() }
                jsonPath("$.properties.date") { isString() }
                jsonPath("$.properties.description") { isString() }

                //Entities
                jsonPath("$.entities") { doesNotExist() }

                //Actions
                jsonPath("$.actions") { isArray() }
                jsonPath("$.actions[0].name") { value("update-note") }
                jsonPath("$.actions[1].name") { value("delete-note") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("home") }
                jsonPath("$.links[2].rel") { value("notes") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun `getNoteNotFoundTest`() {
        assertNotNull(client)

        client
            .get(Uris.Notes.Note.make(1,1,1,1000))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun `postUpdateDeleteNoteTest`() {
        assertNotNull(client)

        //First we post a new resource
        var result = client
            .post(Uris.Notes.make(1,1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"description\" : \"job not so great\"}"
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.date") { isString() }
                jsonPath("$.description") { value("job not so great") }
            }
            .andReturn()

        //Retrieving information from response to make update and delete
        val string: String = result.response.contentAsString
        val createdNote: NotesOutputModel = mapper.readValue(string, NotesOutputModel::class.java)

        //Second we update the resource created
        client
            .put(Uris.Notes.Note.make(1, 1, 1, createdNote.id)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"description\" : \"job well done after all\"}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                jsonPath("$.id") { isNumber() }
                jsonPath("$.date") { isString() }
                jsonPath("$.description") { value("job well done after all") }
            }

        //Third we try to delete what we just posted
        client
            .delete(Uris.Notes.Note.make(1, 1, 1, createdNote.id))
            .andExpect {
                status { isOk() }
            }
    }
}