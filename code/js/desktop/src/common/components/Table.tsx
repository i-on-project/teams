import * as React from 'react'
import { useContext } from 'react'
import { Table, Loader, Button } from 'semantic-ui-react'
import { PagingContext } from '../PagingContext'

export type Paging = {
    nextUri?: string,
    prevUri?: string
}

export function DefaultTable({ propNames, pagingProps, children }: { propNames: string[], pagingProps?: Paging, children: React.ReactNode }) {
    const { setPaging } = useContext(PagingContext)

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
                    {pagingProps.prevUri ? <Button onClick={() => console.log('click')}>Previous</Button> : null}
                    {pagingProps.nextUri ? <Button onClick={() => console.log('click')}>Next</Button> : null}
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