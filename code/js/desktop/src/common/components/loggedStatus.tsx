import { createContext, useContext } from "react"

/**
 * Context used to know if the app is in a logged in state or not
 */

export const LoggedInContext = createContext({
    loggedInState: {logged: true, access_token: null},
    setLoggedState: (state: object) => { },
})

export function useLoggedInState() {
    return useContext(LoggedInContext)
}