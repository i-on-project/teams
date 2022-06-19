type UriParam = string | number

export function makeHome(): string {
  return '/'
}


//Organizations
export function makeOrganizations(): string {
  return '/orgs'
}

export function makeOrganization(id: UriParam) {
  return `/orgs/${id}`
}
