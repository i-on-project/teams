# Notes

Represents a comment or review of the teacher relative to a Team. Only available for the Teachers app.

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
- ``description`` - Content of the comment.
  - mandatory
  - editable
  - type: string
  - example: ``This group as all requirement needed, but requirement 5 is not working.``

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

- [classroom](./classrooms.md#get-classroom-teacher)
- [team](./teams.md#get-team-teacher)
- home
- logout
- github - GitHub Organization URI
- avatar - GitHub Organization Avatar URI

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

- [self](https://www.iana.org/go/rfc4287)
- [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
- [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

- [List Notes](#list-notes)
- [Get Note](#get-note)

---

### Success Responses

#### List Notes

List all the Notes of a specific team.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/notes
```

```text
Status:  200 OK
```

```json
{
  "class": ["note", "collection"],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": ["note"],
      "rel": ["item"],
      "properties": {
        "id": 6,
        "date": "2022-05-08"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classroom/1/teams/123123/notes/6"
        }
      ],
      "actions": [
        {
          "name": "update-note",
          "title": "Update Note",
          "method": "PUT",
          "href": "/api/orgs/123123/classrooms/1/teams/1/notes/{notesId}",
          "type": "application/json",
          "field": [
            {"name": "description", "type": "string"}
          ]
        },
        {
        "name": "delete-note",
        "title": "Delete Note",
        "method": "DELETE",
        "href": "/api/orgs/123123/classrooms/1/teams/1/notes/{notesId}"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classroom/1/teams/123123/notes"
    },
    {
      "rel": ["next"],
      "href": "/api/orgs/852/classroom/1/teams/123123/notes?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "/api/orgs/852/classroom/1/teams/123123/notes?page=1&limit=10"
    },
    {
      "rel": ["team"],
      "href": "/api/orgs/852/classroom/1/teams/123123"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Note

This returns a single response.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/notes/{noteId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["note"],
  "properties": {
    "id": 6,
    "date": "2022-05-08",
    "description": "Very good."
  },
  "actions": [
    {
      "name": "update-note",
      "title": "Update Note",
      "method": "PUT",
      "href": "/api/orgs/123123/classrooms/1/teams/1/notes/{notesId}",
      "type": "application/json",
      "field": [
        {"name": "description", "type": "string"}
      ]
    },
    {
    "name": "delete-note",
    "title": "Delete Note",
    "method": "DELETE",
    "href": "/api/orgs/123123/classrooms/1/teams/1/notes/{notesId}"
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/teams/1/notes/1"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["notes"],
      "href": "/api/orgs/123123/classrooms/1/teams/1/notes"
    }
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
