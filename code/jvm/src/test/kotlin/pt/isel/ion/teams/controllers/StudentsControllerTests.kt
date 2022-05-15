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
import pt.isel.ion.teams.students.StudentCompactOutputModel
import pt.isel.ion.teams.students.StudentInputModel
import pt.isel.ion.teams.students.StudentsService
import pt.isel.ion.teams.students.toDb

@SpringBootTest
@AutoConfigureMockMvc
class StudentsControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    @Autowired
    private lateinit var studentsService: StudentsService

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllStudentsByTeamTest() {
        assertNotNull(client)

        client
            .get(Uris.Students.FromTeam.make(1,1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("student") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("student") }
                jsonPath("$.entities[0].properties.number") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
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
    fun getAllStudentsByClassroomTest() {
        assertNotNull(client)

        client
            .get(Uris.Students.FromClassroom.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("student") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("student") }
                jsonPath("$.entities[0].properties.number") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
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
    fun getStudentTest() {
        assertNotNull(client)

        client
            .get(Uris.Students.Student.make(1,1,10000)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("student") }

                //Properties
                jsonPath("$.properties.number") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.tid") { isNumber() }

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
                jsonPath("$.actions[0].name") { value("update-student") }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("classroom") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun getRepoNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Students.Student.make(1,1,99999))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun postStudentTest() {
        assertNotNull(client)

        val studentInput = StudentInputModel(46912, "Martim Francisco")
        val studentOutput = StudentCompactOutputModel(46912, "Martim Francisco")

        client
            .post(Uris.Students.FromClassroom.make(1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = mapper.writeValueAsString(studentInput)
            }
            .andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(mapper.writeValueAsString(studentOutput))
                }
            }

        studentsService.deleteStudent(46912)
    }

    @Test
    fun putStudentTest() {
        assertNotNull(client)

        val studentInput = StudentInputModel(46912, "Martim Francisco").toDb()
        val student = studentsService.createStudent(studentInput)

        //TODO
    }
}