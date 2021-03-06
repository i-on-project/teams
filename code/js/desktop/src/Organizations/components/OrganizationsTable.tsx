import * as React from "react"
import { useNavigate } from 'react-router-dom';
import { Table } from 'semantic-ui-react';
import { useMenuItemNameContext } from "../../common/components/MenuItemNameContext";
import { DefaultTable, Paging } from '../../common/components/Table'
import { Collection } from '../../common/types/siren';
import * as Uris from '../../common/Uris';

export function OrganizationsTable({ collection }: { collection: Collection }) {

  const setName = useMenuItemNameContext().setOrgName

  function onClick(id: number, name: string) {
    setName(name)
    navigate(Uris.makeOrganization(id), { replace: false })
  }

  const navigate = useNavigate()  

  function rowSpan() {
    const organizations = collection.entities.map(entity => {
      return {
        id: entity.properties.id,
        name: entity.properties.name,
        description: entity.properties.description,
        link: entity.links[0].href
      }
    })

    return organizations.map(item =>
      <Table.Row key={item.id} >
        <Table.Cell onClick={() => onClick(item.id,item.name)}> {item.name} </Table.Cell>
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
    <DefaultTable propNames={["Name", "Descrition"]} pagingProps={paging}>{rowSpan()}</DefaultTable>
  )
}