import * as React from "react"
import { Button, Icon, Image, Menu, Segment } from "semantic-ui-react"
import { useNavigate } from "react-router-dom"
import { makeHome } from "../Uris"
import { useMenu } from "./MenuContext"
import { useLoggedInState } from "./loggedStatus"


export type MenuItem = {
    name: string,
    href: string,
    isActive?: boolean,
    hasSubItems?: boolean,
    subItems?: MenuItem[]
}


export function HorizontalMenu() {

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
        fetch('http://localhost:8080/auth/logout', 
            {
                credentials: 'include'
            }
        )
            .then(() => {
                setLoggedState(false)
                navigate('/')
            })
    }

    function onLogin() {
        navigate('/login')
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
        <Menu secondary>
            <Menu.Item key="menu_logo" onClick={() => { navigate(makeHome()) }}>
                <Image src="/logo_blue.svg" alt='logo' style={logoStyle} />
            </Menu.Item>
            {
                itemsBuilder(items)
            }
            { useLoggedInState().loggedInState &&
                <Menu.Menu position="right">
                    <Menu.Item key={'logout-button'}>
                        <Button fluid circular icon negative onClick={() => { onLogout() }}>
                            Logout <Icon name='sign-out'/>
                        </Button>
                    </Menu.Item>
                </Menu.Menu>
            }
            {(!useLoggedInState().loggedInState && !window.location.href.endsWith('/start')) &&
                <Menu.Menu position="right">
                    <Menu.Item key={'loginSignup-button'}>
                        <Button fluid circular secondary onClick={() => { onLogin() }}>Log in or Sign up</Button>
                    </Menu.Item>
                </Menu.Menu>
            }
        </Menu>
    )

}