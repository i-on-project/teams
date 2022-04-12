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

* [Bad Request](errors/badrequest.md)
* [Not Found](errors/not_found.md)
* [Unauthorized](errors/unauthorized.md)
* [Conflict](errors/conflict.md)
