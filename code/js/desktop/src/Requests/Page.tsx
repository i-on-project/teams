import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { useMenu } from "../common/components/MenuContext"
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext"
import { NothingToShow } from "../common/components/NothingToShow"
import { Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { RequestsTable } from "./components/RequestsTable"

/**
 * Function represents a list of requests.
 */
export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeRequests(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <Body collection={payload} orgId={orgId} classId={classId}></Body>
                }
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

/**
 * Body function represents the body of the page. It is responsible for displaying the relevant information to the user.
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
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId), isActive: true },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            }
        ]

        setItems(menuItems)
    }, [])

    return (
        collection.entities.length != 0 ?
            <Container>
                <h1>Requests for creating a team</h1>
                <RequestsTable entities={collection.entities} orgName={menuItemNameContext.orgName}/>
            </Container>
            :
            <NothingToShow>No Request to show.</NothingToShow>
    )
}