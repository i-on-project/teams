# Home

Home is _**Not yet completed**_

## Properties

### Domain specific

* `Organizations` - List of Organizations.
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
* `Classrooms` - List of Classrooms.
    * (...)
* ``Teams`` - List of Teams.
    * (...)
* `Deliveries` - Upcoming Deliveries. 
    * (...)


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

* [Organizations](./organizations.md#list-organizations)
* [Classrooms](./classrooms.md#list-classrooms)
* [Teams](./teams.md#list-teams)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI
* logout

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions
* [Home - Student](#home)
* [Home - Teacher](#home)

---

### Success Responses

#### Home Student

Request for home page of the Student.

```http
GET /api
```

```text
Status:  200 OK
```

```json
{
  "class": [ "classrooms", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "classrooms" ],
      "rel": [ "item" ],
      "properties": {
        "id": 123123,
        "name": "LI61D",
        "state": "active",
        "schoolYear": "2021/22"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123123"
        },
        {
          "rel": ["teams"],
          "href": "/api/orgs/852/classrooms/123123/teams"
        },
        {
          "rel": ["assignments"],
          "href": "/api/orgs/852/classrooms/123123/assignments"
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
    },
    {
      "rel": ["organizations"],
      "href": "/api/orgs"
    }
  ]
}
```

#### Home Teacher

Request for home page of the Teacher.


```http
GET /api
```

```text
Status:  200 OK
```

```json
{
  "class": [ "organizations", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "organization" ],
      "rel": [ "item" ],
      "properties": {
        "id": 852,
        "name": "i-on-project",
        "description": "GitHub organization for the i-on projects"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852"
        },
        {
          "rel": ["classrooms"],
          "href": "/api/orgs/852/classrooms"
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


