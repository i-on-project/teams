import { createContext, useState, useContext } from "react"
import * as React from "react"

/**
 * Context used to update paged when a change is made (ex. a new resource was created)
 */

export const ChangedContext = createContext({
    changed: false,
    setChanged: (state: boolean) => {}
})

export function useChangedState() {
    return useContext(ChangedContext)
}

export function ChangedContainer({children}:{children: React.ReactNode}) {
    const [state, setState] = useState(false)

    React.useEffect(() => {
        setState(false)
    }, [state])
    
    return (
        <div>
            <ChangedContext.Provider value={{changed: state, setChanged: setState}}>
               {children}
            </ChangedContext.Provider>
        </div>
    )
}