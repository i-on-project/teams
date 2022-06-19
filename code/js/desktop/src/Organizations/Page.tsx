import * as React from "react"
import { Container, Divider, Loader } from "semantic-ui-react"
import { BuildForm } from "../commons/components/BuildForm"
import { ErrorNOk, Error } from "../commons/components/error"
import { Fetch } from "../commons/components/fetch"
import { BuildMenu, MenuItem } from "../commons/components/Menu"
import { Action, Collection } from "../commons/types/siren"
import { makeHome, makeOrganizations } from "../commons/Uris"
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
        <Fetch
            url={`/api${makeOrganizations()}`}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <div>
                    <BuildMenu items={menuItems} currItem={makeOrganizations()}></BuildMenu>
                    <Body collection={payload}></Body>
                </div>
            }
            renderLoading={() => <Loader /> }
            renderNok={message => <ErrorNOk message={message} />}
            renderError={error => <Error error={error} />}
        />
    )
}


function Body({ collection }: { collection: Collection }) {
    return (
        <Container>
            <h1>Your Organizations</h1>
            <OrganizationsTable collection={collection}></OrganizationsTable>
            <Divider/>
            {
                collection.actions.map((action: Action) =>
                        <BuildForm action={action}></BuildForm>
                )
            }
            <Divider hidden/>
        </Container>
    )
}