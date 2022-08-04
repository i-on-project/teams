import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image, Divider } from 'semantic-ui-react';
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
  code: string
}

function convertUrltoObj(url: string) {

  let split = url.split('://')
  const protocol = split[0]

  split = split[1].split('=')
  const code = split[1]

  const obj: UrlObj = { protocol: protocol, code: code }

  return obj
}

export function LoginSignup() {

  const setLoggedState = useLoggedInState().setLoggedState
  const [url, setUrl] = React.useState('*No URL yet*')

  electron.customProtocolUrl((_event, value) => {
    setUrl(value)
  })

  React.useEffect(() => {
    if (!url.includes('code=')) return

    const urlObj = convertUrltoObj(url)

    fetch(`http://localhost:8080/auth/access_token?code=${urlObj.code}`)
      .then(resp => resp.json())
      .then((token: AccessToken) => {
        console.log(token)
        setLoggedState({logged: true, access_token: token})
      })

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

              <Form.Input fluid required icon='user' iconPosition='left' placeholder='First and Last names' />
              <Form.Input fluid required icon='mail' iconPosition='left' placeholder='E-mail address' />
              <Form.Input fluid required icon='id card outline' iconPosition='left' placeholder='Institutional Number' />
              <Form.Input fluid required icon='point' iconPosition='left' placeholder='Office' />

              <Button circular color='black' icon='github' />
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