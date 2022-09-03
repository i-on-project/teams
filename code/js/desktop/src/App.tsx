import * as React from 'react'
import { LoggedInContext } from './common/components/loggedStatus';
import { ServiceLocationContainer } from './common/components/ServiceLocationContext';
import { LoginSignup } from './LoginSignup/LoginSignup';
import { Router } from './Router/Router';

/**
 * Entry point for the react application. Verifies if the user is logged, if true the router is called, if false the login page is shown
 */
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