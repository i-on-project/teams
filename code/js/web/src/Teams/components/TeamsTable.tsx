import * as React from "react"
import { useState } from 'react'
import { Button, Message, Table } from 'semantic-ui-react';
import { DefaultTable } from '../../common/components/Table'
import { Action, Entity, Resource } from '../../common/types/siren';

export function TeamsTable({ entities }: { entities: Entity[], }) {

    const [successMsg, setSuccessMsg] = useState(false)
    const [errorMsg, setErrorMsg] = useState(false)

    //Assign Student to a team
    function onClick(href: string) {
        //TODO: TEST
        fetch(`http://localhost:8080${href}`, { credentials: 'include' })
            .then((response: Response) =>
                response.json()
            )
            .then((resource: Resource) => {
                const action = resource.actions.find((action: Action) => action.name == 'add-student')
                if (action) {
                    fetch(`http://localhost:8080${action.href}`, {
                        method: action.method,
                        headers: {
                            'Accept': action.type,
                            'Content-Type': action.type
                        },
                        credentials: 'include'
                    })
                        .then((response: Response) => {
                            if (response.status == 201) setSuccessMsg(true)
                            console.log(response.type)
                            if (!response.ok) setErrorMsg(true)
                        })
                } else setErrorMsg(true)
            })


    }

    function rowSpan() {
        const teams = entities.map((entity: Entity) => {
            return {
                id: entity.properties.id,
                name: entity.properties.name,
                state: entity.properties.state,
                link: entity.links[0].href
            }
        })

        return teams.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell >
                    {item.name}
                </Table.Cell>
                <Table.Cell textAlign="center">
                    <Button circular icon="signup" onClick={() => { onClick(item.link) }}></Button>
                </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <React.Fragment>
            <DefaultTable propNames={["Name", ""]}>{rowSpan()}</DefaultTable>
            {successMsg && <Message success>Success!</Message>}
            {errorMsg && <Message negative>Something Went wrong.</Message>}
        </React.Fragment>
    )
}