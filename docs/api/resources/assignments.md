# Assignments

Represents a task and is description.

## Properties

### Domain specific

- `id` - Unique and stable global identifier
  - mandatory
  - non editable
  - type: number
  - example: ``1``
- ``realeaseDate`` - Date of release.
  - mandatory
  - editable
  - type: string
  - possible values: ``2025/07/05``

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

- [Classrooms for student](./classrooms.md#get-classroom-student)
- [Classrooms for teacher](./classrooms.md#get-classroom-teacher)
- logout
- home - Home page
- github - GitHub Organization URI
- avatar - GitHub Organization Avatar URI

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

- [self](https://www.iana.org/go/rfc4287)
- [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
- [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

- [List Assignments](#list-assignments)
- [Get Assignment - Student](#get-assignment-student)
- [Get Assignment - Teacher](#get-assignment-teacher)

---

### Success Responses

#### List Assignments

List all the Assignments that the user has access to.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/assignments
```

```text
Status:  200 OK
```

```json
{
  "class": [ "assignments", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "assignment" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123123,
        "releaseDate": "2028/05/04",
      },
      "links": [
        {
          "rel": ["self"],

          "href": "/api/orgs/852/classrooms/123123/assignments/123123"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/123123/assignments?page=0&limit=10"
    },
    {
    "rel": ["next"],
    "href": "/api/orgs/852/classrooms/123123/assignments?page=1&limit=10"
    },
    {
    "rel": ["prev"],
    "href": "/api/orgs/852/classrooms/123123/assignments?page=1&limit=10"
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

#### Get Assignment (Student)

This returns a single team item. The user must be a part of the classroom to make such request.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/assignments/{assignmentId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["assignment"],
  "properties": {
    "id": 123123,
    "releaseDate": "2028/05/04",
  },
  "entities": [
    {
      "class": ["delivery"],
      "rel": ["item"],
      "properties": {
        "id": 9,
        "name": "phase 1",
        "date": "23/03/2022"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/classrooms/9"
        },
      ]
    }
  ],
  "actions": [
      {
        "name": "create-assignment",
        "title": "Create Assignment",
        "method": "POST",
        "href": "/api/orgs/{orgId}/classrooms/{classroomId}/assignments",
        "type": "application/json",
        "field": [
          {"name": "releaseDate", "type": "date"},
          {"name": "description", "type": "string"}
        ]
      }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/assignments/234342"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["classroom"],
      "href": "/api/orgs/123123/classrooms/1"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Assignment (Teacher)

This returns a single response. The user must be a part of the classroom to make such request.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/assignments/{assignmentId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["assignment"],
  "properties": {
    "id": 123123,
    "releaseDate": "2028/05/04",
  },
  "entities": [
    {
      "class": ["classroom"],
      "rel": ["item"],
      "properties": {
        "id": 9,
        "name": "LI61D",
        "state": "active",
        "schoolYear": "2021/22"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/classrooms/9"
        }
      ]
    }
  ],
  "actions": [
    {
      "name": "update-assignment",
      "title": "Update Assignment",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/class/{classId}/assignments{assignmentId}",
      "type": "application/json",
      "field": [
        {"name": "releaseDate", "type": "string"},
        {"name": "cId", "type": "number"},
        {"name": "description", "type": "string"}
      ]
    },
    {
    "name": "delete-assignment",
    "title": "Delete Assignment",
    "method": "DELETE",
    "href": "/api/orgs/{orgId}/class/{classId}/assignments{assignmentId}"
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/assignments/123123"
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
