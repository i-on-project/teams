# Student

Represents a student

## Properties

### Domain specific

- `number` - Student number.
    - mandatory
    - non editable
    - type: number
    - example: ``90654``
- ``name`` - Student name.
    - mandatory
    - editable
    - type: Date
    - possible values: ``Jumberto Silva``

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

* [Classroom](./classrooms.md#get-classroom-teacher)
* Home page
* logout
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Students - Classroom](#list-students-classroom)
* [List Students - Team](#list-students-team)
* [Get Students](#list-students-team)

---

### Success Responses

#### List Students Classroom

List all the students of a classroom.

```http
GET api/orgs/{orgId}/classrooms/{classId}/students
```

```text
Status:  200 OK
```

```json
{
  "class": [ "students", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "students" ],
      "rel": [ "item" ],
      "properties": {
        "number": "82264",
        "name": "Xhang-Chi"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "api/orgs/3/classrooms/4/students/82264"
        },
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/students/requests?page=0&limit=10"
    },
    {
      "rel": ["next"],
      "href": "api/orgs/3/classrooms/4/students/requests?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "api/orgs/3/classrooms/4/students?page=1&limit=10"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    },
    {
      "rel": ["classroom"],
      "href": "api/orgs/3/classrooms/4"
    }
  ]
}
```

#### List Students team

List all the students of a team.

```http
GET api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/students
```

```text
Status:  200 OK
```

```json
{
  "class": [ "students", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "students" ],
      "rel": [ "item" ],
      "properties": {
        "number": "82264",
        "name": "Xhang-Chi"
      },
      "links": [
        {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/teams/5/students/82264"
    },
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/teams/5/students/requests?page=0&limit=10"
    },
    {
      "rel": ["next"],
      "href": "api/orgs/3/classrooms/4/teams/5/students/requests?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "api/orgs/3/classrooms/4/teams/5/students?page=1&limit=10"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    },
    {
      "rel": ["classroom"],
      "href": "api/orgs/3/classrooms/4"
    },
    {
      "rel": ["team"],
      "href": "api/orgs/3/classrooms/4/team/5"
    }
  ]
}
```
#### Get Student

Student view of a Get Student request.

```http
GET api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/students{number}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "student" ],
  "properties": {
      "number": 977,
      "name": "Julio Lourenço"
  },
  "entities": {
      "class": [ "team" ],
      "rel": [ "item" ],
      "properties": {
        "id": 12,
        "name": "team1",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/456/teams/123"
        }
      ]
    },
    "actions": [
      {
        "name": "update-student",
        "title": "Update Student",
        "method": "PUT",
        "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/123/students/977",
        "type": "application/json",
        "field": [
          {"name": "name", "type": "string"}
        ]
      }
    ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/456/teams/123/students/977"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["organization"],
      "href": "/api/orgs/123123"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Student

Teacher view of a Get Student request.

```http
GET api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/students{number}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "student" ],
  "properties": {
      "number": 977,
      "name": "Julio Lourenço"
  },
  "entities": {
      "class": [ "team" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123,
        "name": "team1",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/456/teams/123"
        }
      ]
    },
    "actions": [
      {
        "name": "update-student",
        "title": "Update Student",
        "method": "PUT",
        "href": "/api/orgs/{orgId}/classrooms/{classId}/teams/123/students/977",
        "type": "application/json",
        "field": [
          {"name": "name", "type": "string"},
          {"name": "tId", "type": "number"},
          {"name": "cId", "type": "number"}
        ]
      }
    ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/456/teams/123/students/977"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["organization"],
      "href": "/api/orgs/123123"
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