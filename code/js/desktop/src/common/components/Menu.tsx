import { Dropdown, Grid, Menu } from "semantic-ui-react"
import * as React from "react"
import { useNavigate } from "react-router-dom"
import { MenuContext } from "./MenuStatus"


export type MenuItem = {
    name: string,
    href: string,
    isActive?: boolean
}


export function HorizontalMenu() {

    const { items } = React.useContext(MenuContext)
    const navigate = useNavigate()

    function onLogout() {
        //TODO: perform logout
        navigate('/')
    }

    function itemsBuilder(items: MenuItem[]) {
        return items.map((item: MenuItem) =>
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
            <Menu.Item key="menu_logo" onClick={() => { navigate("/") }}>
                <img src="public/logo_blue_simple.svg" alt='logo' style={{ height: "20px" }}></img>
            </Menu.Item>
            {
                itemsBuilder(items)
            }
            <Menu.Item
                name='logout'
                active
                color="red"
                position="right"
                onClick={() => { onLogout() }}
            />
        </Menu>
    )

}

/**
 * Builds a vertical Menu.
 * @param subItems - MenuItems of the menu
 * @param children - Elements that will be alongside with the vertical menu 
 * @returns 
 */
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