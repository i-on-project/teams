import { createContext, useContext, useState } from "react"
import * as React from "react"

export const LoggedInContext = createContext({
    loggedInState: true,
    setLoggedState: (state: boolean) => { },
})

export function useLoggedInState() {
    return useContext(LoggedInContext)
}