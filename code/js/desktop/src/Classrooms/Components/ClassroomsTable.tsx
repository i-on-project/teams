import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { DefaultTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function ClassroomsTable({ entities, orgId }: { entities: Entity[], orgId: number }) {

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

        /*
        FIXME: Change link to individual classroom 
        */

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell onClick={() => navigate(Uris.makeClassroom(orgId, item.id), { replace: false })}> {item.name} </Table.Cell>
                <Table.Cell > {item.description} </Table.Cell>
                <Table.Cell > {item.schoolYear} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <DefaultTable propNames={["Name", "Descrition", "School Year"]}>{rowSpan()}</DefaultTable>
    )
}