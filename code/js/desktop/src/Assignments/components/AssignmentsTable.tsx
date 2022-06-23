import * as React from "react"
import { useNavigate, useParams } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { BuildTable, Paging } from '../../common/components/Table'
import { Collection } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function AssignmentsTable({ collection }: { collection: Collection }) {

  const navigate = useNavigate()  
  const { orgId, classId } = useParams()

  function rowSpan() {
    const assignments = collection.entities.map(entity => {
      return {
        id: entity.properties.id,
        name: entity.properties.name,
        releaseDate: entity.properties.releaseDate,
      }
    })

    return assignments.map(item =>
      <Table.Row key={item.id} >
        <Table.Cell onClick={() => navigate(Uris.makeAssignment(orgId, classId, item.id), { replace: false })}> {item.name} </Table.Cell>
        <Table.Cell > {item.releaseDate} </Table.Cell>
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
    <BuildTable propNames={["Name", "Release Date"]} pagingProps={paging}>{rowSpan()}</BuildTable>
  )
}