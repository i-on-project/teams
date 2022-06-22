import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Divider, Loader } from "semantic-ui-react"
import { BuildForm } from "../common/components/BuildForm"
import { ErrorNOk, Error } from "../common/components/error"
import { Fetch } from "../common/components/fetch"
import { BuildMenu, MenuItem } from "../common/components/Menu"
import { Action, Collection } from "../common/types/siren"
import { makeClassrooms, makeHome, makeOrganizations } from "../common/Uris"
import { OrganizationsTable } from "./components/OrganizationsTable"

export function Page() {

    const menuItems: MenuItem[] = [
        {
            name: "Home",
            href: makeHome()
        },
        {
            name: "Organizations",
            href: makeOrganizations()
        }
    ]

    return (
        <div>
            <BuildMenu items={menuItems} currItem={makeOrganizations()}></BuildMenu>
            <Fetch
                url={`/api${makeOrganizations()}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <Body collection={payload}></Body>
                }
                renderLoading={() => <Loader />}
                renderNok={message => <ErrorNOk message={message} />}
                renderError={error => <Error error={error} />}
            />
        </div>
    )
}


function Body({ collection }: { collection: Collection }) {
    return (
        <Container>
            <h1>Your Organizations</h1>
            <OrganizationsTable collection={collection}></OrganizationsTable>
            <Divider />
            {
                collection.actions.map((action: Action) =>
                    <BuildForm action={action} key={action.name}></BuildForm>
                )
            }
            <Divider hidden />
        </Container>
    )
}