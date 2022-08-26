import * as React from 'react'
import { LoggedInContext } from './common/components/loggedStatus'
import { Router } from './Router'

export function App() {
    
    const [logged, setLogged] = React.useState(false)

  return (
    <LoggedInContext.Provider value={{ loggedInState: logged, setLoggedState: setLogged }}>
        <Router/>
    </LoggedInContext.Provider>
    )
}
