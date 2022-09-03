import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { useMenu } from "../common/components/MenuContext"
import { NothingToShow } from "../common/components/NothingToShow"
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext"
import { UriContext } from "../common/components/UriContext"
import { Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { AssignmentsTable } from "./components/AssignmentsTable"

/**
 * Function represents a assignments list page.
 */
export function Page() {

    const { orgId, classId } = useParams()
    const [uri, setUri] = React.useState(`/api${makeAssignments(orgId, classId)}`)

    return (
        <div>
            <Fetch
                url={uri}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <UriContext.Provider value={{ uri, setUri }} >
                        <Body collection={payload}></Body>
                    </UriContext.Provider>
                }
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

/**
 * Body function represents the page body. It is responsible for displaying the relevant information to the user.
 */
function Body({ collection }: { collection: Collection }) {

    const setItems = useMenu().setItems
    const { orgId, classId } = useParams()
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
                    { name: 'Assignments', href: makeAssignments(orgId, classId), isActive: true }
                ]
            }
        ]

        setItems(menuItems)
    }, [])

    const sugestion = {
        message: "Go to Classroom description to create an Assignment.",
        href: makeClassroom(orgId, classId)
    }

    return (
        collection.entities.length != 0 ?
            <Container>
                <h1>Classroom's assignments</h1>
                <AssignmentsTable collection={collection}></AssignmentsTable>
            </Container>
            :
            <NothingToShow sugestion={sugestion}>No Assignements to show.</NothingToShow>
    )
}