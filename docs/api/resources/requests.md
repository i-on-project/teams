# Requests

Represents a request to create a team, made by one student. Only available for teacher.

## Properties

### Domain specific

- `link` - Invite link for the creation.
    - mandatory
    - non editable
    - type: number
    - example: ``api/orgs/3/classrooms/4/invite-link``
- ``teamId`` - Indicates the team that is pending.
    - mandatory
    - editable
    - type: Date
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

* [List Requests](#list-requests)
* [Get Request](#get-request)

---

### Success Responses

#### List Requests

List all the Notes of a specific team.

```http
GET api/orgs/{orgId}/classrooms/{classroomId}/requests
```

```text
Status:  200 OK
```

```json
{
  "class": [ "requests", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "request" ],
      "rel": [ "item" ],
      "properties": {
        "link": "api/orgs/3/classrooms/4/invite_link",
        "teamId": "5"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "api/orgs/3/classrooms/4/requests/{teamId}"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/requests?page=0&limit=10"
    },
    {
    "rel": ["next"],
    "href": "api/orgs/3/classrooms/4/requests?page=1&limit=10"
    },
    {
    "rel": ["prev"],
    "href": "api/orgs/3/classrooms/4/requests?page=1&limit=10"
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

#### Get Request

This returns a single response.

```http
GET api/orgs/{orgId}/classrooms/{classroomId}/requests/{teamId}
```

```text
Status:  200 OK
```

```json
{
  "class": [
    "request"
  ],
  "rel": [
    "item"
  ],
  "properties": {
    "link": "api/orgs/3/classrooms/4/invite_link",
    "teamId": "5"
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
        "id": 9,
        "name": "li61d_g4",
        "state": "active"
      },
      "links": [
        {
          "rel": [
            "self"
          ],
          "href": "/api/orgs/123123/classrooms/1/teams/9"
        }
      ]
    }
  ],
  "actions": [
    {
      "name": "accept-request",
      "title": "Accept Request",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/class/{classId}/teams/{teamId}",
      "field": [
        {"name": "name", "type": "string"},
        {"name": "url", "type": "string"},
        {"name": "assId", "type": "number"}
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
      "rel": ["classroom"],
      "href": "/api/orgs/123123/classrooms/1"
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
