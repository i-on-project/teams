type UriParam = string | number

//Organizations
export function makeOrganizations(): string {
  return '/orgs'
}

export function makeOrganization(id: any): import("history").To {
  return `/orgs/${id}`
}
