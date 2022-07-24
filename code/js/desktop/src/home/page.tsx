import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Loader, Menu} from 'semantic-ui-react'
import { Fetch } from '../common/components/fetch'
import { useMenu } from '../common/components/MenuContext'
import { useMenuItemNameContext } from '../common/components/MenuItemNameContext'
import { Collection, Entity, Link_relation } from '../common/types/siren'
import { makeClassroom, makeHome, makeOrganization, makeOrganizations } from '../common/Uris'

//TODO: session
export function Page() {

    const setItems = useMenu().setItems

    React.useEffect(() => {
        setItems([
            {
                name: "Home",
                href: makeHome(),
                isActive: true
            },
            {
                name: "Organizations",
                href: makeOrganizations(),
            }
        ])
    }, [])

    return (
        <Fetch
            url={`/api${makeOrganizations()}`}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <Body collection={payload} />
            }
            renderLoading={() => <Loader />}
        />
    )

    function Body({ collection }: { collection: Collection }) {

        const navigate = useNavigate()
        const menuItemNameContext = useMenuItemNameContext()

        const nextUri = collection.links.find(link => link.rel === 'next')?.href
        const prevUri = collection.links.find(link => link.rel === 'prev')?.href

        return (
            <React.Fragment>
                <h1> Your Organizations and Classrooms</h1>
                    {
                        collection.entities.map((entity: Entity) =>
                            <Menu fluid vertical size='large'>
                                <Menu.Item  
                                    key={entity.properties.id} 
                                    onClick={() => { 
                                        menuItemNameContext.setOrgName(entity.properties.name)
                                        navigate(makeOrganization(entity.properties.id))
                                    }}
                                    name={entity.properties.name}
                                />
                                {
                                    getClassrooms(
                                        entity.properties.id, 
                                        entity.properties.name,
                                        entity.links.find((link: Link_relation) => link.rel == 'classrooms')
                                    )
                                }
                            </Menu>
                        )
                    }
                {
                    prevUri != null &&
                    <Button onClick={() => console.log('click')}>Previous</Button>
                }
                {
                    nextUri != null &&
                    <Button onClick={() => console.log('click')}>Next</Button>
                }
            </React.Fragment>
        )



        function getClassrooms(id: number, name: string, link?: Link_relation) {

            return (
                link != null && <Fetch
                    url={link.href}
                    renderBegin={() => <p>Waiting for URL...</p>}
                    renderOk={(payload) =>
                        <Menu.Menu fluid>
                            {
                                payload.entities.map((entity: Entity) =>
                                    <Menu.Item
                                        key={entity.properties.id} 
                                        onClick={() => { 
                                            menuItemNameContext.setOrgName(name)
                                            menuItemNameContext.setClassName(entity.properties.name)
                                            navigate(makeClassroom(id,entity.properties.id))
                                        }}
                                        name={entity.properties.name}
                                    />
                                )
                            }
                        </Menu.Menu>
                    }
                    renderLoading={() => <Loader/>}
                />
            )

        }
    }
}