import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Header, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { useMenu } from "../common/components/MenuContext";
import { Resource } from "../common/types/siren";
import { makeAssignment, makeAssignments, makeClassroom, makeDelivery, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { DeliveryInfo } from "./components/DeliveryInfo";
import { TagsTable } from "../Tags/components/TagsTable";
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext";

export function Page() {

    const { orgId, classId, assId, delId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeDelivery(orgId, classId, assId, delId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload} ></Body>}
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ resource }: { resource: Resource }) {

    const setItems = useMenu().setItems
    const { orgId, classId, assId, delId } = useParams()
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
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            },
            {
                name: menuItemNameContext.assignmentName,
                href: makeAssignment(orgId, classId, assId)
            },
            {
                name: menuItemNameContext.deliveryName,
                href: makeDelivery(orgId, classId, assId, delId),
                isActive: true
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <DeliveryInfo resource={resource} />
            <Divider />
            <Header as='h2' floated="left">Tags of the delivery</Header>
            <Header as='h2' floated="right" color={resource.entities.length > 0 ? "green" : "red"}>{resource.entities.length} Tags</Header>
            {resource.entities.length != 0 &&
                <TagsTable entities={resource.entities}></TagsTable>
            }
        </Container>
    )
}