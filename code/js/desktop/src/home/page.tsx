import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Loader, Menu } from 'semantic-ui-react'
import { Fetch } from '../common/components/fetch'
import { useMenu } from '../common/components/MenuContext'
import { useMenuItemNameContext } from '../common/components/MenuItemNameContext'
import { NothingToShow } from '../common/components/NothingToShow'
import { UriContext, useUri } from '../common/components/UriContext'
import { Collection, Entity, Link_relation } from '../common/types/siren'
import { makeClassroom, makeHome, makeOrganization, makeOrganizations } from '../common/Uris'

/**
 * Function represents the home page.
 */
export function Page() {

    const setItems = useMenu().setItems
    //Uri state used for paging.
    const [uri, setUri] = React.useState(`/api${makeOrganizations()}`)

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
            url={uri}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <UriContext.Provider value={{ uri, setUri }} >
                    <Body collection={payload} setUri={setUri} />
                </UriContext.Provider>
            }
            renderLoading={() => <Loader />}
        />
    )

    /**
     * Body function represents the page body. It is responsible for displaying the relevant information to the user.
     */
    function Body({ collection, setUri }: { collection: Collection, setUri: any }) {

        const navigate = useNavigate()
        const menuItemNameContext = useMenuItemNameContext()
        const uri = useUri()

        const nextUri = collection.links.find(link => link.rel === 'next')?.href
        const prevUri = collection.links.find(link => link.rel === 'prev')?.href

        const sugestion = {
            message: "Go to organizations and create a new one.",
            href: makeOrganizations()
        }

        return (
            collection.entities.length != 0 ?
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
                    <Button onClick={() => setUri(prevUri!!)}>Previous</Button>
                }
                {
                    nextUri != null &&
                    <Button onClick={() => setUri(nextUri!!)}>Next</Button>
                }
            </React.Fragment>
            :
            <NothingToShow sugestion={sugestion}>No Organizations to show.</NothingToShow>
        )

        function getClassrooms(id: number, name: string, link?: Link_relation) {

            return (
                link != null && <Fetch
                    url={link.href}
                    renderBegin={() => <p>Waiting for URL...</p>}
                    renderOk={(payload) =>
                        <Menu.Menu>
                            {
                                payload.entities.map((entity: Entity) =>
                                    <Menu.Item
                                        key={entity.properties.id}
                                        onClick={() => {
                                            menuItemNameContext.setOrgName(name)
                                            menuItemNameContext.setClassName(entity.properties.name)
                                            navigate(makeClassroom(id, entity.properties.id))
                                        }}
                                        name={entity.properties.name}
                                    />
                                )
                            }
                        </Menu.Menu>
                    }
                    renderLoading={() => <Loader />}
                />
            )

        }
    }
}