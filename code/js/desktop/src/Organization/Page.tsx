import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { ClassroomsTable } from "../Classrooms/Components/ClassroomsTable";
import { BuildForm } from "../common/components/BuildForm";
import { ErrorNOk, Error } from "../common/components/error";
import { Fetch } from "../common/components/fetch";
import { BuildMenu, MenuItem } from "../common/components/Menu";
import { Action, Entity, Resource } from "../common/types/siren";
import { makeClassrooms, makeHome, makeOrganization, makeOrganizations } from "../common/Uris";
import { OrganizationInfo } from "./components/OrganizationInfo";

export function Page() {

    const { orgId } = useParams()

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
            name: "Classrooms",
            href: makeClassrooms(orgId)
        }
    ]


    return (
        <Fetch
            url={`/api${makeOrganization(orgId)}`}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <div>
                    <BuildMenu items={menuItems} currItem={makeOrganization(orgId)} ></BuildMenu>
                    <Body resource={payload}></Body>
                </div>
            }
            renderLoading={() => <Loader />}
            renderNok={message => <ErrorNOk message={message} />}
            renderError={error => <Error error={error} />}
        />
    )
}

//TODO: missing components
function Body({ resource }: { resource: Resource }) {
    return (
        <Container>
            <OrganizationInfo resource={resource} />
            <Divider />
            <h1>Classrooms in this organization</h1>
            {
                <ClassroomsTable entities={resource.entities}></ClassroomsTable>
            }
        </Container>
    )
}