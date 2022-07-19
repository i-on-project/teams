import * as React from "react"
import { Container, Divider, Loader } from "semantic-ui-react"
import { DefaultForm } from "../common/components/DefaultForm"
import { ErrorNOk, Error } from "../common/components/error"
import { Fetch } from "../common/components/fetch"
import { MenuItem } from "../common/components/Menu"
import { MenuContext } from "../common/components/MenuStatus"
import { UriContext } from "../common/PagingContext"
import { Action, Collection } from "../common/types/siren"
import { makeHome, makeOrganizations } from "../common/Uris"
import { OrganizationsTable } from "./components/OrganizationsTable"

export function Page() {

    const [uri, setUri] = React.useState(`/api${makeOrganizations()}`)

    return (
        <div>
            <Fetch
                url={uri}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <UriContext.Provider value={{ uri, setUri }} >
                        <Body collection={payload} />
                    </UriContext.Provider>
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
                    <DefaultForm action={action} key={action.name}></DefaultForm>
                )
            }
        </Container>
    )
}