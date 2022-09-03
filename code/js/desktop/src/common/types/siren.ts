/**
 * Siren type definition.
 */

export type Collection = {
    class: [ string , "collection" ],
    properties: {
        pageIndex: number,
        pageSize: number
    },
    entities: Entity[],
    actions: Action[],
    links: Link_relation[]
}

export type Resource = {
    class: [ string ],
    properties: any,
    entities?: Entity[],
    actions: Action[],
    links: Link_relation[]
}

export type Entity = {
    class: string[],
    rel: string[],
    properties: any,
    actions?: Action[],
    links: Link_relation[]
}

export type Action = {
    name: string,
    title: string,
    method: string,
    href: string,
    type: string,
    possibilities?: Possibility[],
    fields: Field[]
}

export type Field = {
    name: string,
    type: string
}

export type Possibility = {
    field: string,
    name: string,
    value: string
}

export type Link_relation = {
    rel: string,
    href: string
}