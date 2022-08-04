import { createContext, useContext, useState } from "react"
import * as React from "react"

export const LoggedInContext = createContext({
    loggedInState: {logged: true, access_token: null},
    setLoggedState: (state: object) => { },
})

export function useLoggedInState() {
    return useContext(LoggedInContext)
}