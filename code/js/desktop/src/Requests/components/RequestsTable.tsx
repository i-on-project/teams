import * as React from "react"
import { useContext } from "react";
import { Button, Table } from 'semantic-ui-react';
import { ChangedContext } from "../../common/components/changedStatus";
import { BuildTable } from '../../common/components/Table'
import { Action, Entity } from '../../common/types/siren';

export function RequestsTable({ entities }: { entities: Entity[] }) {

    const { setChanged } = useContext(ChangedContext)

    function onClick(action: Action) {

        fetch(`http://localhost:8080${action.href}`, {
            method: action.method,
            headers: {
                'Accept': action.type,
                'Content-Type': action.type
            },
            credentials: 'include'
        })
            .then( () => setChanged(true))
    }

    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.tid,
                name: entity.properties.teamName,
                actions: entity.actions,
                link: entity.links[0].href
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell key={item.id +'_name'}> {item.name} </Table.Cell>
                <Table.Cell textAlign="right" key={item.id + '_buttons'}>
                    <Button.Group>
                        <Button positive onClick={ () => onClick( item.actions.find( (it) => it.name == "accept-request" ))} key={'Accept_' + item.id}>Accept</Button>
                        <Button negative onClick={ () => onClick( item.actions.find( (it) => it.name == "decline-request"))} key={'Decline_' + item.id}>Decline</Button>
                    </Button.Group>
                </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <BuildTable propNames={["Name", ""]}>{rowSpan()}</BuildTable>
    )
}