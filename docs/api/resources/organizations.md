# Organizations

An organization represents a [GitHub Organization](https://docs.github.com/en/organizations).

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
* [Classrooms](classrooms.md#list-classrooms)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Organizations](#list-organizations)
* [Get Organization - Student](#get-organization-student)
* [Ger Organization - Teacher](#get-organization-teacher)

---

### Success Responses

#### List Organizations

List all the organizations that the user has access to, only accessible to teachers.

```http
GET /api/orgs
```

```text
Status:  200 OK
```

```json
{
  "class": [ "organization", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "organization" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123,
        "name": "i-on-project",
        "description": "GitHub organization for the i-on projects"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/123"
        },
        {
          "rel": ["classrooms"],
          "href": "/api/orgs/123/classrooms"
        },
        {
          "rel": ["github"],
          "href": "https://github.com/i-on-project"
        },
        {
          "rel": ["avatar"],
          "href": "https://avatars.githubusercontent.com/u/59561360?s=200&v=4"
        },
      ]
    }
  ],
  "actions": [
    {
      "name": "create-organization",
      "title": "Create Organization",
      "method": "POST",
      "href": "/api/orgs/",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"}
        {"name": "description", "type": "string"},
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
  "class": [
    "organization"
  ],
  "properties": {
    "id": 123123,
    "name": "i-on-project",
    "description": "GitHub organization for the i-on projects"
  },
  "entities": [
    {
      "class": [
        "classroom"
      ],
      "rel": [
        "item"
      ],
      "properties": {
        "id": 1,
        "name": "LI61D",
        "state": "active",
        "schoolYear": "2021/22"
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
      "rel": [
        "logout"
      ],
      "href": "/api/logout"
    },
    {
      "rel": [
        "classrooms"
      ],
      "href": "/api/orgs/123123/classrooms"
    },
    {
      "rel": [
        "organizations"
      ],
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
        "description": "Day class 6 semester number 1."
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
      "href": "/api/orgs/{orgId}/classrooms",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"}
        {"name": "description", "type": "string"},
        {"name": "schoolYear", "type": "string"},
        {"name": "maxGroups", "type": "string"},
        {"name": "maxGroupMembers", "type": "string"}
      ]
    },
    {
      "name": "update-organization",
      "title": "Update Organization",
      "method": "PUT",
      "href": "/api/orgs/{orgId}",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"state": "state", "type":  "string"},
        {"name": "description", "type": "string"},
      ]
    },
    {
      "name": "delete-organization",
      "title": "Delete Organization",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}",
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
      "rel": ["logout"],
      "href": "/api/logout"
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
