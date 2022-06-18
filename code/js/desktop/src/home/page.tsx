import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import * as Organizations from '../Organizations/page'

export function Page({authenticated=true}: {authenticated?: boolean}) {

    const navigate = useNavigate()

    return (
        <Container>
            {getHome()}
        </Container>
    )

    function getHome() {
        return( authenticated?
                <Organizations.Page/>
                :
                <div>
                    <h1>Sign in/ Sign up</h1>
                </div>
        )
        
    }
}