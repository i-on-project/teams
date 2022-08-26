# Internal Server Error

## Status Code

500

## Type

Server error

## Description

The server has encountered a situation it does not know how to handle.

## Use cases

- **DbConnectionException** - The connection with the database failed for an unknown reason.

- **DbTableNonExistentException**: The database table you are trying to access does not exist.

- **DbForeignKeyViolation**: The database query contains a foreign key violation

- **DbCheckViolation**: BThe database query contains a check violation.

- **DbNotNullViolation**: The database query contains a not null violation.

- **UnknownDbException**: A unknown database error has occured and the request cannot be completed.

- **SQLSyntaxErrorException**: The SQL syntax of the query you tried to perform is incorrect.

For aditional information please contact the system admin.
