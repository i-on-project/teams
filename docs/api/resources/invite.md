# Invite

Represents an invitation to a classroom. Only available for teacher.

## Properties
### Domain Specif

- `classroom-name` - Name of the classroom in question
  - mandatory
  - non editable
  - type: number
  - example: ``api/orgs/3/classrooms/4/invite-link``

### Media-type [Siren](https://github.com/kevinswiber/siren)

* `class`
* `properties`
* `entities`
* 
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

* [Get Link](#get-link)

---

### Success Responses

#### Get Link

This returns a single response.

```http
GET api/orgs/{orgId}/classrooms/{classId}/invite-link/{code}
```

```text
Status:  200 OK
```

```json
{
  "class": [
    "invite-link"
  ],
  "rel": [
    "item"
  ],
  "properties": { 
    "classroom-name": "62d_daw"
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
        "id": 9,
        "name": "LI61D",
        "description": "Class for Web application development.",
        "state": "active",
        "schoolYear": "2021/22"
      },
      "links": [
        {
          "rel": [
            "self"
          ],
          "href": "/api/orgs/123123/classrooms/9"
        }
      ]
    }
  ],
  "actions": [
    {
      "name": "create-team",
      "title": "Create Team",
      "method": "POST",
      "href": "/api/orgs/{orgId}/classrooms/{classId}/teams",
      "field": [
        {"name": "name", "type": "string"}
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/9/invite-link/89435y345h"
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