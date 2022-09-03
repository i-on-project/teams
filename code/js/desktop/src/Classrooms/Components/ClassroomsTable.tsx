import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { useMenuItemNameContext } from "../../common/components/MenuItemNameContext";
import { DefaultTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

/**
 * Function represents a table of classrooms
 */
export function ClassroomsTable({ entities, orgId }: { entities: Entity[], orgId: number }) {

    const navigate = useNavigate()
    const setClassroomName = useMenuItemNameContext().setClassName

    /**
     * Function responsible for building the table's rows.
     */
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
                <Table.Cell onClick={() => {
                    setClassroomName(item.name)
                    navigate(Uris.makeClassroom(orgId, item.id), { replace: false })
                }}> {item.name} </Table.Cell>
                <Table.Cell > {item.description} </Table.Cell>
                <Table.Cell > {item.schoolYear} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <DefaultTable propNames={["Name", "Descrition", "School Year"]}>{rowSpan()}</DefaultTable>
    )
}