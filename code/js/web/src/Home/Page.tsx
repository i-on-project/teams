import * as React from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Button, Container, Divider, Header, Icon, Input, Loader } from 'semantic-ui-react'
import { Fetch } from "../common/components/Fetch";
import { useLoggedInState } from "../common/components/loggedStatus";
import { useMenu } from "../common/components/MenuContext";
import { makeAbout, makeHome, makeInviteCode } from "../common/Uris";

export function Page() {

    const setItems = useMenu().setItems
    const navigate = useNavigate()
    const [code,setCode] = React.useState("")

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
            <Input placeholder='Insert invite code where.' onChange={(event)=> setCode(event.target.value)}></Input>
            <Button color="black" icon='arrow right' size="small" onClick={() => navigate(makeInviteCode(code))}/>
        </Container>
    )
}