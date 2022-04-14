# Classrooms

A classroom represents a classroom of a given course, it is also associated with a given repo.

## Properties

### Domain specific

* `id` - Unique and stable global identifier
  * mandatory
  * non editable
  * type: **number**
  * example: `1`
* `name` - Unique and short name that defines the resource
  * mandatory
  * non editable
  * type: **string**
  * example: `Some Organization`
* `description` - Description that characterizes the resource
  * non mandatory
  * non editable
  * type: **string**
  * example: `This is some organization`
* `state` - Classroom status
  * mandatory
  * non editable
  * type: **string**
  * example: `This is some organization`
* ``ShcoolYear`` - School year of the present class
  * mandatory
  * non editable
  * type: **string**
  * example: ``2022/23``
* `maxGroups` - Maximum allowed number os teams in a classroom
  * mandatory
  * non editable
  * type: **number**
  * example: `This is some organization`
* `maxGroupMembers` - Maximum allowed number of students per team
  * mandatory
  * non editable
  * type: **number**
  * example: `This is some organization`
* `schoolYear` - Year in which the classroom functioned
  * mandatory
  * non editable
  * type: **string**
  * example: `This is some organization`
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

* [Organizations](organizations.md#list-organizations)
* [Classrooms](#list-classrooms)
* [Assignments](assignments.md#list-assignments)
* [Team](teams.md#list-teams)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Classrooms](#list-classrooms)
* [Get Classroom - Student](#get-classroom-student)
* [Ger Classroom - Teacher](#get-classroom-teacher)

---

### Success Responses

#### List Classrooms

List all the classrooms that the user has access to. This request can be made by either students or teachers.

```http
GET /api/orgs/{orgId}/classrooms
```

```text
Status:  200 OK
```

```json
{
  "class": ["classrooms", "collection"],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
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
          "href": "/api/orgs/852/classrooms/123123/teams/456"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms?page=0&limit=10"
    },
    {
      "rel": ["next"],
      "href": "/api/orgs/852/classrooms?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "/api/orgs/852/classrooms?page=1&limit=10"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Classroom (Student)

Student view of a Get Classroom request. This returns a single request. The user must be apart of the classroom to make such request.

```http
GET /api/orgs/{orgId}/classrooms/{classroomId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "classroom" ],
  "rel": [ "item" ],
  "properties": {
    "properties": {
      "id": 9,
      "name": "LI61D",
      "state": "active",
      "schoolYear": "2021/22"
    }
  },
  "entities": {
      "class": [ "team" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123123,
        "cId": 456,
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123123/teams/456"
        }
      ]
    },
  "links": [
    {
        "rel": ["self"],
        "href": "/api/orgs/123123"
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
        "rel": ["organization"],
        "href": "/api/orgs/123123"
    },
    {
        "rel": ["assignments"],
        "href": "/api/orgs/123123/classrooms/1/assignments"
    },
    {
      "rel": ["team"],
      "href": "/api/orgs/123123/classrooms/1/teams/5"
    }
  ]
}
```

#### Get Classroom (Teacher)

Teacher view of a Get Classroom request. This returns a single request. The user must be apart of the classroom to make such request.

```http
GET /api/orgs/{orgId}/classrooms/{classroomId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "classroom" ],
  "rel": [ "item" ],
  "properties": {
    "id": 9,
    "name": "LI61D",
    "state": "active",
    "schoolYear": "2021/22"
  },
  "entities": [
    {
      "class": [ "team" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123123,
        "cId": 456,
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123123/teams/456"
        }
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
        {"name": "cId", "type": "int"},
        {"name": "description", "type": "string"}
      ]
    },
    {
      "name": "delete-assignment",
      "title": "Delete Assignment",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/classrooms/{classId}/assignments"
    },
    {
      "name": "update-assignment",
      "title": "Update Organization",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/classrooms/{classId}/assignments",
      "type": "application/json",
      "field": [
        {"name": "releaseDate", "type": "date"},
        {"name": "cId", "type": "int"},
        {"name": "description", "type": "string"}
      ]
    },
    {
      "name": "update-classroom",
      "title": "Update Classroom",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/classrooms/{classId}",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"state": "state", "type":  "string"},
        {"name": "description", "type": "string"},
        {"name": "schoolYear", "type": "string"},
        {"name": "maxGroups", "type": "string"},
        {"name": "maxGroupMembers", "type": "string"}
      ]
    }
  ],
  "links": [
    {
        "rel": ["self"],
        "href": "/api/orgs/123123/classrooms/9"
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
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    },
    {
      "rel": ["organizations"],
      "href": "/api/orgs/123123"
    },
    {
      "rel": ["assignment"],
      "href": "/api/orgs/123123/classrooms/1/assignments"
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
