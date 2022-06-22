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

export function makeClassroom(orgId: UriParam, classId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}`
}

//Students
export function makeStudentsClassroom(orgId: UriParam, classId: UriParam) {
  return `/orgs/${orgId}/classrooms/${classId}/students`
}

//Teams
export function makeTeams(orgId: UriParam, classId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}/teams`
}

export function makeTeam(orgId: UriParam, classId: UriParam, teamId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}/teams/${teamId}`
}

//Requests
export function makeRequests(orgId: UriParam, classId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}/requests`
}

export function makeRequest(orgId: UriParam, classId: UriParam, teamId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}/requests/${teamId}`
}

//Assignments
export function makeAssignments(orgId: UriParam, classId: UriParam): string {
  return `/orgs/${orgId}/classrooms/${classId}/assignments`
}