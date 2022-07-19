import { createContext, useContext } from "react"

const uriContextDefault = {
    uri: '',
    setUri: (state: string) => { }
}

export const UriContext = createContext(uriContextDefault)

export function useUri() {
    return useContext(UriContext)
}