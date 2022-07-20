import * as React from "react"
import { useNavigate, useParams } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { useMenuItemNameContext } from "../../common/components/MenuItemNameContext";
import { DefaultTable, Paging } from '../../common/components/Table'
import { Collection } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function AssignmentsTable({ collection }: { collection: Collection }) {

  const navigate = useNavigate()  
  const { orgId, classId } = useParams()
  const setAssignmentName = useMenuItemNameContext().setAssignmentName

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
        <Table.Cell onClick={() => {
          setAssignmentName(item.name)
          navigate(Uris.makeAssignment(orgId, classId, item.id), { replace: false })  
        }}> {item.name} </Table.Cell>
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
    <DefaultTable propNames={["Name", "Release Date"]} pagingProps={paging}>{rowSpan()}</DefaultTable>
  )
}