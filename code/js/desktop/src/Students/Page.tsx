import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { useMenu } from "../common/components/MenuContext"
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext"
import { NothingToShow } from "../common/components/NothingToShow"
import { UriContext } from "../common/components/UriContext"
import { Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { StudentsTable } from "./components/StudentsTable"

/**
 * Students page, displays a list of students.
 */
export function Page() {

    const { orgId, classId } = useParams()
    //Uri state used for paging
    const [uri, setUri] = React.useState(`/api${makeStudentsClassroom(orgId, classId)}`)

    return (
        <div>
            <Fetch
                url={uri}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <UriContext.Provider value={{ uri, setUri }} >
                        <Body collection={payload} orgId={orgId} classId={classId}></Body>
                    </UriContext.Provider>
                }
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

/**
 * Body function, represents the body of the page. It is responsible for displaying relevant information to the user.
 */
function Body({ collection, orgId, classId }: { collection: Collection, orgId: any, classId: any }) {

    const setItems = useMenu().setItems
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
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId), isActive: true },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        collection.entities.length != 0 ?
            <Container>
                <h1>Students in classroom</h1>
                <StudentsTable collection={collection}></StudentsTable>
            </Container>
            :
            <NothingToShow>No Students in this classroom.</NothingToShow>
    )
}