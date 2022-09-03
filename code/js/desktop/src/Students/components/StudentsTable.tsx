import * as React from "react"
import { Table } from 'semantic-ui-react';
import { DefaultTable, Paging } from '../../common/components/Table'
import { Collection } from '../../common/types/siren';

/**
 * Function represents a table of students.
 */
export function StudentsTable({ collection }: { collection: Collection }) {

  /**
   * Function used to build the rows of the table of students.
   */
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
    <DefaultTable propNames={["Number", "Name"]} pagingProps={paging}>{rowSpan()}</DefaultTable>
  )
}