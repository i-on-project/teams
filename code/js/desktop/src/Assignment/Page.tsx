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
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext";
import { NothingToShow } from "../common/components/NothingToShow";

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

/**
 * Body function represents the page body. It is responsible for displaying the relevant information to the user.
 */
function Body({ resource }: { resource: Resource }) {

    const setItems = useMenu().setItems
    const { orgId, classId, assId } = useParams()
    
    const menuItemNameContext = useMenuItemNameContext()

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
                name: menuItemNameContext.orgName,
                href: makeOrganization(orgId)
            },
            {
                name: menuItemNameContext.className,
                href: makeClassroom(orgId, classId),
                hasSubItems: true,
                subItems: [
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId)},
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId)}
                ]
            },
            {
                name: menuItemNameContext.assignmentName,
                href: makeAssignment(orgId, classId, assId),
                isActive: true
            }

        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <AssignmentInfo resource={resource} />
            <Divider />
            { resource.entities.length != 0 ?
                <React.Fragment>
                    <h1>Deliveries of the assignment</h1>
                    {
                        <DeliveriesTable entities={resource.entities}></DeliveriesTable>
                    }
                </React.Fragment>
                :
                <NothingToShow>No deliveries for this assignment.</NothingToShow>
            }
        </Container>
    )
}