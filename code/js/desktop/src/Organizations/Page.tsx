import * as React from "react"
import { Container, Loader } from "semantic-ui-react"
import { ErrorNOk, Error } from "../commons/components/error"
import { Fetch } from "../commons/components/fetch"
import { Collection } from "../commons/types/siren"
import { makeOrganizations } from "../commons/Uris"
import { OrganizationsTable } from "./components/OrganizationsTable"
//import * as remote from "@electron/remote";

export function Page() {

    /*const net = remote.net
    const request = net.request({
        method: 'GET',
        protocol: 'https:',
        hostname: 'localhost',
        port: 8080,
        path: '/api/organizations',
        credentials: "include"
      })

    request.on('response', (response) => {
        console.log(response)
        response.on('data', (chunk) => {
            console.log('BODY: '+ chunk)
        })
    })*/

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
        </Container>
    )
}