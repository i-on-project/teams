import { createContext, useState } from "react"
import * as React from "react"
import { MenuItem } from "./Menu"

export const MenuContext = createContext({
    items: [],
    setItems: (items: MenuItem[]) => {}
})

export function MenuContainer({children}:{children: React.ReactNode}) {
    const [items, setItems] = useState([])
    
    return (
        <div>
            <MenuContext.Provider value={{items: items, setItems: setItems}}>
               {children}
            </MenuContext.Provider>
        </div>
    )
}