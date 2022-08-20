import * as React from "react";
import { Button, Container, Divider, Header } from 'semantic-ui-react'
import { useLoggedInState } from "../common/components/loggedStatus";
import { useMenu } from "../common/components/MenuContext";
import { makeAbout, makeHome } from "../common/Uris";

export function Page() {

    const setItems = useMenu().setItems

    React.useEffect(() => {
        setItems([
            {
                name: "About",
                href: makeHome(),
                isActive: true
            }
        ])
    }, [])

    const setLoggedState = useLoggedInState().setLoggedState //FOR TEST ONLY. Not supose to perform a fake log in
    return (
        <Container textAlign="center">
            <Header as='h1'>About</Header>
            Click the button bellow to perform a fake log in.
            <Divider hidden/>
            <Button circular secondary onClick={() => {setLoggedState({logged: true, access_token: null})}}>
                 Start Right Now 
            </Button>
        </Container>
    )
}