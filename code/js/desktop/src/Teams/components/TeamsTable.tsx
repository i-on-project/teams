import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { BuildTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function TeamsTable({ entities, orgId, classId }: { entities: Entity[], orgId: number, classId: number }) {

    const navigate = useNavigate()

    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.id,
                name: entity.properties.name,
                state: entity.properties.state,
                link: entity.links[0].href
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell onClick={() => navigate(Uris.makeTeam(orgId, classId, item.id), { replace: false })}> {item.name} </Table.Cell>
                <Table.Cell > {item.state} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <BuildTable propNames={["Name", "State"]}>{rowSpan()}</BuildTable>
    )
}