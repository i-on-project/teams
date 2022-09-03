import { createContext, useContext } from "react"

/**
 * Uri context is used in paging, with this context the uri used in the fetch component can be changed and a new fetch is
 * made to display a new page.
 */

const uriContextDefault = {
    uri: '',
    setUri: (state: string) => { }
}

export const UriContext = createContext(uriContextDefault)

export function useUri() {
    return useContext(UriContext)
}