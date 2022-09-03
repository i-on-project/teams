import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { useMenu } from "../common/components/MenuContext";
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext";
import { Resource, Link_relation } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeam, makeTeams } from "../common/Uris";
import { NotesAsComments } from "../Notes/components/NotesAsComments";
import { TeamInfo } from "./Components/TeamInfo";

/**
 * Team page, displays the information of an individual team.
 */
export function Page() {

    const { orgId, classId, teamId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeTeam(orgId, classId, teamId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload}></Body>}
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

/**
 * Body function represents the body of the page, it is responsible for displaying all the relevant information to the user.
 */
function Body({ resource }: { resource: Resource }) {

    const { orgId, classId, teamId } = useParams()
    const setItems = useMenu().setItems
    const notesLink = resource.links.find((it) => it.rel == "notes")
    const menuItemNameContext = useMenuItemNameContext()

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
                name: menuItemNameContext.orgName,
                href: makeOrganization(orgId)
            },
            {
                name: menuItemNameContext.className,
                href: makeClassroom(orgId, classId),
                hasSubItems: true,
                subItems: [
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId) },
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            },
            {
                name: menuItemNameContext.teamName,
                href: makeTeam(orgId, classId, teamId),
                isActive: true
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        <Container>
            <TeamInfo resource={resource}></TeamInfo>
            {getNotes(notesLink)}
        </Container>
    )
}

/**
 * Function used to display the notes of the team
 */
function getNotes(link: Link_relation) {

    return (
        <Fetch
            url={link.href}
            renderBegin={() => <p>Waiting for URL...</p>}
            renderOk={(payload) =>
                <NotesAsComments collection={payload} />
            }
            renderLoading={() => <Loader />} />
    )
}