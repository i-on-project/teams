import * as React from "react"
import { useNavigate, useParams } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { useMenuItemNameContext } from "../../common/components/MenuItemNameContext";
import { DefaultTable } from '../../common/components/Table'
import { Entity } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

/**
 * Function responsible for building a deliveries table.
 */
export function DeliveriesTable({ entities }: { entities: Entity[] }) {

    const navigate = useNavigate()
    const { orgId, classId, assId } = useParams()
    const menuItemNameContext = useMenuItemNameContext()

    /**
     * Function responsible for building the deliveries table rows.
     */
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
                <Table.Cell
                    onClick={() => {
                        menuItemNameContext.setDeliveryName(item.name)
                        navigate(Uris.makeDelivery(orgId, classId, assId, item.id), { replace: false })
                    }}> {item.name}
                </Table.Cell>
                <Table.Cell > {item.date} </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <DefaultTable propNames={["Name", "Date"]}>{rowSpan()}</DefaultTable>
    )
}