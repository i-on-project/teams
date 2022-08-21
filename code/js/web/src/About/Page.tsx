import * as React from "react";
import { Container, Header, List, Image, Card } from 'semantic-ui-react'
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
        <Container>
            <Header as='h1' textAlign="center">About</Header>
            <p>
                The project is inserted on the i-on initiative, that consists on the development of systems and applications
                to support academic activities in ISEL, by students. One of main goals of this initiative is the development
                and availability of code as open-source.
            </p>
            <p>
                The i-on Teams project aims to facilitate da managing of the teacher curricular units, through the unification of the
                groups and practical assignments of every class.
            </p>


            <List celled>
                <List.Item  >
                    <Image size='mini' src='https://isel.pt/sites/default/files/NoPath%20-%20Copy%402x_0.png' />
                    <List.Content>
                        <List.Header as='a' href={'https://isel.pt/'}>ISEL</List.Header>
                        <List.Description as='a'>Our College.</List.Description>
                    </List.Content>
                </List.Item>
                <List.Item  >
                    <List.Icon name='github' size='large' verticalAlign='middle' />
                    <List.Content>
                        <List.Header as='a' href={'https://github.com/i-on-project/teams'}>i-on Teams</List.Header>
                        <List.Description as='a'>Repository with the source code.</List.Description>
                    </List.Content>
                </List.Item>
            </List>
            <Card.Group itemsPerRow={3}>
                <Card fluid
                    header='Afonso Machado'
                    meta='Author'
                    description='Afonso is a 3rd year student, of a Degree on Computer Engineering in ISEL.'
                    color="blue"
                />
                <Card fluid
                    header='Martim Francisco'
                    meta='Author'
                    description='Martim is a 3rd year student, of a Degree on Computer Engineering in ISEL.'
                    color="blue"
                />
                <Card fluid
                    header='Pedro Felix'
                    meta='Teacher'
                    description='Teacher that guided the project.'
                    color="blue"
                />
            </Card.Group>
        </Container>
    )
}