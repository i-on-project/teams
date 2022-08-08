# Forbidden

## Status Code

403

## Type

User error

## Description

The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource. We know the identity of the user.

## Use cases

- **InvalidAuthenticationStateException**: The state returned by the authorization endpoint does not match the one provided and thus the authentication process failed.

- **UserNotRegisteredException**: The user trying to login is not yet registered in the service.

- **NotAnAuthorizedEmailException**: For teacher registration, the email must be previously authorized by the system admin. Registration attempts with emails that are not previously authorized will fail.

- **NoAccessTokenException**: No access token was provided by the authorization endpoint.

- **NoGithubUserFoundException**: No GitHub user was found associated with the current login process.

For aditional information please contact the system admin.
