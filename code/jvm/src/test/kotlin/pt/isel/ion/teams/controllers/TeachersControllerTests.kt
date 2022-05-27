package pt.isel.ion.teams.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pt.isel.ion.teams.classrooms.ClassroomsService
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.errors.ProblemJsonModel
import pt.isel.ion.teams.common.siren.APPLICATION_TYPE
import pt.isel.ion.teams.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.siren.SIREN_SUBTYPE
import pt.isel.ion.teams.teacher.*

@SpringBootTest
@AutoConfigureMockMvc
class TeachersControllerTests {

    @Autowired
    private lateinit var client: MockMvc

    @Autowired
    private lateinit var teachersService: TeachersService

    @Autowired
    private lateinit var classroomsService: ClassroomsService

    private var mapper = jacksonObjectMapper()

    @Test
    fun getAllTeachersTest() {
        assertNotNull(client)

        client
            .get(Uris.Teachers.make(1,1)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("collection") }
                jsonPath("$.class[1]") { value("teacher") }

                //Properties
                jsonPath("$.properties.pageIndex") { isNumber() }
                jsonPath("$.properties.pageSize") { isNumber() }

                //Entities
                jsonPath("$.entities") { isArray() }
                jsonPath("$.entities[0].rel") { value("item") }
                jsonPath("$.entities[0].class") { value("teacher") }
                jsonPath("$.entities[0].properties.number") { isNumber() }
                jsonPath("$.entities[0].properties.name") { isString() }
                jsonPath("$.entities[0].properties.email") { isString() }
                jsonPath("$.entities[0].properties.office") { isString() }
                jsonPath("$.entities[0].links") { isArray() }
                jsonPath("$.entities[0].links[0].rel") { value("self") }

                //Actions
                jsonPath("$.actions") { doesNotExist() }

                //Links
                jsonPath("$.links") { isArray() }
                jsonPath("$.links[0].rel") { value("self") }
                jsonPath("$.links[1].rel") { value("classrooms") }
                jsonPath("$.links[2].rel") { value("home") }
                jsonPath("$.links[3].rel") { value("logout") }
            }
    }

    @Test
    fun getTeacherTest() {
        assertNotNull(client)

        client
            .get(Uris.Teachers.Teacher.make(1,1,86951)) {
                accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            }
            .andExpect {
                status { isOk() }
                content { contentType(SIREN_MEDIA_TYPE) }

                //Class
                jsonPath("$.class[0]") { value("teacher") }

                //Properties
                jsonPath("$.properties.number") { isNumber() }
                jsonPath("$.properties.name") { isString() }
                jsonPath("$.properties.email") { isString() }
                jsonPath("$.properties.office") { isString() }
                jsonPath("$.properties.cid") { isNumber() }

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
    fun getRepoNotFoundTest() {
        assertNotNull(client)

        client
            .get(Uris.Teachers.Teacher.make(1,1,123123123))
            .andExpect {
                status { isNotFound() }
                content { contentType(ProblemJsonModel.MEDIA_TYPE) }
            }
    }

    @Test
    fun createTeacherTest() {
        assertNotNull(client)

        //Objects to insert and compare
        val teacherInput = TeacherInputModel(12312, "TestCreate", "test@test.test", "G.0.0")
        val teacherOutput =
            TeacherCompactOutputModel(12312, "TestCreate", "test@test.test", "G.0.0")

        client
            .post(Uris.Teachers.make(1,1)) {
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = mapper.writeValueAsString(teacherInput)
            }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert POST response body
                content { json(mapper.writeValueAsString(teacherOutput)) }
            }

        teachersService.deleteTeacher(47217)
    }

    @Test
    fun updateTeacherCidTest() {
        assertNotNull(client)

        //Create teacher to test
        val teacherDb =
            TeacherDbWrite(93829, "TestUpdateCid", "test@test.test", "G.0.0", 1,1)
        val teacher = teachersService.createTeacher(teacherDb)

        //Update cid and compare
        val teacherOutput =
            TeacherCompactOutputModel(93829, "TestUpdateCid", "test@test.test", "G.0.0")
        client
            .put(Uris.Teachers.Teacher.make(1, 1, teacher.number)){
                accept = MediaType.APPLICATION_JSON
                contentType = MediaType.APPLICATION_JSON
                content = "{\"cid\":2}"
            }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                //Assert PUT response body
                content { json(mapper.writeValueAsString(teacherOutput)) }
            }

        //Fetch classrooms by teacher to see if it was successfully added
        val classes = classroomsService.getAllClassroomsByTeacher(teacher.number)
        assertEquals(classes.last().id, 2)
    }

    //TODO create and two updates test, there is no delete in this case
}