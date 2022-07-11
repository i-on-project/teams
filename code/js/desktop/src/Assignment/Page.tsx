import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeAssignment, makeAssignments, makeClassroom, makeClassrooms, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { AssignmentInfo } from "./components/AssignmentInfo";
import { DeliveriesTable } from "../Deliveries/components/DeliveriesTable";

export function Page() {

    const { orgId, classId, assId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeAssignment(orgId, classId, assId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload} ></Body>}
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ resource }: { resource: Resource }) {

    const { setItems } = React.useContext(MenuContext)
    const { orgId, classId, assId } = useParams()

    React.useEffect(() => {

        const menuItems: MenuItem[] = [
            {
                name: "Home",
                href: makeHome()
            },
            {
                name: "Organizations",
                href: makeOrganizations(),
            },
            {
                name: "Organization",
                href: makeOrganization(orgId),
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId),
                hasSubItems: true,
                subItems: [
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            },
            {
                name: "Assignment",
                href: makeAssignment(orgId, classId, assId),
                isActive: true
            },

        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <AssignmentInfo resource={resource} />
            <Divider />
            <h1>Deliveries of the assignment</h1>
            {
                <DeliveriesTable entities={resource.entities}></DeliveriesTable>
            }
        </Container>
    )
}