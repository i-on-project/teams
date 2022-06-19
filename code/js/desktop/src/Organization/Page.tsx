import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Divider, Loader } from "semantic-ui-react";
import { ErrorNOk } from "../commons/components/error";
import { Fetch } from "../commons/components/fetch";
import { BuildMenu, MenuItem } from "../commons/components/Menu";
import { Resource } from "../commons/types/siren";
import { makeHome, makeOrganization, makeOrganizations } from "../commons/Uris";
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
            renderLoading={() => <Loader /> }
            renderNok={message => <ErrorNOk message={message} />}
        />
    )
}

//TODO: missing components
function Body({ resource }: { resource: Resource }) {
    return(
        <Container>
            <OrganizationInfo resource={resource}/>
            <Divider/>
            
        </Container>
    )
}