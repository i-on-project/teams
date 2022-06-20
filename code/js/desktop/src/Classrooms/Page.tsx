import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Divider, Loader } from "semantic-ui-react"
import { BuildForm } from "../commons/components/BuildForm"
import { ErrorNOk, Error } from "../commons/components/error"
import { Fetch } from "../commons/components/fetch"
import { BuildMenu, MenuItem } from "../commons/components/Menu"
import { Action, Collection } from "../commons/types/siren"
import { makeClassrooms, makeHome, makeOrganizations } from "../commons/Uris"
import { ClassroomsTable } from "./Components/ClassroomsTable"

export function Page() {

    const {orgId} = useParams()
    
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
            name: "Classrooms",
            href: makeClassrooms(orgId)
        }
    ]

    return (
        <Fetch
            url={`/api${makeClassrooms(orgId)}`}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={ (payload) =>
                <div>
                    <BuildMenu items={menuItems} currItem={makeClassrooms(orgId)}></BuildMenu>
                    <Body collection={payload}></Body>
                </div>
            }
            renderLoading={() => <Loader /> }
            renderNok={message => {
                return <ErrorNOk message={message} />
            }}
            renderError={error => <Error error={error} />}
        />
    )
}


function Body({ collection }: { collection: Collection }) {
    console.log(collection)

    return (
        <Container>
            <h1>Your Classrooms</h1>
            <ClassroomsTable entities={collection.entities}></ClassroomsTable>
        </Container>
    )
}