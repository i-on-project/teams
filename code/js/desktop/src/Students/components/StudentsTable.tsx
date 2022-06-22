import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { BuildTable, Paging } from '../../common/components/Table'
import { Collection } from '../../common/types/siren';

export function StudentsTable({ collection }: { collection: Collection }) {


  function rowSpan() {
    const organizations = collection.entities.map(entity => {
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

  const nextUri = collection.links.find(link => link.rel === 'next')?.href
  const prevUri = collection.links.find(link => link.rel === 'prev')?.href

  const paging: Paging = {
    nextUri: nextUri,
    prevUri: prevUri
  }

  return (
    <BuildTable propNames={["Number", "Name"]} pagingProps={paging}>{rowSpan()}</BuildTable>
  )
}