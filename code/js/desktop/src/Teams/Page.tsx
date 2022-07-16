import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Header, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { NothingToShow } from "../common/components/NothingToShow"
import { Collection } from "../common/types/siren"
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris"
import { TeamsTable } from "./components/TeamsTable"

export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeTeams(orgId, classId)}`}
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
                    { name: 'Description', href: makeClassroom(orgId, classId) },
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
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
                <Header as='h1'>Tags of the delivery</Header>
                <Header size="tiny" color='grey'>{collection.entities.length} Teams</Header>
                <TeamsTable entities={collection.entities} orgId={orgId} classId={classId}></TeamsTable>
            </Container>
            :
            <NothingToShow>No Teams to show.</NothingToShow>
    )
}