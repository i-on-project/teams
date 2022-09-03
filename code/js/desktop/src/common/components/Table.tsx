import * as React from 'react'
import { Table, Loader, Button } from 'semantic-ui-react'
import { useUri } from './UriContext'

export type Paging = {
    nextUri?: string,
    prevUri?: string
}

/**
 * Function used by all table functions. It is a generic function that can be complemented to build custom tables for each individual resource.
 */
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

/**
 * Table loading representation
 */
export function DefaultTableLoading() {

    return (
        <Table loading>
            <Loader active inline='centered' size='medium'></Loader>
        </Table>
    )
}