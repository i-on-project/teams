import * as React from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { Button, Container, Divider, Header, Segment } from 'semantic-ui-react'
import { useLoggedInState } from "../common/components/loggedStatus";
import { useMenu } from "../common/components/MenuContext";
import { makeAbout, makeLoginSignup } from "../common/Uris";

export function Page() {

    const setItems = useMenu().setItems
    const navigate = useNavigate()

    React.useEffect(() => {
        setItems([
            {
                name: "About",
                href: makeAbout()
            }
        ])
    }, [])

    return (
        <Container>

            <Segment vertical>
                <Header as='h2' textAlign="center">
                    What's all about?
                </Header>
                <p>
                    I-on Teams is a system with objective of integrating <a href="https://github.com">GitHub</a> functionalities into a classroom environment.
                    It's only useful if the course has group assignments that require code development.
                </p>
                <p>
                    All users are authenticated via GitHub so that we associate names and school numbers to a GitHub username,
                    in a way that teachers can recognize the student behind a username.
                </p>
                <p>
                    Being that the case, a teacher can use our Desktop App to manage all the teams in which class,
                    where he can establish the number of teams (or groups) that can be created as well as the number of members per team.
                    For each assignment created by the teacher a GitHub repository is associated with it.
                    The delivery process is made via GitHub tags as in a specific tag,
                    established by the teacher, needs to be made by the student for our system to acknowledge that the delivery has been made.
                </p>
                <p>
                    The student also benefit from our system using this website to enter a team,
                    via a shared link, and to consult the assignment, delivery date and teacher information.
                </p>
            </Segment>
            <Segment vertical textAlign="center">
                <Header as='h2' >
                    What do you say?
                </Header>
                <Button circular color="blue" onClick={() => { navigate(makeLoginSignup()) }}>
                    Start Right Now
                </Button>
            </Segment>
        </Container>
    )
}
