import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image, Divider, Icon } from 'semantic-ui-react';
import { useLoggedInState } from "../common/components/loggedStatus"


declare const electron: {
  externalBrowserApi: {
    open: (value: string) => undefined
  },
  customProtocolUrl: (callback: (_event: any, value: string) => void) => void,
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

  electron.customProtocolUrl((_event, value) => {
    setUrl(value)
  })

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
        fetch(`http://localhost:8080/auth/register?clientId=desktop-register`, {
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

  return (
    <div>
      THIS IS A TEST PAGE, TO SEE THE REAL PAGES UNCOMMENT THE ROUTER!
      <div>
        The url is: {url}
      </div>
      <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
        <Grid.Column style={{ maxWidth: 450 }}>
          <Image src='./public/logo_blue.svg' size="medium" centered />
          <Segment stacked style={{ marginTop: '16px' }}>
            <Header as="h3">Login via GitHub</Header>
            <Button
              circular
              color='black'
              icon='github'
              onClick={() => { electron.externalBrowserApi.open('http://localhost:8080/auth/login?clientId=desktop') }}
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
                onClick={() => { electron.externalBrowserApi.open('http://localhost:8080/auth/register?clientId=desktop-register') }}
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