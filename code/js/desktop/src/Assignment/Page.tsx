import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { Resource } from "../common/types/siren";
import { makeAssignment, makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { AssignmentInfo } from "./components/AssignmentInfo";
import { DeliveriesTable } from "../Deliveries/components/DeliveriesTable";
import { useMenu } from "../common/components/MenuContext";

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

    const setItems = useMenu().setItems
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
                    { name: 'Description', href: makeClassroom(orgId, classId)},
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId)},
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId)}
                ]
            },
            {
                name: "Assignment",
                href: makeAssignment(orgId, classId, assId),
                isActive: true
            }

        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <AssignmentInfo resource={resource} />
            { resource.entities.length != 0 &&
                <React.Fragment>
                    <Divider />
                    <h1>Deliveries of the assignment</h1>
                    {
                        <DeliveriesTable entities={resource.entities}></DeliveriesTable>
                    }
                </React.Fragment>
            }
        </Container>
    )
}