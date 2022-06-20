type UriParam = string | number

export function makeHome(): string {
  return '/'
}


//Organizations
export function makeOrganizations(): string {
  return '/orgs'
}

export function makeOrganization(orgId: UriParam) {
  return `/orgs/${orgId}`
}

//Classrooms
export function makeClassrooms(orgId: UriParam): string {
  return `/orgs/${orgId}/classrooms`
}

export function makeClassroom(orgId: UriParam, classId: UriParam) {
  return `/orgs/${orgId}/classrooms/${classId}`
}

