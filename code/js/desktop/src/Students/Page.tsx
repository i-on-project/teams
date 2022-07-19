import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Divider, Loader } from "semantic-ui-react"
import { DefaultForm } from "../common/components/DefaultForm"
import { ErrorNOk, Error } from "../common/components/error"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { NothingToShow } from "../common/components/NothingToShow"
import { UriContext } from "../common/PagingContext"
import { Action, Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { StudentsTable } from "./components/StudentsTable"

export function Page() {

    const { orgId, classId } = useParams()
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


function Body({ collection, orgId, classId }: { collection: Collection, orgId: any, classId: any }) {

    const { setItems } = React.useContext(MenuContext)

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
                name: "Organization",
                href: makeOrganization(orgId)
            },
            {
                name: "Classroom",
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