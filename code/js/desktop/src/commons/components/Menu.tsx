import { Icon, Menu } from "semantic-ui-react"
import { useState } from "react"
import * as React from "react"
import { useNavigate } from "react-router-dom"


export type MenuItem = {
    name: string,
    href: string
}

export function BuildMenu({ items }: { items: MenuItem[] }) {

    const [activeItem, setActiveItem] = useState('/')
    const navigate = useNavigate()

    function onClick(href: string) {
        setActiveItem(href)
        navigate(href)
    }

    function onLogout(event: any) {
        //TODO: perform logout
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
                    />
                )
            }
            <Menu.Menu position='right'>
            <Menu.Item
              name='logout'
              active={activeItem === 'logout'}
              color='red'
              onClick={() => onClick('/logout')}
            />
          </Menu.Menu>
        </Menu>
    )

}