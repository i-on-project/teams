# Classrooms

A classroom represents a classroom of a given course, it is also associated with a given repo.

Classrooms is _**Not yet completed**_

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
* `state` - Classroom status
  * mandatory
  * non editable
  * type: **string**
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

* [Organizations](#list-organizations)
* [Classrooms](/docs/api/resources/classrooms.md#list-classrooms)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Classrooms](#list-user-classrooms)
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
  "class": [ "classroom", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "classroom" ],
      "rel": [ "item" ],
      "properties": {
        "name": "LI61D",
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/classrooms"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs?page=0&limit=10"
    },
    {
      "rel": ["next"],
      "href": "/api/orgs?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "/api/orgs?page=1&limit=10"
    }
  ]
}
```

#### Get Organization (Student)

Student view of a Get Organization request. This returns a single request. The user must be apart of the organization to make such request.

```http
GET /api/orgs/{orgId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "organization" ],
  "rel": [ "item" ],
  "properties": {
    "id": 123123,
    "name": "i-on-project",
    "description": "GitHub organization for the i-on projects"
  },
  "entities": [
    {
      "class": [ "classroom" ],
      "rel": [ "item" ],
      "properties": {
        "id": 1,
        "name": "LI61D",
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/class/1"
        }
      ]
    }
  ],
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
        "rel": ["classrooms"],
        "href": "/api/orgs/123123/classrooms"
    },
    {
        "rel": ["organizations"],
        "href": "/api/orgs"
    }
  ]
}
```

#### Get Organization (Teacher)

Teacher view of a Get Organization request. This returns a single request. The user must be apart of the organization to make such request.

```http
GET /api/orgs/{orgId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "organization" ],
  "rel": [ "item" ],
  "properties": {
    "id": 123123,
    "name": "i-on-project",
    "description": "GitHub organization for the i-on projects"
  },
  "entities": [
    {
      "class": [ "classroom" ],
      "rel": [ "item" ],
      "properties": {
        "id": 1,
        "name": "LI61D",
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123123/class/1"
        }
      ]
    }
  ],
  "actions": [
    {
      "name": "create-classroom",
      "title": "Create Classroom",
      "method": "POST",
      "href": "/api/orgs/{orgId}/class",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"name": "description", "type": "string"},
        {"name": "schoolYear", "type": "string"},
        {"name": "maxGroups", "type": "string"},
        {"name": "maxGroupMembers", "type": "string"}
      ]
    },
    {
      "name": "delete-classroom",
      "title": "Delete Classroom",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/class/{classId}"
    },
    {
      "name": "update-organization",
      "title": "Update Organization",
      "method": "PUT",
      "href": "/api/orgs/{orgId}",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"name": "description", "type": "string"},
      ]
    }
  ],
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
        "rel": ["classrooms"],
        "href": "/api/orgs/123123/classrooms"
    },
    {
        "rel": ["organizations"],
        "href": "/api/orgs"
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
