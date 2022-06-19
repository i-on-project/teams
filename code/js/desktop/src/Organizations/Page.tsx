import * as React from "react"
import { Container, Divider, Loader } from "semantic-ui-react"
import { BuildForm } from "../commons/components/BuildForm"
import { ErrorNOk, Error } from "../commons/components/error"
import { Fetch } from "../commons/components/fetch"
import { Action, Collection } from "../commons/types/siren"
import { makeOrganizations } from "../commons/Uris"
import { OrganizationsTable } from "./components/OrganizationsTable"

export function Page() {

    return (
        <Fetch
            url={`/api${makeOrganizations()}`}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <Body collection={payload}></Body>
            }
            renderLoading={() =>
                <Container>
                    <Loader />
                </Container>
            }
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
            <Divider></Divider>
            {
                collection.actions.map((action: Action) =>
                        <BuildForm action={action}></BuildForm>
                )
            }
        </Container>
    )
}