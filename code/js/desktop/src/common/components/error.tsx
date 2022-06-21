import * as React from "react"
import { useNavigate } from "react-router"
import { Button, Container, Header, TextArea } from "semantic-ui-react"

export function ErrorNOk({message}: {message: Response}) {

    const navigate = useNavigate()
    
    return (
        <Container>
            <Header as='h1' textAlign="center">{message.status}</Header>
            <Header as='h3' textAlign="center">An error occured, please go back to the main page.</Header>
            <Button primary fluid onClick={() => navigate('/', {replace: false})}>Home</Button>
        </Container>
    )
}

export function Error({error}: {error: Error}) {

    const navigate = useNavigate()
    
    return (
        <Container >
            <Header as='h1' textAlign="center">{error.message}</Header>
            <Header as='h3' textAlign="center">An error occured, please go back to the main page.</Header>
            <Button primary fluid onClick={() => navigate('/', {replace: false})}>Home</Button>
        </Container>
    )
}