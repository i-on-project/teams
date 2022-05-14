package pt.isel.ion.teams.common.errors

import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import java.net.URI

@ControllerAdvice
class ErrorsController : ResponseEntityExceptionHandler() {

    //TODO Review URIs
    val type400 = URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md")
    val type404 = URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md")
    val type405 = URI("")
    val type406 = URI("")
    val type415 = URI("")
    val type500 = URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md")
    val type503 = URI("")

    /*                      BUSINESS EXCEPTIONS                       */

    @ExceptionHandler(BaseException::class)
    fun handleBusinessExceptions(ex: BaseException, request: WebRequest): ResponseEntity<Any> {
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
                type500,
                "Unknown server error",
                500,
                "An unexpected server error occurred, please contact admin."
            ))
    }

    /*                      STANDARD SPRING EXCEPTIONS                       */

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
                    type400,
                    "Bad Request",
                    400,
                    "Method argument not valid"
                )
            )
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling NoHandlerFoundException")
        return ResponseEntity
            .status(404)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type404,
                    "Resource Not Found",
                    404,
                    "No handler not found for the request"
                )
            )
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling HttpMediaTypeNotAcceptableException")
        return ResponseEntity
            .status(406)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type406,
                    "Not Acceptable",
                    406,
                    "Http media type not acceptable"
                )
            )
    }

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling MissingPathVariableException")
        return ResponseEntity
            .status(500)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type500,
                    "Internal Server Error",
                    500,
                    "Missing path variable"
                )
            )
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling MissingServletRequestParameterException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Missing servlet request parameter"
                )
            )
    }

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling ServletRequestBindingException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Servlet request binding error"
                )
            )
    }

    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling ConversionNotSupportedException")
        return ResponseEntity
            .status(500)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type500,
                    "Internal Server Error",
                    500,
                    "Conversion not supported"
                )
            )
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling MissingPathVariableException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Missing servlet request parameter"
                )
            )
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling HttpMessageNotReadableException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Http message not readable"
                )
            )
    }

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling HttpMessageNotWritableException")
        return ResponseEntity
            .status(500)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type500,
                    "Internal Server Error",
                    500,
                    "Http message not writable"
                )
            )
    }

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling MissingServletRequestPartException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Missing servlet request part"
                )
            )
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.info("Handling BindException")
        return ResponseEntity
            .status(400)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(
                ProblemJsonModel(
                    type400,
                    "Bad Request",
                    400,
                    "Bind error"
                )
            )
    }
}