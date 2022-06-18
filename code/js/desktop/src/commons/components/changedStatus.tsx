import { createContext, useState } from "react"
import * as React from "react"

export const ChangedContext = createContext({
    changed: true,
    setChanged: (state: boolean) => {}
})

export function ChangedContainer({children}:{children: React.ReactNode}) {
    const [state, setState] = useState(true)

    React.useEffect(() => {
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