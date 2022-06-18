import { createContext } from "react"

const pagingContextDefaulValue = {
    paging: '',
    setPaging: (state: string) => { }
}

export const PagingContext = createContext(pagingContextDefaulValue)
