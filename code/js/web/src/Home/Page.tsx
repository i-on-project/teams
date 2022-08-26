import * as React from "react";
import { useNavigate } from "react-router-dom";
import { Button, Container, Divider, Header, Input, Loader } from 'semantic-ui-react'
import { Fetch } from "../common/components/Fetch";
import { useMenu } from "../common/components/MenuContext";
import { UriContext } from "../common/components/UriContext";
import { makeHome, makeInviteCode, makeTeams } from "../common/Uris";
import { TeamsTable } from "../Teams/components/TeamsTable";
import { TeamsTablePlus } from "../Teams/components/TeamsTablePlus";

export function Page() {

    const setItems = useMenu().setItems
    const navigate = useNavigate()
    const [code, setCode] = React.useState("")

    React.useEffect(() => {
        setItems([
            {
                name: "Home",
                href: makeHome(),
                isActive: true
            }
        ])
    }, [])

    return (
        <Container>
            <Header as='h3'>Your Teams</Header>
            <Input placeholder='Insert invite code where.' onChange={(event) => setCode(event.target.value)}></Input>
            <Button color="black" icon='arrow right' size="small" onClick={() => { if (code.length != 0) navigate(makeInviteCode(code)) }} />
            <Divider hidden/>
            {getTeamsAndInfo()}
        </Container>
    )
}

function getTeamsAndInfo() {


    return (
        <Fetch
            url={"/api/student/teams"}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                    <TeamsTablePlus collection={payload}/>                
            }
            renderLoading={() => <Loader />}
        />
    )
}