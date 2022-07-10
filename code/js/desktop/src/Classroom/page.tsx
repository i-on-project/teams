import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Grid, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem, VerticalMenu } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { TeamsTable } from "../Teams/components/TeamsTable";
import { ClassroomInfo } from "./components/ClassroomInfo";

export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeClassroom(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload} orgId={orgId} classId={classId}></Body>}
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ resource, orgId, classId }: { resource: Resource, orgId: any, classId: any }) {

    const { setItems } = React.useContext(MenuContext)

    React.useEffect(() => {

        const menuItems: MenuItem[] = [
            {
                name: "Home",
                href: makeHome()
            },
            {
                name: "Organizations",
                href: makeOrganizations()
            },
            {
                name: "Organization",
                href: makeOrganization(orgId)
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId),
                isActive: true
            }
        ]
        setItems(menuItems)
    }, [])


    const subItems: MenuItem[] = [
        { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
        { name: 'Teams', href: makeTeams(orgId, classId) },
        { name: 'Requests', href: makeRequests(orgId, classId) },
        { name: 'Assignments', href: makeAssignments(orgId, classId) }
    ]


    return (
        <VerticalMenu subItems={subItems}>
            <ClassroomInfo resource={resource} orgId={orgId} classId={classId} />
            <Divider />
            <h1>Teams in Classroom</h1>
            {
                <TeamsTable entities={resource.entities} orgId={orgId} classId={classId}></TeamsTable>
            }
        </VerticalMenu>
    )
}