package pt.isel.ion.teams.common.errors

import org.jdbi.v3.core.JdbiException
import java.sql.SQLException

val exceptionMap: HashMap<String, Exception> = hashMapOf(
    "P0001" to DbActionNotAllowed(),
    "08001" to DbConnectionException(),
    "23505" to ResourceAlreadyExistsException(),
    "42P01" to DbTableNonExistentException(),
    "42601" to SQLSyntaxErrorException(),
    "23503" to DbForeignKeyViolation(),
    "23514" to DbCheckViolation(),
    "23502" to DbNotNullViolation()
)

/**
 * Handler responsible for mapping SQLExceptions into their corresponding custom exception. This is done through the
 * code that comes in the reason of the exception, that indicated what went wrong in the data access.
 */
inline fun <reified T> sqlExceptionHandler(func: () -> (T?)) :T {
    try {
        return func() ?: throw EmptyDbReturnException()
    } catch (e: JdbiException) {
        val ex = exceptionMap[(e.cause as SQLException).sqlState]
        throw (ex ?: UnknownDbException())
    }
}