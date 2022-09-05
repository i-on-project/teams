import * as React from 'react'
import { useLocation, useSearchParams } from 'react-router-dom';
import { Button, Form, Grid, Header, Message, Segment, Image, Divider, Icon, Container } from 'semantic-ui-react';
import { useLoggedInState } from "../common/components/loggedStatus"
import { useMenu } from '../common/components/MenuContext';
import { makeAbout } from '../common/Uris';


declare type Problem = {
  type: string,
  title: string,
  status: number,
  detail: string
}

type MessageState = { hidden: boolean, success: boolean, error: boolean, status: number, message: string }

type MessageAction =
  | { type: 'error', status: number, message: string }
  | { type: 'info', message: string }
  | { type: 'success' }
  | { type: 'reset' }

function messageReducer(state: MessageState, action: MessageAction): MessageState {
  switch (action.type) {
    case 'success': return { hidden: false, success: true, error: false, status: 200, message: 'Successful!' }

    case 'info': return { hidden: false, success: true, error: false, status: null, message: action.message }

    case 'error': return { hidden: false, success: false, error: true, status: action.status, message: action.message }

    case 'reset': return { hidden: true, success: false, error: false, status: null, message: null }

    default: return state
  }
}


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

export function Page() {

  const setLoggedState = useLoggedInState().setLoggedState
  const [parameters, setParameters] = React.useState<RegisterParams>({})
  const setItems = useMenu().setItems
  const [loadindState, setLoading] = React.useState(false)
  const [messageState, messageDispatch] = React.useReducer(messageReducer,
    { hidden: true, success: false, error: false, status: null, message: null })
  const [searchParams, setSearchParams] = useSearchParams();

  React.useEffect(() => {
    setItems([
      {
        name: "About",
        href: makeAbout()
      }
    ])

    if (searchParams.get("toVerify"))
      messageDispatch({ type: 'info', message: 'A verification email was sent. After verifying please login. The email may be in your spam folder.' })
  }, [])

  async function fetchWithTimeout(resource: string, options?: any) {

    const timeout = 8000;

    const controller = new AbortController();
    const id = setTimeout(() => {
      console.log("Fetch timed out, aborting.")
      setLoading(false)
      return controller.abort()
    }, timeout);
    const response = await fetch(resource, {
      ...options,
      signal: controller.signal
    });
    clearTimeout(id);
    return response;
  }

  function onclickLogIn() {
    setLoading(true)
    fetchWithTimeout('https://ion-teams-service.herokuapp.com/auth/login?clientId=web',
      {
        credentials: 'include'
      }
    )
      .then(async resp => {
        if (resp.ok) return resp.json()
        throw await resp.json()
      })
      .then((jsonRedirectObj: { location: string }) => {
        window.location.href = jsonRedirectObj.location
      })
      .catch((err: Problem) => {
        setLoading(false)
        console.log(err)
        messageDispatch({ type: 'error', status: err.status, message: err.detail })
      })
  }

  function onclickSignUp() {
    setLoading(true)
    fetchWithTimeout('https://ion-teams-service.herokuapp.com/auth/register?clientId=web-register',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(parameters),
        credentials: 'include'
      }
    )
      .then(async resp => {
        if (resp.ok) return resp.json()
        throw await resp.json()
      })
      .then((jsonRedirectObj: { location: string }) => {
        window.location.href = jsonRedirectObj.location
      })
      .catch((err: Problem) => {
        setLoading(false)
        console.log(err)
        messageDispatch({ type: 'error', status: err.status, message: err.detail })
      })
  }



  //TODO: change download files
  return (
    <Grid columns={2} stackable textAlign='center'>
      <Grid.Row verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }} >
          <Segment stacked >
            <Header as='h3'>
              Download Desktop app
            </Header>
            <p>
              For Windows:
              <div />
              <Button circular icon='windows' as={'a'} href='/ion-teams-desktop-Setup.exe' download />
            </p>
            <p>
              For Mac Os:
              <div />
              <Button circular icon as={'a'} href='/ion-teams-desktop-macos-x64.zip' download>
                <Icon name='apple' /> x64
              </Button>
              <Button circular icon as={'a'} href='/ion-teams-desktop-macos-arm64.zip' download>
                <Icon name='apple' /> arm
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
          <Segment stacked disabled={loadindState} loading={loadindState}>
            <Message
              hidden={messageState.hidden}
              positive={messageState.success}
              error={messageState.error}
              header={messageState.status}
              content={messageState.message}
              onDismiss={() => { messageDispatch({ type: 'reset' }) }}
            />
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
