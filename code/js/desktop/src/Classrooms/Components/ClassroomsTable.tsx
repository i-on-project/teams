import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { entry } from "../../../webpack.common";
import { BuildTable } from '../../commons/components/Table'
import { Entity } from '../../commons/types/siren';
import * as Uris from '../../commons/Uris';

export function ClassroomsTable({ entities }: { entities: Entity[] }) {

    const navigate = useNavigate()

    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.id,
                name: entity.properties.name,
                description: entity.properties.description,
                schoolYear: entity.properties.schoolYear,
                link: entity.links[0].href
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell onClick={() => navigate(Uris.makeOrganization(item.id), { replace: false })}> {item.name} </Table.Cell>
                <Table.Cell > {item.description} </Table.Cell>
                <Table.Cell > {item.schoolYear} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <BuildTable propNames={["Name", "Descrition", "School Year"]}>{rowSpan()}</BuildTable>
    )
}