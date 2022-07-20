import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { ClassroomsTable } from "../Classrooms/Components/ClassroomsTable";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { useMenu } from "../common/components/MenuContext";
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext";
import { Resource } from "../common/types/siren";
import { makeHome, makeOrganization, makeOrganizations } from "../common/Uris";
import { OrganizationInfo } from "./components/OrganizationInfo";

export function Page() {

    const { orgId } = useParams()

    return (
        <div>
            
            <Fetch
                url={`/api${makeOrganization(orgId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) =>
                    <Body resource={payload} orgId={orgId}></Body>
                }
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ resource, orgId }: { resource: Resource, orgId: any }) {


    const setItems = useMenu().setItems
    const orgName = useMenuItemNameContext().orgName

    React.useEffect(() => {
        const menuItems: MenuItem[] = [
            {
                name: "Home",
                href: makeHome()
            },
            {
                name: "Organizations",
                href: makeOrganizations()
            },
            { 
                name: orgName, 
                href: makeOrganization(orgId), 
                isActive: true 
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <OrganizationInfo resource={resource} />
            { resource.entities.length != 0 &&
                <React.Fragment>
                    <Divider />
                    <h1>Classrooms in this organization</h1>
                    <ClassroomsTable entities={resource.entities} orgId={orgId}></ClassroomsTable>
                </React.Fragment>
            }
        </Container>
    )
}