# Repos

Represents a GitHub repository, as the solution for an assignment.

## Properties

### Domain specific

- `id` - Unique and stable global identifier
  - mandatory
  - non editable
  - type: number
  - example: ``1``
- ``name`` - Indicates the name.
  - mandatory
  - editable
  - type: string
  - possible values: ``leic_daw_g4``
- ``githubUri`` - URL to the GitHub repository
  - mandatory
  - editable
  - type: string
  - example: ``https://github.com/i-on-project/teams``
- ``tId`` - Identifies the Team that this repo belongs
  - mandatory
  - editable
  - type: number
  - example: ``2``
- ``assId`` - Identifies the Assignment that this repo is working on
  - mandatory
  - editable
  - type: number
  - example: ``4``

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

- [team](./teams.md#get-team-student)
- tags
- home - Home page
- logout
- github - GitHub Organization URI
- avatar - GitHub Organization Avatar URI

### Standard - [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

- [self](https://www.iana.org/go/rfc4287)
- [next](https://html.spec.whatwg.org/multipage/links.html#link-type-next)
- [prev](https://html.spec.whatwg.org/multipage/links.html#link-type-prev)

## Actions

- [List Repos](#list-repos)
- [Get Repo - Student](#get-repo-student)
- [Get Repo - Teacher](#get-repo-teacher)

---

### Success Responses

#### List Repos

List all the Repos of a specific team.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos
```

```text
Status:  200 OK
```

```json
{
  "class": [ "repo", "collection" ],
  "properties": {
    "pageIndex": 0,
    "pageSize": 1
  },
  "entities": [
    {
      "class": [ "repo" ],
      "rel": [ "item" ],
      "properties": {
        "id": 88,
        "githubUri": "https://github.com/i-on-project/teams",
        "name": "li61d_g4_repo"
      },
      "links": [
        {
          "rel": ["self"],
          "href": "/api/orgs/852/classrooms/123123/teams/4/repos/88"
        }
      ]
    }
  ],
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/852/classrooms/123123/teams/4/repos"
    },
    {
    "rel": ["next"],
    "href": "/api/orgs/852/classrooms/123123/teams/4/repos?page=1&limit=10"
    },
    {
    "rel": ["prev"],
    "href": "/api/orgs/852/classrooms/123123/teams/4/repos?page=1&limit=10"
    },
    {
    "rel": ["team"],
    "href": "/api/orgs/852/classrooms/123123/teams/4"
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

#### Get Repo (Student)

This returns a single response.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos/{repoId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["repo"],
  "properties": {
    "id": 88,
    "url": "https://github.com/i-on-project/teams",
    "name": "li61d_g4_repo"
  },
  "links": [
    {
      "rel": ["self"],
      "href": "/api/orgs/123123/classrooms/1/teams/1/repos/234342"
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
      "rel": ["repos"],
      "href": "/api/orgs/123123/classrooms/1/teams/1/repos/5"
    },
    {
      "rel": ["team"],
      "href": "/api/orgs/123123/classrooms/1/teams/5"
    },
    {
      "rel": ["assignment"],
      "href": "/api/orgs/123123/classrooms/1/assignments/5"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    }
  ]
}
```

#### Get Repo (Teacher)

This returns a single response.

```http
GET /api/orgs/{orgId}/classrooms/{classId}/teams/{teamId}/repos/{repoId}
```

```text
Status:  200 OK
```

```json
{
  "class": ["repo"],
  "properties": {
    "id": 88,
    "url": "https://github.com/i-on-project/teams",
    "name": "li61d_g4_repo"
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
      "name": "update-repo",
      "title": "Update Repo",
      "method": "PUT",
      "href": "/api/orgs/{orgId}/class/{classId}/teams/{teamId}/repos/{repoID}",
      "type": "application/json",
      "field": [
        {"name": "name", "type": "string"},
        {"name": "url", "type": "string"},
        {"name": "assId", "type": "number"}
        
      ]
    },
    {
      "name": "delete-repo",
      "title": "Delete Repo",
      "method": "DELETE",
      "href": "/api/orgs/{orgId}/class/{classId}/teams/{teamId}/repos/{repoId}"
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
      "rel": ["tags"],
      "href": "/api/orgs/123123/classrooms/1/teams/4/repos/88/tags"
    },
    {
      "rel": ["repos"],
      "href": "/api/orgs/123123/classrooms/1/teams/1/repos/5"
    },
    {
      "rel": ["team"],
      "href": "/api/orgs/123123/classrooms/1/teams/5"
    },
    {
      "rel": ["assignment"],
      "href": "/api/orgs/123123/classrooms/1/assignments/5"
    },
    {
      "rel": ["logout"],
      "href": "/api/logout"
    },
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
