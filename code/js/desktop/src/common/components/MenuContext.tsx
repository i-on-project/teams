import { createContext, useState } from "react"
import * as React from "react"
import { MenuItem } from "./Menu"

/**
 * Context used to maintain the side menu, each page defines what should be on the menu.
 */

export const MenuContext = createContext({
    items: [],
    setItems: (items: MenuItem[]) => { },

})

export function useMenu() {
    return React.useContext(MenuContext)
}

export function MenuContainer({ children }: { children: React.ReactNode }) {
    const [items, setItems] = useState([])

    return (
        <MenuContext.Provider value={{ items: items, setItems: setItems }}>
            {children}
        </MenuContext.Provider>
    )
}