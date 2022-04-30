package pt.isel.daw.project.common.errors

import org.jdbi.v3.core.JdbiException
import java.sql.SQLException

var exceptionMap: HashMap<String, Exception> = hashMapOf(
    "08001" to DbConnectionException(),
    "23505" to ResourceAlreadyExistsException(),
    "42P01" to DbTableNonExistentException(),
    "42601" to SQLSyntaxErrorException(),
    "23503" to DbForeignKeyViolation(),
    "23514" to DbCheckViolation(),
    "23502" to DbNotNullViolation(),
)

inline fun <reified T> sqlExceptionHandler(func: () -> (T?)) :T {
    try {
        return func() ?: throw EmptyDbReturnException()
    } catch (e: JdbiException) {
        val ex = exceptionMap[(e.cause as SQLException).sqlState]
        throw (ex ?: UnknownDbException())
    }
}