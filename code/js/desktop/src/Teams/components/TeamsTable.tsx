import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { useMenuItemNameContext } from "../../common/components/MenuItemNameContext";
import { DefaultTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

/**
 * Function responsible for creating a table of teams.
 */
export function TeamsTable({ entities, orgId, classId }: { entities: Entity[], orgId: number, classId: number }) {

    const navigate = useNavigate()
    const menuItemNameContext = useMenuItemNameContext()

    /**
     * Function responsible for building the rows of the teams table.
     */
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
                <Table.Cell onClick={() => {
                    menuItemNameContext.setTeamName(item.name)
                    navigate(Uris.makeTeam(orgId, classId, item.id), { replace: false })
                }}> {item.name} </Table.Cell>
                <Table.Cell > {item.state} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <DefaultTable propNames={["Name", "State"]}>{rowSpan()}</DefaultTable>
    )
}