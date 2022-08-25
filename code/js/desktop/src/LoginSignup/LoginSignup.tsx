import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image, Divider, Icon, StrictConfirmProps, StrictGridColumnProps } from 'semantic-ui-react';
import { useLoggedInState } from "../common/components/loggedStatus"


declare const electron: {
  externalBrowserApi: {
    open: (value: string) => undefined
  },
  cookiesApi: () => Promise<[]>,
  customProtocolUrl: (callback: (_event: any, value: string) => void) => void,
}

declare type AccessToken = {
  access_token: string,
  scope: string,
  token_type: string
}

declare type Problem = {
  type: string,
  title: string,
  status: number,
  detail: string
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

type MessageState = { hidden: boolean, success: boolean, error: boolean, status: number, message: string }

type MessageAction =
  | { type: 'error', status: number, message: string }
  | { type: 'info', message: string }
  | { type: 'success' }
  | { type: 'reset' }

function messageReducer(state: MessageState, action: MessageAction): MessageState {
  switch (action.type) {
    case 'success': return { hidden: false, success: true, error: false, status: 200, message: 'Successful!' }

    case 'error': return { hidden: false, success: false, error: true, status: action.status, message: action.message }

    case 'info': return { hidden: false, success: true, error: false, status: null, message: action.message }

    case 'reset': return { hidden: true, success: false, error: false, status: null, message: null }

    default: return state
  }
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

export function LoginSignup() {

  const setLoggedState = useLoggedInState().setLoggedState
  const [url, setUrl] = React.useState('*No URL yet*')
  const [parameters, setParameters] = React.useState<RegisterParams>({})
  const [loadindState, setLoading] = React.useState(false)
  const [messageState, messageDispatch] = React.useReducer(messageReducer,
     { hidden: true, success: false, error: false, status: null, message: null })

  electron.customProtocolUrl((_event, value) => {
    console.log(value)
    setUrl(value)
  })

  React.useEffect(() => {
    fetch(`http://localhost:8080/auth/access_token`)
        .then(resp => {
          if (!resp.ok) {
            throw new Error("No Access Token")
          }
          return resp
        })
        .then(resp => resp.json())
        .then((token: string) => {
          console.log(token)
          setLoggedState({ logged: true, access_token: token })
        })
        .catch((err: Error) => {
          console.log(err)
        })
  }, [])

  React.useEffect(() => {
    if (!url.includes('code=')) return

    const urlObj = convertUrltoObj(url)
    console.log(urlObj)

    async function checkRespOk(resp: Response) {
      console.log('In verification')

      if (!resp.ok) {
        throw await resp.json()
      }

      return resp
    }

    if (urlObj.type === "login") {
      fetch(`http://localhost:8080/auth/access_token?code=${urlObj.code}&type=login`)
        .then(checkRespOk)
        .then(resp => resp.json())
        .then((token: string) => {
          messageDispatch({ type: 'success' })
          console.log(token)
          setLoggedState({ logged: true, access_token: token })
          setLoading(false)
        })
        .catch((err: Problem) => {
          setLoading(false)
          messageDispatch({ type: 'error', status: err.status, message: err.detail })
        })
    } else if (urlObj.type === "register") {
      fetch(`http://localhost:8080/auth/register?clientId=desktop-register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(parameters)
      })
        .then(() => fetch(`http://localhost:8080/auth/access_token?code=${urlObj.code}&type=register&number=${parameters.number}`))
        .then(checkRespOk)
        .then(() => {
          messageDispatch({ type: 'info', message: 'A verification email was sent. After verifying please login.' })
          setLoading(false)
        })
        .catch((err: Problem) => {
          setLoading(false)
          messageDispatch({ type: 'error', status: err.status, message: err.detail })
        })
    } else {
      setLoading(false)
    }

  }, [url])

  function loginButtonOnClick() {
    setLoading(true)
    electron.externalBrowserApi.open('http://localhost:8080/auth/login?clientId=desktop')
  }

  function signupButtonOnClick() {
    setLoading(true)
    electron.externalBrowserApi.open('http://localhost:8080/auth/register?clientId=desktop-register')
  }

  return (
    <div>
      <div>
        <Button onClick={() => { setLoggedState({ logged: true, access_token: 'belele' }) }}> Fake Login </Button>
        The url is: {url}
      </div>
      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Image src='./public/logo_blue.svg' size="medium" centered />
          <Segment stacked style={{ marginTop: '16px' }} disabled={loadindState} loading={loadindState}>
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
              onClick={loginButtonOnClick}
            />

            <Divider horizontal>Or</Divider>

            <Form>
              <Header as="h3">Signup</Header>
              <Header as="h5">Enter the following information and then sign up through github</Header>

              <Form.Input fluid required icon='user' iconPosition='left' placeholder='First and Last names' onChange={(event) => setParameters({ ...parameters, ["name"]: event.target.value })} />
              <Form.Input fluid required icon='mail' iconPosition='left' placeholder='E-mail address' onChange={(event) => setParameters({ ...parameters, ["email"]: event.target.value })} />
              <Form.Input fluid required icon='id card outline' iconPosition='left' placeholder='Institutional Number' onChange={(event) => setParameters({ ...parameters, ["number"]: event.target.value })} />
              <Form.Input fluid required icon='point' iconPosition='left' placeholder='Office' onChange={(event) => setParameters({ ...parameters, ["office"]: event.target.value })} />

              <Button
                circular
                color='black'
                onClick={signupButtonOnClick}
              >
                <Icon name='github' />
                Sign up
              </Button>
            </Form>
          </Segment>
          <Message>
            This desktop application is meant to be used exclusively by teachers.
          </Message>
        </Grid.Column>
      </Grid>
    </div>
  )
}