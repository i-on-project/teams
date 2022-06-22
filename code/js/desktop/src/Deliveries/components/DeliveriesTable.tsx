import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { BuildTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function DeliveriesTable({ entities }: { entities: Entity[] }) {

    const navigate = useNavigate()

    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.id,
                name: entity.properties.name,
                date: entity.properties.date,
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell onClick={() => navigate("/", { replace: false })}> {item.name} </Table.Cell>
                <Table.Cell > {item.date} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <BuildTable propNames={["Name", "Date"]}>{rowSpan()}</BuildTable>
    )
}