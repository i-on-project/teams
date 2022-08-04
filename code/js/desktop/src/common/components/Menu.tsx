import { Button, Menu, MenuHeader } from "semantic-ui-react"
import * as React from "react"
import { useNavigate } from "react-router-dom"
import { useMenu } from "./MenuContext"
import { useLoggedInState } from "./loggedStatus"


export type MenuItem = {
    name: string,
    href: string,
    isActive?: boolean,
    hasSubItems?: boolean,
    subItems?: MenuItem[]
}


export function VerticalFixedMenu() {

    const items = useMenu().items
    const navigate = useNavigate()
    const setLoggedState = useLoggedInState().setLoggedState

    const logoStyle = {
        display: "block",
        height: "40px",
        marginLeft: "auto",
        marginRight: "auto",
        width: "50 %"
    }

    function onLogout() {
        //TODO: perform logout
        setLoggedState({logged: false, access_token: null})
        navigate('/')
    }


    function itemsBuilder(items: MenuItem[]) {

        return items.map((item: MenuItem) =>
            item.hasSubItems ?
                <React.Fragment>
                    <Menu.Item
                        key={item.name}
                        name={item.name}
                        active={item.isActive}
                        onClick={() => navigate(item.href)} />
                    <Menu.Menu key={item.name} >
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
                </React.Fragment>
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
        <Menu inverted vertical fixed="left">
            <Menu.Item key="menu_logo" onClick={() => { navigate("/") }}>
                <img src="public/logo_blue_darktheme.svg" alt='logo' style={logoStyle}></img>
            </Menu.Item>
            {
                itemsBuilder(items)
            }
            <Menu.Item>
                <Button fluid negative onClick={() => { onLogout() }}>Logout</Button>
            </Menu.Item>
        </Menu >
    )

}