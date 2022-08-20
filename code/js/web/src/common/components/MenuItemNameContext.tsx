import { createContext, useState } from "react"
import * as React from "react"


export const MenuItemNameContext = createContext({
    orgName: '',
    setOrgName: (name: string) => { },
    className: '',
    setClassName: (name: string) => { },
    teamName: '',
    setTeamName: (name: string) => { },
    assignmentName: '',
    setAssignmentName: (name: string) => { },
    deliveryName: '',
    setDeliveryName: (name: string) => { }
})

export function useMenuItemNameContext() {
    return React.useContext(MenuItemNameContext)
}

export function MenuItemNameContainer({ children }: { children: React.ReactNode }) {
    const [orgName, setOrgName] = useState('')
    const [className, setClassName] = useState('')
    const [teamName, setTeamName] = useState('')
    const [assignmentName, setAssignmentName] = useState('')
    const [deliveryName, setDeliveryName] = useState('')

    return (
        <MenuItemNameContext.Provider value={{
            orgName: orgName, setOrgName: setOrgName,
            className: className, setClassName: setClassName,
            teamName: teamName, setTeamName: setTeamName,
            assignmentName: assignmentName, setAssignmentName: setAssignmentName,
            deliveryName: deliveryName, setDeliveryName: setDeliveryName
        }}>
            {children}
        </MenuItemNameContext.Provider>
    )
}