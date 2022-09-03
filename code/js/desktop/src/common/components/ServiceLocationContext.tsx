import { createContext, useState, useContext } from "react"
import * as React from "react"

/**
 * Context used to remove hardcoded uri's in the fetch functions.
 */

export const ServiceLocationContext = createContext(
    { url: 'https://ion-teams-service.herokuapp.com'}
)

export function useServiceLocation() {
    return useContext(ServiceLocationContext)
}

export function ServiceLocationContainer({children}:{children: React.ReactNode}) {
    
    return (
        <div>
            <ServiceLocationContext.Provider value={ {url: 'https://ion-teams-service.herokuapp.com'} }>
               {children}
            </ServiceLocationContext.Provider>
        </div>
    )
}