package pt.isel.ion.teams.common.errors

import java.net.URI

open class BaseException(
    open val type: URI,
    open val title: String?,
    open val status: Int,
    open val detail: String?,
): Exception()

/******************** 400 ********************/

class InvalidDateFormatException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md"),
    "Bad Request",
    400,
    "The date you inserted is in an invalid format, must be <yyyy-mm-dd hh:mm:ss>"
)

class InvalidClientIdException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md"),
    "Bad Request",
    400,
    "The Client ID you provided in not valid in the current context."
)

class MissingRegisterParametersException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/bad_request.md"),
    "Bad Request",
    400,
    "There is one or more register parameters missing."
)

class InvalidAuthenticationStateException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"), //TODO Update URL
    "Forbidden",
    403,
    "The state return by the authorization provider does not match the user's state."
)

class UserNotRegisteredException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"), //TODO Update URL
    "Forbidden",
    403,
    "The user attempting to login is not yet registered."
)

class NotAnAuthorizedEmailException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"), //TODO Update URL
    "Forbidden",
    403,
    "The provided teacher's email for registration was not pre authorized."
)

class NoAccessTokenException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"),
    "Unauthorized",
    403,
    "No access token was provided by the token endpoint."
)

class NoGithubUserFoundException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"),
    "Resource Not Found",
    403,
    "There is no existing github user associated with the login process."
)

class EmptyDbReturnException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/not_found.md"),
    "Resource Not Found",
    404,
    "The database resource you tried to access was not found or does not exist"
)

class ResourceAlreadyExistsException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/conflict.md"),
    "Conflict",
    409,
    "The resource you are trying to create already exists and thus cannot be created"
)

/******************** 500 ********************/

class DbConnectionException : BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The connection with the database failed for an unknown reason"
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

class SQLSyntaxErrorException: BaseException(
    URI("https://github.com/isel-leic-daw/project-g4/blob/main/docs/api/problems/internal_server_error.md"),
    "Internal Server Error",
    500,
    "The SQL syntax of the query you tried to perform is incorrect"
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
