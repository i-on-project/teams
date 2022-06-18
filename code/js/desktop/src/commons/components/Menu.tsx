import { Menu } from "semantic-ui-react"
import { useState } from "react"
import * as React from "react"
import { useNavigate } from "react-router-dom"

export type MenuItem = {
    name: string,
    href: string
}

export function MenuVertical({ items }: { items: MenuItem[] }) {

    const [activeItem, setActiveItem] = useState('home')
    const navigate = useNavigate()

    function onClick(event: any, name: string) {
        setActiveItem(name)
        navigate(`/${name}`)
    }

    function onLogout(event: any) {
        //TODO: perform logout
    }

    return (
        <Menu vertical>
            {
                items.map((item: MenuItem) =>
                    <Menu.Item
                        name={item.name}
                        active={(activeItem === item.name)}
                        onClick={(event) => onClick(event, item.name)}
                    />
                )
            }
            <Menu.Item
                name={'log-out'}
                color='red'
                onClick={(event) => onLogout(event)}
            />
        </Menu>
    )

}