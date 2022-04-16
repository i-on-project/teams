# Teams

Represents a group of students. 

## Properties

### Domain specific

* `id` - Unique and stable global identifier
  * mandatory
  * non editable
  * type: number
  * example: ``1``
* ``name`` - display name for the team
  * mandatory
  * non editable
  * type: string
  * example: ``62D_G2``
* ``state`` - Indicates the state of the team, having 3 possible values.
  * mandatory
  * editable
  * type: string
  * possible values: ``pending``, ``active``, ``inactive``
* `pageIndex` - Index of the current collection page
  * non mandatory
  * editable
  * type: **number**
  * example: `0`
* `pageSize` - Describes the number of elements in the current collection page
  * non mandatory
  * editable
  * type: **number**
  * example: `0`

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

* [Classrooms](./classrooms.md#list-classrooms)
* [Repo for student](./repos.md#get-repo-student)
* [Repo for teacher](./repos.md#get-repo-teacher)
* [List assignments](./assignments.md#list-assignments)
* [Note](./notes.md#get-note)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* logout
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Teams](#list-teams)
* [Get Team - Student](#get-team-student)
* [Get Team - Teacher](#get-team-teacher)

---

### Success Responses

#### List Teams

List all the Teams that the user has access to.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams
```

```text
Status:  200 OK
```

```json
{
  "class": [ "team", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "team" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123123,
        "name": "li61d_g4",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],

          "href": "/api/orgs/852/classrooms/123123/teams/123123"
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
    "rel": ["next"],
    "href": "/api/orgs/852/classrooms/123123/teams?page=1&limit=10"
    },
    {
    "rel": ["prev"],
    "href": "/api/orgs/852/classrooms/123123/teams?page=1&limit=10"
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

#### Get Team (Student)

This returns a single team item. The user must be apart of the team to make such request.


```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["team"],
  "rel": ["item"],
  "properties": {
    "id": 234,
    "name": "li61d_g4",
    "state": "active"
  },
  "entities": [
    {
      "class": ["repo"],
      "rel": ["item"],
      "properties": {
        "id": 88,
        "url": "https://github.com/i-on-project/teams",
        "name": "li61d_g4_repo"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/classrooms/1/teams/234/repo/88"
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
      "rel": ["classrooms"],
      "href": "/api/orgs/123123/classrooms"
    },
    {
      "rel": [ "repo" ],
      "href": "/api/orgs/123123/classrooms/1/teams/234342/repos/3"
    },
    {
      "rel": [ "assignments" ],
      "href": "/api/orgs/123123/classrooms/1/assignments"
    }
  ]
}
```


#### Get Team (Teacher)

This returns a single response.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}
```

```text
Status:  200 OK
```

```json
{
  "class": [
    "team"
  ],
  "rel": [
    "item"
  ],
  "properties": {
    "id": 234342,
    "name": "li61d_g4",
    "state": "active"
  },
  "entities": [
    {
      "class": [
        "repo"
      ],
      "rel": [
        "item"
      ],
      "properties": {
        "id": 1,
        "url": "https://github.com/example",
        "name": "assignment1"
      },
      "links": [
        {
          "rel": [
            "self"
          ],
          "href": "/api/orgs/123123/classrooms/1/teams/234342/repo/1"
        }
      ]
    }
  ],
  "actions": [
    {
    "name": "update-team",
    "title": "Update Team",
    "method": "PUT",
    "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}",
    "type": "application/json",
    "field": [
      {"name": "name", "type": "string"},
      {"name": "state", "type": "string"},
      {"name": "cId", "type": "number"}
    ]
    },
    {
      "name": "delete-team",
      "title": "Delete Team",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}"
    },
    {
    "name": "create-note",
    "title": "Create note",
    "method": "POST",
    "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/notes",
    "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"name": "date", "type": "date"},
        {"name": "tId", "type": "number"},
        {"name": "description", "type": "string"}
      ]
    },
    {
      "name": "delete-note",
      "title": "Delete Note",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/notes/{noteId}"
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
      "rel": ["classroom"],
      "href": "/api/orgs/123123/classrooms/1"
    }, 
    {
      "rel": ["notes"],
      "href": "/api/orgs/{orgId}/classrooms/1/teams/234342/notes"
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
