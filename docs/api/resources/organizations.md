# Organizations

An organization represents a [GitHub Organization](https://docs.github.com/en/organizations).

## Properties

### Domain specific

* `id` - Unique and stable global identifier
  * mandatory
  * non editable
  * type: **number**
  * example: `1`
* `name` - Unique and short name that defined the GitHub Organization
  * mandatory
  * non editable
  * type: **string**
  * example: `Some Organization`
* `description` - Description that characterizes GitHub Organization
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

### Standard

* `item`
* `rel`

## Link Relations

### Domain Specific

* [Organizations](#list-organizations)
* github - GitHub Organization URI
* avatar - GitHub Organization Avatar URI

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* [self](https://www.iana.org/go/rfc4287)
* [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
* [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

* [List Organizations](#list-organizations)
* [Get Organization](#get-organization)

---

### List Organizations

List all the organizations that the user has access to.

```http
GET /api/orgs
```

#### Success Response

```text
Status:  200 OK
```

```json
{
    "class": [ "organization", "collection" ],
    "properties": {
        "pageIndex": 0,
        "pageSize": 2
    },
    "entities": [
        {
            "class": [ "organization" ],
            "rel": [ "item" ],
            "properties": {
                "id": 123123,
                "name": "i-on-project",
                "description": "GitHub organization for the i-on projects"
            },
            "links": [
                {
                    "rel": ["self"],
                    "href": "/api/orgs/123123"
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
                    "rel": ["organizations"],
                    "href": "/api/orgs"
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

#### Bad Request

```text
Status: 400 Bad Request
```

```json
{
  "type": "PROBLEM URI",
  "title": "The request parameters are invalid.",
  "status": 400,
  "detail": "Some parameters are missing or are of an invalid type."
}
```

#### Unauthorized

```text
Status: 401 Unauthorized
```

```json
{
  "type": "PROBLEM URI",
  "title": "The request is unauthorized.",
  "status": 401,
  "detail": "User must be authenticated to perform this request."
}
```

### Get Organization

Get a single organization. Both the user and the GitHub App need to be in the organization.

```http
GET /api/orgs/{orgId}
```

#### Success Response

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
  "links": [
      {
          "rel": ["self"],
          "href": "/api/orgs/123123"
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

#### Bad Request

```text
Status: 400 Bad Request
```

```json
{
  "type": "PROBLEM URI",
  "title": "The request parameters are invalid.",
  "status": 400,
  "detail": "Some parameters are missing or are of an invalid type."
}
```

#### Unauthorized

```text
Status: 401 Unauthorized
```

```json
{
  "type": "PROBLEM URI",
  "title": "The request is unauthorized.",
  "status": 401,
  "detail": "User must be authenticated to perform this request."
}
```

#### Not Found

```text
Status: 404 Not Found
```

```json
{
  "type": "PROBLEM URI",
  "title": "Resource not found.",
  "status": 404,
  "detail": "The requested resource could not be found."
}
```
