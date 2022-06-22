import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { ErrorNOk, Error } from "../common/components/error";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
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
                renderNok={message => <ErrorNOk message={message} />}
                renderError={error => <Error error={error} />}
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
                name: "Classroom",
                href: makeClassroom(orgId, classId),
                isActive: true,
                isDropDown: true,
                dropDownOptions: [
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId)}
                ]
            }
        ]

        setItems(menuItems)
    }, [])

    return (
        <Container>
            <ClassroomInfo resource={resource} />
            <Divider />
            <h1>Teams in this classrooms</h1>
            {
                <TeamsTable entities={resource.entities} orgId={orgId} classId={classId}></TeamsTable>
            }
        </Container>
    )
}