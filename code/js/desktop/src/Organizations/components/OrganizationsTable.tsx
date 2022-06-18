import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { BuildTable, Paging } from '../../commons/components/Table'
import { Collection } from '../../commons/types/siren';
import * as Uris from '../../commons/Uris';

export type Props = {
  collection: Collection
}

export function OrganizationsTable({ collection }: Props) {

  const navigate = useNavigate()

  function rowSpan() {
    const projects = collection.entities.map(entity => {
      return {
        id: entity.properties.id,
        name: entity.properties.name,
        description: entity.properties.description,
        link: entity.links[0].href
      }
    })

    return projects.map(item =>
      <Table.Row key={item.id} >
        <Table.Cell onClick={() => navigate(Uris.makeOrganization(item.id), { replace: false })}> {item.name} </Table.Cell>
        <Table.Cell > {item.description} </Table.Cell>
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
    <BuildTable propNames={["Name", "Descrition"]} pagingProps={paging}>{rowSpan()}</BuildTable>
  )
}