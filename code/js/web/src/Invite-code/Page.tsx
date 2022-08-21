import * as React from "react"
import { useParams } from "react-router-dom"
import { Header, Loader } from "semantic-ui-react"
import { Fetch } from "../common/components/Fetch"
import { Loading } from "../common/components/Loading"
import { useMenu } from "../common/components/MenuContext"
import { Resource } from "../common/types/siren"
import { makeHome } from "../common/Uris"

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
    return (
        <div>
            <Header as='h2'>Create your own team</Header>
            ...
            <Header as='h2'>Enter an existing team</Header>
            ...
        </div>
    )
}
