import * as React from 'react'
import { useContext } from 'react'
import { Table, Loader, Button } from 'semantic-ui-react'
import { UriContext, useUri } from '../PagingContext'

export type Paging = {
    nextUri?: string,
    prevUri?: string
}

export function DefaultTable({ propNames, pagingProps, children }: { propNames: string[], pagingProps?: Paging, children: React.ReactNode }) {
    const uri = useUri()

    return (
        <div>
            <Table selectable>
                <Table.Header>{
                    propNames.map((propName) =>
                        <Table.HeaderCell width={5} key={propName}>
                            {propName}
                        </Table.HeaderCell>
                    )
                }</Table.Header>

                <Table.Body>
                    {children}
                </Table.Body>
            </Table>

            {
                pagingProps != null &&
                <div>
                    {pagingProps.prevUri ? <Button onClick={() => uri.setUri(pagingProps.prevUri!!)}>Previous</Button> : null}
                    {pagingProps.nextUri ? <Button onClick={() => uri.setUri(pagingProps.nextUri!!)}>Next</Button> : null}
                </div>
            }
        </div>

    )
}

export function DefaultTableLoading() {

    return (
        <Table loading>
            <Loader active inline='centered' size='medium'></Loader>
        </Table>
    )
}