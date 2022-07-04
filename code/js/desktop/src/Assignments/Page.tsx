import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Divider, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { Action, Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeClassrooms, makeHome, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { AssignmentsTable } from "./components/AssignmentsTable"

export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeAssignments(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <Body collection={payload}></Body>
                }
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ collection }: { collection: Collection }) {

    const { setItems } = React.useContext(MenuContext)
    const { orgId, classId } = useParams()

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
                name: "Classrooms",
                href: makeClassrooms(orgId),
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId),
                isActive: true,
                isDropDown: true,
                dropDownOptions: [
                    { name: 'This', href: makeClassroom(orgId, classId)},
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId), isActive: true}
                ]
            }
        ]

        setItems(menuItems)
    }, [])

    return (
        <Container>
            <h1>Classroom's assignments</h1>
            <AssignmentsTable collection={collection}></AssignmentsTable>
        </Container>
    )
}