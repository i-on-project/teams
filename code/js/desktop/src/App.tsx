import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image } from 'semantic-ui-react';
import { LoggedInContext } from './common/components/loggedStatus';
import { ServiceLocationContainer } from './common/components/ServiceLocationContext';
import { LoginSignup } from './LoginSignup/LoginSignup';
import { Router } from './Router/Router';

declare type AccessToken = {
  access_token: string,
  scope: string,
  token_type: string
}
export default function App() {

  const [logged, setLogged] = React.useState({ logged: false, access_token: null })

  return (
    <ServiceLocationContainer>
      <LoggedInContext.Provider value={{ loggedInState: logged, setLoggedState: setLogged }}>
        {
          logged.logged ?
            <Router />
            :
            <LoginSignup />
        }
      </LoggedInContext.Provider>
    </ServiceLocationContainer>
  )
}