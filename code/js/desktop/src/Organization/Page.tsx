import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { ClassroomsTable } from "../Classrooms/Components/ClassroomsTable";
import { ErrorNOk, Error } from "../common/components/error";
import { Fetch } from "../common/components/fetch";
import { BuildMenu, MenuItem } from "../common/components/Menu";
import { MenuContext } from "../common/components/MenuStatus";
import { Resource } from "../common/types/siren";
import { makeClassrooms, makeHome, makeOrganization, makeOrganizations } from "../common/Uris";
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
                renderNok={message => <ErrorNOk message={message} />}
                renderError={error => <Error error={error} />}
            />
        </div>
    )
}

function Body({ resource, orgId }: { resource: Resource, orgId: any }) {

    const { setItems } = React.useContext(MenuContext)

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
                name: "Organization",
                href: makeOrganization(orgId),
                isActive: true
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <OrganizationInfo resource={resource} />
            <Divider />
            <h1>Classrooms in this organization</h1>
            {
                <ClassroomsTable entities={resource.entities} orgId={orgId}></ClassroomsTable>
            }
        </Container>
    )
}