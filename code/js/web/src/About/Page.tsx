import * as React from "react";
import { Container, Header, Segment } from 'semantic-ui-react'
import { useLoggedInState } from "../common/components/loggedStatus";
import { useMenu } from "../common/components/MenuContext";
import { makeAbout } from "../common/Uris";

export function Page() {

    const setItems = useMenu().setItems

    React.useEffect(() => {
        setItems([
            {
                name: "About",
                href: makeAbout(),
                isActive: true
            }
        ])
    }, [])

    
    return (
        <Container textAlign="center">
            <Header as='h1'>About</Header>
            <Segment vertical>
                Click the button bellow to perform a fake log in.
            </Segment>
        </Container>
    )
}