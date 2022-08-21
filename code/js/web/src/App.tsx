import * as React from 'react'
import { LoggedInContext } from './common/components/loggedStatus'
import { HorizontalMenu } from './common/components/Menu'
import { MenuContainer } from './common/components/MenuContext'
import { Page } from './LoginSignup/LoginSignup'
import { Router } from './Router'

export function App() {
    
    const [logged, setLogged] = React.useState({logged: false, access_token: null})

  return (
    <LoggedInContext.Provider value={{ loggedInState: logged, setLoggedState: setLogged }}>
        {
            <Router logged={logged.logged}/>
        } 
    </LoggedInContext.Provider>
    )
}