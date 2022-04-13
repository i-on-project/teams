# Teams

teams is _**Not yet completed**_

**STARTED BY AFONSO MACHADO**

## Properties

### Domain specific

- `id` - Unique and stable global identifier
  - mandatory
  - non editable
  - type: number
  - example: ``1``
- ``cId`` - Identifies the Classroom that this team belongs 
  - mandatory
  - editable
  - type: number
  - example: ``2``
- ``state`` - Indicates the state of the team, having 3 possible values.
  - mandatory
  - editable
  - type: string
  - possible values: ``pending``, ``active``, ``inactive``

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

* [Classrooms](/docs/api/resources/classrooms.md#list-user-classrooms)
* [Repo](/docs/api/resources/Repo.md#get-repo)
* [Assignment](/docs/api/resources/Assignment.md#get-Assignment)
* logout
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* home - Home page

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Classrooms](#list-user-classrooms)
* [Get Assignment](#get-assignment)
* [Get Repo](#get-Repo)

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
        "cId": 456,
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "GET /api/orgs/852/classrooms/123123/teams/456"
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
    }
  ]
}
```

#### Get Team

This returns a single response. The user must be apart of the team to make such request.

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
    "cId": 1,
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
        "name": "assignment1",
        "tId": 234342,
        "assId": 7
      },
      "links": [
        {
          "rel": [
            "self"
          ],
          "href": "/api/orgs/123123/classrooms/1/teams/234342"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": [
        "self"
      ],
      "href": "/api/orgs/123123/classrooms/1/teams/234342"
    },
    {
      "rel": [
        "home"
      ],
      "href": "/api"
    },
    {
      "rel": [
        "github"
      ],
      "href": "https://github.com/i-on-project"
    },
    {
      "rel": [
        "avatar"
      ],
      "href": "https://avatars.githubusercontent.com/u/59561360?s=200&v=4"
    },
    {
      "rel": [
        "classrooms"
      ],
      "href": "/api/orgs/123123/classrooms"
    },
    {
      "rel": [
        "assignment"
      ],
      "href": "/api/orgs/123123/classrooms/1/assignment/7"
    },
    {
      "rel": [
        "repo"
      ],
      "href": "/api/orgs/123123/classrooms/1/teams/234342/repo/3"
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
