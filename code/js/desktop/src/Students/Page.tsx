import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Divider, Loader } from "semantic-ui-react"
import { DefaultForm } from "../common/components/DefaultForm"
import { ErrorNOk, Error } from "../common/components/error"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { NothingToShow } from "../common/components/NothingToShow"
import { Action, Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { StudentsTable } from "./components/StudentsTable"

export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeStudentsClassroom(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <Body collection={payload} orgId={orgId} classId={classId}></Body>
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
                isActive: true,
                hasSubItems: true,
                subItems: [
                    { name: 'Description', href: makeClassroom(orgId, classId)},
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId), isActive: true},
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