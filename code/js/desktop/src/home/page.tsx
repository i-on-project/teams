import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Container, Divider, Loader } from 'semantic-ui-react'
import { ErrorNOk, Error } from '../commons/components/error'
import { Fetch } from '../commons/components/fetch'
import { BuildMenu } from '../commons/components/Menu'
import { makeHome, makeOrganizations } from '../commons/Uris'
import { OrganizationsTable } from '../Organizations/components/OrganizationsTable'

//TODO: change value
export function Page({authenticated=true}: {authenticated?: boolean}) {

    const menuItems = [
        {
            name: "Home",
            href: makeHome()
        }
    ]

    return (
        <div>
            <BuildMenu items={menuItems} currItem={makeHome()}></BuildMenu>
            {getHome()}
        </div>
    )

    //TODO: Replace with get home
    function getHome() {

        const navigate = useNavigate()

        return( authenticated?
            <Container>
                <Fetch
                    url={`/api${makeOrganizations()}`}
                    renderBegin={() => <p>Waiting for URL...</p>}
                    renderOk={(payload) =>
                        <div>
                            <OrganizationsTable collection={payload}></OrganizationsTable>
                        </div>
                    }
                    renderLoading={() => <Loader /> }
                    renderNok={message => <ErrorNOk message={message} />}
                    renderError={error => <Error error={error} />}
                />
            </Container>
                :
            <div>
                <h1>Sign in/ Sign up</h1>
            </div>
        )
        
    }
}

