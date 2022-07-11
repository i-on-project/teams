import { Button, Menu, MenuHeader } from "semantic-ui-react"
import * as React from "react"
import { useNavigate } from "react-router-dom"
import { MenuContext } from "./MenuStatus"
import { useState } from "react"


export type MenuItem = {
    name: string,
    href: string,
    isActive?: boolean,
    hasSubItems?: boolean,
    subItems?: MenuItem[]
}


export function VerticalFixedMenu() {

    const { items } = React.useContext(MenuContext)
    const navigate = useNavigate()

    function onLogout() {
        //TODO: perform logout
        navigate('/')
    }


    function itemsBuilder(items: MenuItem[]) {

        return items.map((item: MenuItem) =>
            item.hasSubItems ?
                <Menu.Item>
                    <MenuHeader>{item.name}</MenuHeader>
                    <Menu.Menu key={item.name} active={item.isActive}>
                        <Menu.Item //HARDCODED??
                            key={item.name}
                            name={'Description'}
                            active={item.isActive}
                            onClick={() => navigate(item.href)}
                        />
                        {
                            item.subItems.map((subItem: MenuItem) => {
                                return (
                                    <Menu.Item
                                        key={subItem.name}
                                        name={subItem.name}
                                        active={subItem.isActive}
                                        onClick={() => navigate(subItem.href)}
                                    />
                                )
                            })

                        }
                    </Menu.Menu>
                </Menu.Item>
                :
                <Menu.Item
                    key={item.name}
                    name={item.name}
                    active={item.isActive}
                    onClick={() => navigate(item.href)}
                />
        )
    }

    return (
        <Menu vertical fixed="left">
            <Menu.Item key="menu_logo" onClick={() => { navigate("/") }}>
                <img src="public/logo_blue.svg" alt='logo' style={{ height: "40px" }}></img>
            </Menu.Item>
            {
                itemsBuilder(items)
            }
            <Menu.Item
                name='logout'
                color="red"
                onClick={() => { onLogout() }}
            >
            </Menu.Item>
        </Menu>
    )

}

/*
 
export function VerticalMenu({ subItems, children }: { subItems: MenuItem[], children: React.ReactNode }) {

    const navigate = useNavigate()

    return (
        <Grid columns={2}>
            <Grid.Column width={3}>
                <Menu secondary pointing vertical>
                    {
                        subItems.map((item: MenuItem) =>
                            <Menu.Item
                                name={item.name}
                                active={item.isActive}
                                onClick={() => navigate(item.href)}
                                key={item.name}
                            />
                        )
                    }
                </Menu>
            </Grid.Column>
            <Grid.Column width={12}>
                {children}
            </Grid.Column>
        </Grid>


    )
}
*/