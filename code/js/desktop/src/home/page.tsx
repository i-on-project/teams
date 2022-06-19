import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Container } from 'semantic-ui-react'
import { BuildMenu } from '../commons/components/Menu'
import { makeHome, makeOrganizations } from '../commons/Uris'

//TODO: change value
export function Page({authenticated=true}: {authenticated?: boolean}) {

    const menuItems = [
        {
            name: "Home",
            href: makeHome()
        }
    ]

    return (
        <div>
            <BuildMenu items={menuItems} currItem={makeHome()}></BuildMenu>
            {getHome()}
        </div>
    )

    function getHome() {

        const navigate = useNavigate()

        return( authenticated?
            <div>
                <h1>Home</h1>
                <Button onClick={()=> navigate(makeOrganizations())}>Orgs</Button>
            </div>
                :
            <div>
                <h1>Sign in/ Sign up</h1>
            </div>
        )
        
    }
}