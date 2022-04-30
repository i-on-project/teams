package pt.isel.daw.project.common.errors

import java.net.URI

open class BaseException(
    open val type: URI,
    open val title: String?,
    open val status: Int,
    open val detail: String?,
): Exception()

class DbConnectionException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The connection with the database failed for an unknown reason"
)

class ResourceAlreadyExistsException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/conflict.md"),
    "Conflict",
    409,
    "The resource you are trying to create already exists and thus cannot be created"
)

class DbTableNonExistentException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The database table you are trying to access does not exist"
)

class UnknownDbException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "AN unknown database error has occured and the request cannot be completed"
)

class EmptyDbReturnException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"),
    "Resource Not Found",
    404,
    "The database resource you tried to access was not found or does not exist"
)

class SQLSyntaxErrorException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The SQL syntax of the query you tried to perform is incorrect"
)

class StateTransitionNotAllowed: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md"),
    "Bad Request",
    400,
    "The state transition you tried to perform is not allowed"
)

class DbForeignKeyViolation: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The database query contains a foreign key violation"
)

class DbCheckViolation: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The database query contains a check violation"
)

class DbNotNullViolation: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The database query contains a not null violation"
)
