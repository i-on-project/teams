# Bad Request

## Status Code

400

## Type

User error

## Description

Bad request indicates that the server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).

## Use cases

- **InvalidFormatException**: The date that was inserted is invalid, it should have the following format \<yyyy-mm-dd hh:mm:ss>.
  
- **InvalidClientIdException**: The client id provided by the client in the authentication process is not valid in the given context.

- **MissingRegisterParametersException**: There are missing parameters required for a successfull registration. For teachers the parameters name, email, insitutional number and office must be provided, for students only name, email and institutional number are required.

For aditional information please contact the system admin.
