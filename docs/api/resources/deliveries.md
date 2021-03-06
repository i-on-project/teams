# Deliveries

Represents an assignment delivery.

## Properties

### Domain specific

- ``id`` - Id of the delivery.  
  - mandatory
  - editable
  - type: number
  - possible values: ``3``
- ``name`` - Name of the delivery.  
  - mandatory
  - editable
  - type: string
  - possible values: ``Phase 1.``
- ``date`` - Indicates date that the comment was made.
  - mandatory
  - editable
  - type: Date
  - possible values: ``2022-08-07``

### Media-type [Siren](https://github.com/kevinswiber/siren)

- `class`
- `properties`
- `entities`
- `links`
- `actions`

### Standard

- `item`
- `rel`

## Link Relations

### Domain Specific

- [assignment](./assignments.md#get-assignment-student)
- [delivery](./deliveries.md#get-delivery-teacher)
- home
- logout

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

- [self](https://www.iana.org/go/rfc4287)
- [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
- [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

- [Get delivery](#get-delivery)

---

### Success Responses

#### List Deliveries

List all the deliveries.

```http
GET /api/orgs/{orgId}}/classrooms/{classId}/assignments/{assignments}/deliveries
```

```text
Status:  200 OK
```

```json
{
  "class": [ "delivery", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "delivery" ],
      "rel": [ "item" ],
      "properties": {
        "id": "7",
        "name":"phase1",
        "date": "2022-05-08"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/5}/classrooms/6/assignments/66/deliveries/7"
        },
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "api/orgs/3/classrooms/4/assignments/66/deliveries"
    },
    {
      "rel": ["next"],
      "href": "api/orgs/3/classrooms/4/assignments/66/deliveries?page=1&limit=10"
    },
    {
      "rel": ["prev"],
      "href": "api/orgs/3/classrooms/4/assignments/66/deliveries?page=1&limit=10"
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
      "rel": ["organization"],
      "href": "/api/orgs/123123"
    },
    {
      "rel": ["classroom"],
      "href": "api/orgs/3/classrooms/4"
    },
    {
      "rel": ["assignments"],
      "href": "/api/orgs/5}/classrooms/6/assignments"
    }
  ]
}
```

#### Get Delivery (Students)

This returns a single delivery.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/assignments/{assId}/deliveries/{deliveryId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "delivery" ],
  "properties": {
    "id": "7",
    "name":"phase1",
    "date": "2022-05-08"
  },
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/assignments/23/delivery/3"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["deliveries"],
      "href": "/api/orgs/852/classrooms/1/assignments/123123/deliveries"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Delivery (Teacher)

This returns a single delivery.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/assignments/{assId}/deliveries/{deliveryId}
```

```text
Status:  200 OK
```

```json
{
  "class": [ "delivery" ],
  "properties": {
    "id": "7",
    "name":"phase1",
    "date": "2022-05-08"
  },
  "entities": [
    {
      "class": ["tags"],
      "rel": ["item"],
      "properties": {
        "id": 123123,
        "name": "li61d_g4",
        "state": "active"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123123/teams/123123"
        }
      ]
    }
  ],
  "actions": [
    {
      "name": "update-delivery",
      "title": "Update Delivery",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/class/{classId}/assignments/{assignmentId}/deliveries/{deliveryId}",
      "type": "application/json",
      "field": [
        {"name": "date", "type": "string"},
      ]
    },
    {
      "name": "delete-delivery",
      "title": "Delete Delivery",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/class/{classId}/assignments{assignmentId}/deliveries/{deliveryId}"
    },
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/assignments/23/delivery/3"
    },
    {
      "rel": ["home"],
      "href": "/api"
    },
    {
      "rel": ["deliveries"],
      "href": "/api/orgs/852/classrooms/1/assignments/123123/deliveries"
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
