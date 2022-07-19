import * as React from "react";
import { useParams } from "react-router-dom";
import { Container, Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { useMenu } from "../common/components/MenuContext";
import { Resource, Link_relation } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeam, makeTeams } from "../common/Uris";
import { NotesAsComments } from "../Notes/components/NotesAsComments";
import { TeamInfo } from "./Components/TeamInfo";

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

function Body({ resource }: { resource: Resource }) {

    const { orgId, classId, teamId } = useParams()
    const setItems = useMenu().setItems
    const notesLink = resource.links.find((it) => it.rel == "notes")

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
                href: makeOrganization(orgId)
            },
            {
                name: "Classroom",
                href: makeClassroom(orgId, classId),
                hasSubItems: true,
                subItems: [
                    { name: 'Description', href: makeClassroom(orgId, classId)},
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId)},
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            },
            {
                name: "Team",
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