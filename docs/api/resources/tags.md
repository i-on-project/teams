# Tags

Represents a tag created in one of the teams repos. This information is only available for the teacher, students are advised to follow and manage tags directly on the Github repository.

## Properties

### Domain specific

- `id` - Unique and stable global identifier
  - mandatory
  - non editable
  - type: number
  - example: ``1``
- ``date`` - Indicates date that the comment was made.
  - mandatory
  - editable
  - type: Date
  - possible values: ``2022-08-07``
- ``name`` - Name of the tag.
  - mandatory
  - editable
  - type: string
  - example: ``v0.0.0``

### Media-type [Siren](https://github.com/kevinswiber/siren)

- `class`
- `properties`
- `entities`
- `links`
- `actions`

### Standard

- `item`
- `rel`

## Link Relations

### Domain Specific

- [repo](./repos.md#get-repo-teacher)
- [delivery](./deliveries.md#get-delivery-teacher)
- [tags](#list-tags)
- [tag](#get-tag)
- home
- logout

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

- [self](https://www.iana.org/go/rfc4287)
- [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
- [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

- [List Notes](#list-notes)
- [Get Note](#get-note)

---

### Success Responses

#### List Tags

List all the Tags of a specific repo.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos/{repoId}/tags
```

```text
Status:  200 OK
```

```json
{
  "class": [ "tag", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "tag" ],
      "rel": [ "item" ],
      "properties": {
        "id": 6,
        "date": "2022-05-08",
        "name": "v0.0.0"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/team/123123/repos/6/tags/1"
        }
      ]
    }
  ],
  "links": [
    {
        "rel": ["self"],
        "href": "/api/orgs/852/classrooms/1/team/123123/repos/1/tags"
    },
    {
        "rel": ["next"],
        "href": "/api/orgs/852/classrooms/1/team/123123/repos/1/tags?page=0&limit=10"
    },
    {
        "rel": ["prev"],
        "href": "/api/orgs/852/classrooms/1/team/123123/repos/1/tags?page=0&limit=10"
    },
    {
        "rel": ["home"],
        "href": "/api"
    },
    {
        "rel": ["repo"],
        "href": "/api/orgs/852/team/123123/repos/6"
    },
    {
        "rel": ["logout"],
        "href": "/api/logout"
    }
  ]
}
```

#### Get Tag

This returns a single tag, request only available for teachers.

```http
GET /api/orgs/{orgId}/classrooms/{classroomId}/teams/{teamId}/notes/{noteId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "tag" ],
  "properties": {
    "id": 6,
    "date": "2022-05-08",
    "name": "v0.0.0"
  },
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/teams/234342"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["tags"],
      "href": "/api/orgs/852/classrooms/1/team/123123/repos/1/tags"
    },
    {
      "rel": ["delivery"],
      "href": "/api/orgs/852/classrooms/1/assignment/1/delivery/1"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

### Error Responses

#### Bad Request

Used in requests that return both collection and individual items.

```text
Status: 400 Bad Request
```

```json
{
  "type": "https://github.com/i-on-project/teams/blob/main/docs/api/problems/bad_request.md",
  "title": "The request parameters are invalid.",
  "status": 400,
  "detail": "Some parameters are missing or are of an invalid type."
}
```

#### Unauthorized

Used in requests that return both collection and individual items.

```text
Status: 401 Unauthorized
```

```json
{
  "type": "https://github.com/i-on-project/teams/blob/main/docs/api/problems/unauthorized.md",
  "title": "The request parameters are invalid.",
  "status": 401,
  "detail": "Some parameters are missing or are of an invalid type."
}
```

#### Not Found

Used only in requests that return individual items.

```text
Status: 404 Not Found
```

```json
{
  "type": "https://github.com/i-on-project/teams/blob/main/docs/api/problems/not_found.md",
  "title": "The request parameters are invalid.",
  "status": 404,
  "detail": "Some parameters are missing or are of an invalid type."
}
```

#### Conflict

Used in requests that return both collection and individual items.

```text
Status: 409 Conflict
```

```json
{
  "type": "https://github.com/i-on-project/teams/blob/main/docs/api/problems/conflict.md",
  "title": "There is a conflict with the request.",
  "status": 409,
  "detail": "There is a conflict with the request because the resource already exists."
}
```
