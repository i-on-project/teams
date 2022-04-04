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

### Standard [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

* `item`

## Actions

* [List Organizations](#list-organizations)

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
      "rel": ["page"],
      "hrefTemplate": "/api/orgs{?page,limit}"
    },
    {
      "rel": ["next"],
      "href": "/api/orgs?page=1&limit=10"
    }
  ]
}
```

#### Bad Request

```text
Status: 400 Bad Request
```

#### Unauthorized

```text
Status: 401 Unauthorized
```
