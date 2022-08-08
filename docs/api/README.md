# API Documentation

API documentation is divided and defined per resource.

Error responses are defined with the media-type [application/problem+json](https://www.iana.org/go/rfc7807). In the **Problems** section you can find the errors specification.

A diagram of the relation between resources is shown [here](api_resource_diagram.svg).

## Resources

* [Assignments](resources/assignments.md)
* [Classrooms](resources/classrooms.md)
* [Home](resources/home.md)
* [Notes](resources/notes.md)
* [Organizations](resources/organizations.md)
* [Repos](resources/repos.md)
* [Teams](resources/teams.md)

## Problems

* [Bad Request](problems/badrequest.md)
* [Not Found](problems/not_found.md)
* [Unauthorized](problems/unauthorized.md)
* [Conflict](problems/conflict.md)
* [Internal Server Error](problems/internal_server_error.md)
