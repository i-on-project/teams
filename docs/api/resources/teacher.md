# Teacher

Represents a teacher.

## Properties

### Domain specific

- `number` - Teacher number.
    - mandatory
    - non editable
    - type: number
    - example: ``90654``
- ``name`` - Teacher name.
    - mandatory
    - editable
    - type: Date
    - possible values: ``Jumberto Silva``
- ``email`` - Email of the teacher.
    - mandatory
    - editable
    - type: String
    - possible values: ``jumberto.silva@university.pt``
- ``office`` - Office inside the university campus.
    - mandatory
    - editable
    - type: String
    - possible values: ``G.0.5``
- ``cId`` - Class id.
    - mandatory
    - editable
    - type: number
    - possible values: ``5``


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

* [Teacher](./classrooms.md#get-classroom-teacher)
* Home page
* logout

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Teachers - Classroom](#list-teachers-per-classroom)
* [Get Teacher  - Student](#get-teacher-student)
* [Get Teacher  - Teacher](#get-teacher-teahcer)

---

### Success Responses

#### List Teachers per Classroom

List all the teachers of a classroom.

```http
GET api/orgs/{orgId}/classrooms/{classId}/students
```

```text
Status:  200 OK
```

```json
{
  "class": [ "teacher", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "teacher" ],
      "rel": [ "item" ],
      "properties": {
        "number": "82264",
        "name": "Xhang-Chi",
        "email": "xhang_chi@university.pt",
        "office": "G.2.8.8",
        "cId": "4"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "api/orgs/3/classrooms/4/teachers/82264"
        },
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/teachers"
    },
    {
      "rel": ["next"],
      "href": "api/orgs/3/classrooms/4/teachers?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "api/orgs/3/classrooms/4/teachers?page=1&limit=10"
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
      "rel": ["organization"],
      "href": "/api/orgs/123123"
    }
  ]
}
```

#### Get Teacher - Student

Student view of a Get Teacher request.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teachers/{number}
```

```text
Status:  200 OK
```

```json
{
  "class": ["teacher"],
  "properties": {
        "number": "82264",
        "name": "Xhang-Chi",
        "email": "xhang_chi@university.pt",
        "office": "G.2.8.8",
        "cId": "4"
  },
  "entities": {
      "class": [ "classroom" ],
      "rel": [ "item" ],
      "properties": {
       "id": 123,
        "name": "AVE",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123"
        }
      ]
    },
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/456/teams/123/teachers/977"
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

#### Get Teacher - Teacher

Teacher view of a Get Teacher request.

```http
GET api/orgs/{orgId}/classrooms/{classId}/teachers/{number}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "teacher" ],
  "properties": {
      "number": "82264",
        "name": "Xhang-Chi",
        "email": "xhang_chi@university.pt",
        "office": "G.2.8.8",
        "cId": "4"
  },
  "entities": {
      "class": [ "classroom" ],
      "rel": [ "item" ],
      "properties": {
       "id": 123,
        "name": "AVE",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123"
        }
      ]
    },
    "actions": [
      {
        "name": "update-teacher",
        "title": "Update Teacher",
        "method": "PUT",
        "href": "/api/orgs/852/classrooms/123/teams/123/teachers/977",
        "type": "application/json",
        "field": [
          {"name": "name", "type": "number"},
          {"name": "email", "type": "string"},
          {"name": "office", "type": "string"},
          {"name": "cId", "type": "number"}
        ]
      }
    ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/456/teams/123/teachers/977"
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