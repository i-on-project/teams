import * as React from "react"
import { Table } from 'semantic-ui-react';
import { DefaultTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';

/**
 * Function responsible for building a table of tags.
 */
export function TagsTable({ entities }: { entities: Entity[] }) {

    /**
     * Function responsible for building the rows of the Tags table.
     */
    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.id,
                teamName: entity.properties.teamName,
                name: entity.properties.name,
                date: entity.properties.date,
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell> {item.teamName} </Table.Cell>
                <Table.Cell> {item.name} </Table.Cell>
                <Table.Cell > {item.date} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <DefaultTable propNames={["Team", "Name", "Date"]}>{rowSpan()}</DefaultTable>
    )
}