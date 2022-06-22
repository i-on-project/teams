import { Dropdown, Menu } from "semantic-ui-react"
import { useState } from "react"
import * as React from "react"
import { NavigateFunction, useNavigate } from "react-router-dom"
import { MenuContext } from "./MenuStatus"


export type MenuItem = {
    name: string,
    href: string,
    isActive?: boolean,
    isDropDown?: boolean,
    dropDownOptions?: MenuItem[]
}

export function BuildMenu() {

    const { items } = React.useContext(MenuContext)
    const navigate = useNavigate()

    function onLogout() {
        //TODO: perform logout
        navigate('/')
    }

    function itemsBuilder(items: MenuItem[]) {
        return items.map((item: MenuItem) =>
            item.isDropDown ?
                <Menu.Item key={item.name} active={item.isActive}>
                    <Dropdown text={item.name} >
                        <Dropdown.Menu>
                        <Dropdown.Item key={'This'} action={navigate(item.href)} text={'This'}/>
                            {
                                item.dropDownOptions.map((option: MenuItem) => {
                                    return (
                                        <Dropdown.Item key={option.name} onClick={() => navigate(option.href)} text={option.name}/>
                                    )
                                })
                            }
                        </Dropdown.Menu>
                    </Dropdown>
                </Menu.Item>
                :
                <Menu.Item
                    name={item.name}
                    active={item.isActive}
                    onClick={() => navigate(item.href)}
                    key={item.name}
                />
        )
    }

    return (
        <Menu secondary pointing>
            <Menu.Item key="menu_logo">
                <img src="public/logo_blue.svg" alt='logo'></img>

            </Menu.Item>
            {
                itemsBuilder(items)
            }
            <Menu.Menu position='right' key="logout_button">
                <Menu.Item
                    name='logout'

                    onClick={() => onLogout()}
                />
            </Menu.Menu>
        </Menu>
    )

}

