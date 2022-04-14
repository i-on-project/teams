# Notes

Notes is _**Completed**_

Only available for the Teachers app.

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

* `class`
* `properties`
* `entities`
* `links`
* `actions`

### Standard

* `item`
* `rel`

## Link Relations

### Domain Specific

* [Classroom](/docs/api/resources/classrooms.md#get-classroom-teacher)
* logout
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Notes](#list-notes)
* [Get Note](#get-note)

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
  "class": [ "notes", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "note" ],
      "rel": [ "item" ],
      "properties": {
        "id": 6,
        "date": "2022-05-08",
        "description": "Very good."
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/team/123123/notes/6"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/123123/teams?page=0&limit=10"
    },
    {
        "rel": ["home"],
        "href": "/api"
    },
    {
      "rel": ["next"],
      "href": "/api/orgs?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "/api/orgs?page=1&limit=10"
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
  "class": [
    "repo"
  ],
  "rel": [
    "item"
  ],
  "properties": {
    "id": 6,
    "date": "2022-05-08",
    "description": "Very good."
  },
  "entities": [
    {
      "class": [
        "team"
      ],
      "rel": [
        "item"
      ],
      "properties": {
        "name": "0.1.0",
        "date": "2022-4-14"
      },
      "links": [
        {
          "rel": [
            "self"
          ],
          "href": "/api/orgs/123123/classrooms/1/teams/234342/repo/88/tags/0.1.0"
        }
      ]
    }
  ],
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
      "rel": ["github"],
      "href": "https://github.com/i-on-project"
    },
    {
      "rel": ["avatar"],
      "href": "https://avatars.githubusercontent.com/u/59561360?s=200&v=4"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    },
    {
      "rel": ["Team"],
      "href": "/api/orgs/123123/classrooms/1/teams/234342"
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