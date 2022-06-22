import * as React from "react"
import { Container, Divider, Loader } from "semantic-ui-react"
import { BuildForm } from "../common/components/BuildForm"
import { ErrorNOk, Error } from "../common/components/error"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { Action, Collection } from "../common/types/siren"
import { makeHome, makeOrganizations } from "../common/Uris"
import { OrganizationsTable } from "./components/OrganizationsTable"

export function Page() {

    return (
        <div>
            <Fetch
                url={`/api${makeOrganizations()}`}
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

    React.useEffect(() => {
        const menuItems: MenuItem[] = [
            {
                name: "Home",
                href: makeHome()
            },
            {
                name: "Organizations",
                href: makeOrganizations(),
                isActive: true
            }
        ]

        setItems(menuItems)
    }, [])

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
        </Container>
    )
}