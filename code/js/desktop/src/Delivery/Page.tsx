import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeAssignment, makeAssignments, makeClassroom, makeDelivery, makeHome } from "../common/Uris";
import { DeliveryInfo } from "./components/DeliveryInfo";
import { TagsTable } from "../Tags/components/TagsTable";

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

    const { setItems } = React.useContext(MenuContext)
    const { orgId, classId, assId, delId } = useParams()

    React.useEffect(() => {

        const menuItems: MenuItem[] = [
            {
                name: "Home",
                href: makeHome()
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId)
            },
            {
                name: "Assignments",
                href: makeAssignments(orgId, classId)
            },
            {
                name: "Assignment",
                href: makeAssignment(orgId, classId, assId)
            },
            {
                name: "Delivery",
                href: makeDelivery(orgId, classId, assId, delId),
                isActive: true
            },
        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <DeliveryInfo resource={resource} />
            <Divider />
            <h1>Tags of the delivery</h1>
            {
                <TagsTable entities={resource.entities}></TagsTable>
            }
        </Container>
    )
}