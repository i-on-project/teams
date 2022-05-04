# Deliveries

Represents an assignment delivery.

## Properties

### Domain specific

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
    "date": "2022-05-08"
  },
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
      "href": "/api/orgs/{orgId}/class/{classId}/assignments/{assignmentId}/deliveries/{deliveryid}",
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
      "href": "/api/orgs/123123/classrooms/1/teams/234342"
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
