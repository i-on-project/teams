package pt.isel.daw.project.common.errors

import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import java.net.URI

@ControllerAdvice
class ErrorController : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ) : ResponseEntity<Any> {

        logger.info("Handling MethodArgumentNotValidException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md"),
                    "Bad Request",
                    400,
                    "Method argument not valid"
                )
            )
    }

    @ExceptionHandler(BaseException::class)
    fun handleDatabaseExceptions(ex: BaseException, request: WebRequest): ResponseEntity<Any> {
        logger.info("Handling " + ex.javaClass.simpleName)
        return ResponseEntity
            .status(ex.status)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(ProblemJsonModel(ex.type, ex.title, ex.status, ex.detail))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.info("Handling " + ex.javaClass.simpleName)
        return ResponseEntity
            .status(500)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(ProblemJsonModel(
                URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
                "Unknown server error",
                500,
                "An unexpected server error occured, please contact admin."
            ))
    }
}