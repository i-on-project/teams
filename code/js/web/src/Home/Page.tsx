import * as React from "react";
import { Header } from 'semantic-ui-react'
import { useMenu } from "../common/components/MenuContext";
import { makeAbout, makeHome } from "../common/Uris";

export function Page() {

    const setItems = useMenu().setItems

    React.useEffect(() => {
        setItems([
            {
                name: "Home",
                href: makeHome(),
                isActive: true
            }
        ])
    }, [])

    return (
        <div>
            <Header as='h1'>Logged In</Header>
        </div>
    )
}