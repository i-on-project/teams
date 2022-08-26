import * as React from "react"
import { useState } from 'react'
import { Button, Header, Message, Table } from 'semantic-ui-react';
import { DefaultTable, Paging } from '../../common/components/Table'
import { Action, Collection, Entity, Resource } from '../../common/types/siren';

export function TeamsTablePlus({ collection }: { collection: Collection, }) {

    function rowSpan() {
        const teams = collection.entities.map((entity: Entity) => {
            return {
                orgName: entity.properties.orgName,
                className: entity.properties.className,
                teamName:entity.properties.teamName
            }
        })

        return teams.map(item =>
            <Table.Row key={item.teamName} >
                <Table.Cell >
                    {item.orgName}
                </Table.Cell>
                <Table.Cell >
                    {item.className}
                </Table.Cell>
                <Table.Cell >
                    {item.teamName}
                </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <React.Fragment>
            { collection.entities.length != 0 ?
                <DefaultTable propNames={["Organization", "Classroom", "Team"]} >{rowSpan()}</DefaultTable>
                :
                <Header as='h3' textAlign='center'>You don't belong to any team.</Header>
            }
        </React.Fragment>
    )
}
