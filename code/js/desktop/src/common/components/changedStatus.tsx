import { createContext, useState, useContext } from "react"
import * as React from "react"

export const ChangedContext = createContext({
    changed: true,
    setChanged: (state: boolean) => {}
})

export function useChangedState() {
    return useContext(ChangedContext)
}

export function ChangedContainer({children}:{children: React.ReactNode}) {
    const [state, setState] = useState(true)

    React.useEffect(() => {
        console.log("CHANGED IS NOW GOING TO FALSE")
        return () => {setState(false)}
    }, [state])
    
    return (
        <div>
            <ChangedContext.Provider value={{changed: state, setChanged: setState}}>
               {children}
            </ChangedContext.Provider>
        </div>
    )
}