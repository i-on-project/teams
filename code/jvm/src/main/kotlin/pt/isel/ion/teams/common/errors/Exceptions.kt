package pt.isel.ion.teams.common.errors

import java.net.URI

/**
 * File used to define all the business logic exceptions. All the exceptions extend a "BaseException" that defines the
 * data that each exception must contain (type, title, status and detail), this information satisfies the media type
 * application/problem+json in which the erros responses will be sent.
 */

open class BaseException(
    open val type: URI,
    open val title: String?,
    open val status: Int,
    open val detail: String?,
): Exception()

val uriType400 = URI("https://github.com/i-on-project/teams/blob/main/docs/api/problems/bad_request.md")
val uriType403 = URI("https://github.com/i-on-project/teams/blob/main/docs/api/problems/unauthorized.md.md")
val uriType404 = URI("https://github.com/i-on-project/teams/blob/main/docs/api/problems/not_found.md")
val uriType409 = URI("https://github.com/i-on-project/teams/blob/main/docs/api/problems/conflict.md")
val uriType500 = URI("https://github.com/i-on-project/teams/blob/main/docs/api/problems/internal_server_error.md")

/******************** 400 ********************/

class InvalidDateFormatException: BaseException(
    uriType400,
    "Bad Request",
    400,
    "The date you inserted is in an invalid format, must be <yyyy-mm-dd hh:mm:ss>"
)

class InvalidClientIdException: BaseException(
    uriType400,
    "Bad Request",
    400,
    "The Client ID you provided in not valid in the current context."
)

class MissingRegisterParametersException: BaseException(
    uriType400,
    "Bad Request",
    400,
    "There is one or more register parameters missing."
)

class InvalidAuthenticationStateException: BaseException(
    uriType403,
    "Forbidden",
    403,
    "The state return by the authorization provider does not match the user's state."
)

class UserNotRegisteredException: BaseException(
    uriType403,
    "Forbidden",
    403,
    "The user attempting to login is not yet registered."
)

class NotAnAuthorizedEmailException: BaseException(
    uriType403,
    "Forbidden",
    403,
    "The provided teacher's email for registration was not pre authorized."
)

class NoAccessTokenException: BaseException(
    uriType403,
    "Unauthorized",
    403,
    "No access token was provided by the token endpoint."
)

class NoGithubUserFoundException: BaseException(
    uriType403,
    "Unauthorized",
    403,
    "There is no existing github user associated with the login process."
)

class UserNotVerifiedException: BaseException(
    uriType403,
    "Unauthorized",
    403,
    "The user has not yet verified its email address. A new email was sent."
)

class UserNotAuthenticatedException: BaseException(
    uriType403,
    "Unauthorized",
    403,
    "The user is not authenticated."
)


class EmptyDbReturnException: BaseException(
    uriType404,
    "Resource Not Found",
    404,
    "The database resource you tried to access was not found or does not exist."
)

class ResourceAlreadyExistsException : BaseException(
    uriType409,
    "Conflict",
    409,
    "The resource you are trying to create already exists and thus cannot be created"
)

class DbActionNotAllowed : BaseException(
    uriType409,
    "Conflict",
    409,
    "The action requested is not allowed."
)


/******************** 500 ********************/

class DbConnectionException : BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The connection with the database failed for an unknown reason."
)

class DbTableNonExistentException : BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The database table you are trying to access does not exist."
)

class UnknownDbException : BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "An unknown database error has occured and the request cannot be completed."
)

class SQLSyntaxErrorException: BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The SQL syntax of the query you tried to perform is incorrect."
)

class DbForeignKeyViolation: BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The database query contains a foreign key violation."
)

class DbCheckViolation: BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The database query contains a check violation."
)

class DbNotNullViolation: BaseException(
    uriType500,
    "Internal Server Error",
    500,
    "The database query contains a not null violation."
)
