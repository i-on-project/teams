import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeAssignment, makeAssignments, makeClassroom, makeClassrooms, makeHome } from "../common/Uris";
import { AssignmentInfo } from "./components/AssignmentInfo";
import { DeliveriesTable } from "../Deliveries/components/DeliveriesTable";

export function Page() {

    const { orgId, classId, assId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeAssignment(orgId, classId, assId)}`}
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
                name: "Classrooms",
                href: makeClassrooms(orgId)
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId)
            },
            {
                name: "Assignments",
                href: makeAssignments(orgId, classId)
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