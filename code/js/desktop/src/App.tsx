import * as React from 'react'
import { Button, Form, Grid, Header, Message, Segment, Image } from 'semantic-ui-react';
import { LoggedInContext } from './common/components/loggedStatus';
import { LoginSignup } from './LoginSignup/LoginSignup';
import { Router } from './Router/Router';

export default function App() {

  const [logged, setLogged] = React.useState(true)

  return (
    <LoggedInContext.Provider value={{ loggedInState: logged, setLoggedState: setLogged }}>
      {
        logged ?
          <Router />
          :
          <LoginSignup />
      }
    </LoggedInContext.Provider>
  )
}