import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image, Divider, Icon } from 'semantic-ui-react';
import { useLoggedInState } from "../common/components/loggedStatus"
import { useServiceLocation } from '../common/components/ServiceLocationContext';

/**
 * Electron IPC api deficitions for usage in this file
 */
declare const electron: {
  externalBrowserApi: {
    open: (value: string) => undefined
  },
  cookiesApi: () => Promise<[]>,
  customProtocolUrl: (callback: (_event: any, value: string) => void) => void,
}

/**
 * Types definitions used in this file
 */
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

/**
 * Function used in a message reducer to display popup information messages.
 */
function messageReducer(state: MessageState, action: MessageAction): MessageState {
  switch (action.type) {
    case 'success': return { hidden: false, success: true, error: false, status: 200, message: 'Successful!' }

    case 'error': return { hidden: false, success: false, error: true, status: action.status, message: action.message }

    case 'info': return { hidden: false, success: true, error: false, status: null, message: action.message }

    case 'reset': return { hidden: true, success: false, error: false, status: null, message: null }

    default: return state
  }
}

/**
 * Function used to convert the URL received from deep-linking in an object so that the information is more easily accessable.
 */
function convertUrltoObj(url: string) {

  let split = url.split('://')
  const protocol = split[0]

  split = split[1].split('&')

  //Obtaining the code
  const code = split[0].split('=')[1]

  //Obtaining the type
  const type = split[1].split('=')[1]

  const obj: UrlObj = { protocol: protocol, code: code, type: type }

  return obj
}

/**
 * Function represents the login/signup page, complete with all the necessary mecanisms for authenticatig and registering a user.
 */
export function LoginSignup() {

  const setLoggedState = useLoggedInState().setLoggedState
  const apiUrl = useServiceLocation().url
  const [url, setUrl] = React.useState('*No URL yet*')
  const [parameters, setParameters] = React.useState<RegisterParams>({})
  const [loadindState, setLoading] = React.useState(false)
  const [messageState, messageDispatch] = React.useReducer(messageReducer,
    { hidden: true, success: false, error: false, status: null, message: null })

  electron.customProtocolUrl((_event, value) => {
    setUrl(value)
  })

  /**
   * Attempt to auto-login, checking if there is an active access token stored as a cookie.
   */
  React.useEffect(() => {
    fetch(`${apiUrl}/auth/access_token`)
      .then(resp => {
        if (!resp.ok) {
          throw new Error("No Access Token")
        }
        return resp
      })
      .then(resp => resp.json())
      .then((token: string) => {
        setLoggedState({ logged: true, access_token: token })
      })
      .catch((err: Error) => {
        
      })
  }, [])

  /**
   * Mecasnism of login/signup after receiving the url from deep-linking
   */
  React.useEffect(() => {
    if (!url.includes('code=')) return

    const urlObj = convertUrltoObj(url)

    /**
     * Fetch implementation with a timeout of 8seconds.
     */
    async function fetchWithTimeout(resource: string, options?: any) {

      const timeout = 8000;

      const controller = new AbortController();
      const id = setTimeout(() => {
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

    /**
     * Function used to check if the fetch response received has a 200ok status code, if not an error is thrown.
     */
    async function checkRespOk(resp: Response) {

      if (!resp.ok) {
        throw await resp.json()
      }

      return resp
    }

    if (urlObj.type === "login") {
      setLoading(true)

      fetchWithTimeout(`${apiUrl}/auth/access_token?code=${urlObj.code}&type=login`)
        .then(checkRespOk)
        .then(resp => resp.json())
        .then((token: string) => {
          messageDispatch({ type: 'success' })
          setLoggedState({ logged: true, access_token: token })
          setLoading(false)
        })
        .catch((err: Problem) => {
          setLoading(false)
          messageDispatch({ type: 'error', status: err.status, message: err.detail })
        })
    } else if (urlObj.type === "register") {
      setLoading(true)

      fetchWithTimeout(`${apiUrl}/auth/register?clientId=desktop-register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(parameters)
      })
        .then(() => fetchWithTimeout(`${apiUrl}/auth/access_token?code=${urlObj.code}&type=register&number=${parameters.number}`))
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

  /**
   * OnClick function of the login button
   */
  function loginButtonOnClick() {
    electron.externalBrowserApi.open(`${apiUrl}/auth/login?clientId=desktop`)
  }

  /**
   * OnClick function of the signup button
   */
  function signupButtonOnClick() {
    electron.externalBrowserApi.open(`${apiUrl}/auth/register?clientId=desktop-register`)
  }

  return (
    <div>
      <div>
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