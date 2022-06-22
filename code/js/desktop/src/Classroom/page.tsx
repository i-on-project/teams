import * as React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Container, Divider, Loader } from "semantic-ui-react";
import { ErrorNOk, Error } from "../common/components/error";
import { Fetch } from "../common/components/fetch";
import { BuildMenu, MenuItem } from "../common/components/Menu";
import { Resource } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { TeamsTable } from "../Teams/components/TeamsTable";
import { ClassroomInfo } from "./components/ClassroomInfo";

export function Page() {

    const { orgId, classId } = useParams()
    const navigate = useNavigate()

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
            name: "Classroom",
            href: makeClassroom(orgId, classId),
            isDropDown: true,
            dropDownOptions: [
                { key: 1, text: 'Teams', value: () => navigate(makeTeams(orgId, classId)) },
                { key: 2, text: 'Students', value: () => navigate(makeStudentsClassroom(orgId, classId)) },
                { key: 3, text: 'Requests', value: () => navigate(makeRequests(orgId, classId)) },
                { key: 4, text: 'Assignments', value: () => navigate(makeAssignments(orgId, classId)) }
              ]
        }
    ]

    return (
        <div>
            <BuildMenu items={menuItems} currItem={makeClassroom(orgId, classId)} ></BuildMenu>
            <Fetch
                url={`/api${makeClassroom(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload} orgId={orgId} classId={classId}></Body> }
                renderLoading={() => <Loader />}
                renderNok={message => <ErrorNOk message={message} />}
                renderError={error => <Error error={error} />}
            />
        </div>
    )
}

function Body({ resource, orgId, classId }: { resource: Resource, orgId: any, classId: any }) {

    return (
        <Container>
            <ClassroomInfo resource={resource} />
            <Divider/>
            <h1>Teams in this classrooms</h1>
            {
                <TeamsTable entities={resource.entities} orgId={orgId} classId={classId}></TeamsTable>
            }
        </Container>
    )
}