import * as React from 'react';
import { Table } from 'semantic-ui-react';
import { BuildTable } from '../../common/components/Table';
import { Entity } from '../../common/types/siren';

export function SimpleStudentsTable({ entities }: { entities: Entity[] }) {


    function rowSpan() {
      const organizations = entities.map(entity => {
        return {
          number: entity.properties.number,
          name: entity.properties.name,
          link: entity.links[0].href
        }
      })
  
      return organizations.map(item =>
        <Table.Row key={item.number} >
          <Table.Cell > {item.number} </Table.Cell>
          <Table.Cell > {item.name} </Table.Cell>
        </Table.Row>
      )
    }
  
    return (
      <BuildTable propNames={["Number", "Name"]}>{rowSpan()}</BuildTable>
    )
  }