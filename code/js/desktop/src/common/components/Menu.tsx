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

    function onClick(href: string) {
        navigate(href)
    }

    function onLogout() {
        //TODO: perform logout
        navigate('/')
    }

    function itemsBuilder(items: MenuItem[]) {
        return items.map((item: MenuItem) =>
            item.isDropDown ?
                <Dropdown key={item.name} text={item.name} simple item >
                    <Dropdown.Menu>
                        {
                            item.dropDownOptions.map((option: MenuItem) => {
                                return (
                                    <Dropdown.Item key={option.name} onClick={() => onClick(item.href)}>{option.name}</Dropdown.Item>
                                )
                            })
                        }
                    </Dropdown.Menu>
                </Dropdown>
                :
                <Menu.Item
                    name={item.name}
                    active={item.isActive}
                    onClick={() => onClick(item.href)}
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

