import * as React from "react"
import { useParams } from "react-router-dom"
import { Container, Header, Loader, Message, Segment } from "semantic-ui-react"
import { DefaultForm } from "../common/components/DefaultForm"
import { Fetch } from "../common/components/Fetch"
import { Loading } from "../common/components/Loading"
import { useMenu } from "../common/components/MenuContext"
import { Action, Collection, Link_relation, Resource } from "../common/types/siren"
import { makeHome } from "../common/Uris"
import { TeamsTable } from "../Teams/components/TeamsTable"

export function Page() {
    
    const {code} = useParams()
    const setItems = useMenu().setItems

    React.useEffect(() => {
        setItems([
            {
                name: "Home",
                href: makeHome()
            }
        ])
    }, [])

    return (
        <Fetch
                url={`/api/invite-code/${code}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload: Resource) =>
                    <Body resource={payload}></Body>
                }
                renderLoading={() => <Loading />}
            />
    )
}

function Body({resource}: {resource: Resource}) {
    
    const createTeam_action = resource.actions.find( (action: Action) => action.name == "create-team" )
    const teams_url = resource.links.find( (link: Link_relation) => link.rel == "teams").href

    return (
        <Container>
            { createTeam_action ?
                <Segment>
                    <DefaultForm action={createTeam_action}/>
                </Segment>
                :
                <Message warning>
                    Team creation is not available.
                </Message>
            }
            { teams_url &&
                <Segment>
                    <Fetch
                        url={teams_url}
                        renderBegin={() => <p>Waiting for URL...</p>}
                        renderOk={
                            (payload: Collection) => payload.entities.length != 0 ?
                                <React.Fragment>
                                    <Header as='h2'>Enter an existing team</Header>
                                    <TeamsTable collection={payload}/>
                                </React.Fragment>
                                :
                                <Header as="h3" textAlign="center"> No teams were created.</Header>
                        }
                        renderLoading={() => <Loader />} 
                    />
                </Segment>
                }
        </Container>
    )
}
