import { Icon, Menu } from "semantic-ui-react"
import { useState } from "react"
import * as React from "react"
import { useNavigate } from "react-router-dom"


export type MenuItem = {
    name: string,
    href: string
}

export function BuildMenu({ items, currItem }: { items: MenuItem[], currItem: string }) {

    const [activeItem, setActiveItem] = useState(currItem)
    const navigate = useNavigate()

    function onClick(href: string) {
        navigate(href)
        setActiveItem(href)
    }

    function onLogout() {
        //TODO: perform logout
        navigate('/')
    }

    return (
        <Menu secondary pointing>
            <Menu.Item>
                <Icon>
                    <img src="logotipo_blue.png"></img>
                </Icon>
            
                
            </Menu.Item>
            {
                items.map((item: MenuItem) =>
                    <Menu.Item
                        name={item.name}
                        active={(activeItem === item.href)}
                        onClick={() => onClick(item.href)}
                        key={item.name}
                    />
                )
            }
            <Menu.Menu position='right'>
            <Menu.Item
              name='logout'
              active={activeItem === 'logout'}
              
              onClick={() => onLogout()}
            />
          </Menu.Menu>
        </Menu>
    )

}