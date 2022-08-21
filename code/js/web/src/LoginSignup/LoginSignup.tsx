import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image, Divider, Icon, Container } from 'semantic-ui-react';
import { useLoggedInState } from "../common/components/loggedStatus"
import { useMenu } from '../common/components/MenuContext';
import { makeAbout } from '../common/Uris';


declare type AccessToken = {
  access_token: string
}

declare type UrlObj = {
  protocol: string,
  code: string,
  type: string
}

declare type RegisterParams = {
  name?: string,
  email?: string,
  number?: string,
  office?: string
}

function convertUrltoObj(url: string) {

  let split = url.split('://')
  const protocol = split[0]

  split = split[1].split('&')

  //Obtaining the code
  const code = split[0].split('=')[1]

  //Obtaining the code
  const type = split[1].split('=')[1]

  const obj: UrlObj = { protocol: protocol, code: code, type: type }

  return obj
}

export function Page() {

  const setLoggedState = useLoggedInState().setLoggedState
  const [url, setUrl] = React.useState('*No URL yet*')
  const [parameters, setParameters] = React.useState<RegisterParams>({})
  const setItems = useMenu().setItems

  React.useEffect(() => {
    setItems([
      {
        name: "About",
        href: makeAbout()
      }
    ])
  }, [])

  React.useEffect(() => {
    if (!url.includes('code=')) return

    const urlObj = convertUrltoObj(url)
    console.log(urlObj)

    switch (urlObj.type) {
      case "login":
        fetch(`http://localhost:8080/auth/access_token?code=${urlObj.code}&type=login`)
          .then(resp => resp.json())
          .then((token: AccessToken) => {
            console.log(token)
            setLoggedState({ logged: true, access_token: token })
          })

      case "register": {
        fetch(`http://localhost:8080/auth/register?clientId=web-register`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(parameters)
        })
          .then(() =>
            fetch(`http://localhost:8080/auth/access_token?code=${urlObj.code}&type=register&number=${parameters.number}`)
              .then(resp => resp.json())
              .then((token: AccessToken) => {
                console.log(token)
                setLoggedState({ logged: true, access_token: token })
              }))
      }
    }
  }, [url])

  function onclickLogIn() {
    fetch('http://localhost:8080/auth/login?clientId=web')
            .then(resp => resp.json())
            .then((token: AccessToken) => {
              console.log(token)
              setLoggedState({ logged: true, access_token: token })
            })
  }

  function onclickSignUp() {
    fetch('http://localhost:8080/auth/register?clientId=web-register')
            .then(resp => resp.json())
            .then((token: AccessToken) => {
              console.log(token)
              setLoggedState({ logged: true, access_token: token })
            })
  }

  //TODO: change download files
  return (
    <Grid columns={2} stackable textAlign='center'>
      <Grid.Row verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }} >
          <Segment stacked>
            <Header as='h3'>
              Download Desktop app
            </Header>
            <p>
              For Windows:
              <div />
              <Button circular icon='windows' as={'a'} href='/logo_blue.svg' download/>
            </p>
            <p>
              For Mac Os:
              <div /> 
              <Button circular icon as={'a'} href='/logo_blue.svg' download>
                <Icon name='apple'/> x64
              </Button>
              <Button circular icon as={'a'} href='/logo_blue.svg' download>
                <Icon name='apple'/> arm
              </Button>
            </p>
            <p>
              For Linux:
              <div />
              <Button circular icon='linux' disabled />
            </p>
          </Segment>
          <Message>
            The Desktop application is meant to be used exclusively by Teachers.
          </Message>
        </Grid.Column>

        <Divider vertical>Or</Divider>

        <Grid.Column style={{ maxWidth: 450 }}>
          <Segment stacked >
            <Header as="h3">Login via GitHub</Header>
            <Button
              circular
              color='black'
              icon='github'
              onClick={() => { onclickLogIn() }}
            />
            <Divider horizontal>Or</Divider>
            <Form>
              <Header as="h3">Signup</Header>
              <Header as="h5">Enter the following information and then sign up through github</Header>

              <Form.Input fluid required icon='user' iconPosition='left' placeholder='First and Last names' onChange={(event) => setParameters({ ...parameters, ["name"]: event.target.value })} />
              <Form.Input fluid required icon='mail' iconPosition='left' placeholder='E-mail address' onChange={(event) => setParameters({ ...parameters, ["email"]: event.target.value })} />
              <Form.Input fluid required icon='id card outline' iconPosition='left' placeholder='Institutional Number' onChange={(event) => setParameters({ ...parameters, ["number"]: event.target.value })} />

              <Button
                circular
                color='black'
                onClick={() => onclickSignUp()}
              >
                <Icon name='github' />
                Sign up
              </Button>
            </Form>
          </Segment>
          <Message>
            This web application is meant to be used exclusively by students.
          </Message>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  )
}

