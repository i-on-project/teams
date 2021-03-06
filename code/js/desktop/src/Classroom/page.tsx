import * as React from "react";
import { useParams } from "react-router-dom";
import { Loader } from "semantic-ui-react";
import { Fetch } from "../common/components/fetch";
import { MenuItem } from "../common/components/Menu";
import { useMenu } from "../common/components/MenuContext";
import { useMenuItemNameContext } from "../common/components/MenuItemNameContext";
import { Resource } from "../common/types/siren";
import { makeAssignments, makeClassroom, makeHome, makeOrganization, makeOrganizations, makeRequests, makeStudentsClassroom, makeTeams } from "../common/Uris";
import { ClassroomInfo } from "./components/ClassroomInfo";

export function Page() {

    const { orgId, classId } = useParams()

    return (
        <div>
            <Fetch
                url={`/api${makeClassroom(orgId, classId)}`}
                renderBegin={() => <p>Waiting for URL...</p>}
                renderOk={(payload) => <Body resource={payload} orgId={orgId} classId={classId}></Body>}
                renderLoading={() => <Loader />}
            />
        </div>
    )
}

function Body({ resource, orgId, classId }: { resource: Resource, orgId: any, classId: any }) {

    const setItems = useMenu().setItems

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
                isActive: true,
                hasSubItems: true,
                subItems: [
                    { name: 'Students', href: makeStudentsClassroom(orgId, classId)},
                    { name: 'Teams', href: makeTeams(orgId, classId) },
                    { name: 'Requests', href: makeRequests(orgId, classId) },
                    { name: 'Assignments', href: makeAssignments(orgId, classId) }
                ]
            }
        ]
        setItems(menuItems)
    }, [])

    return (
        <div>
            <ClassroomInfo resource={resource} orgId={orgId} classId={classId} />
        </div>
    )
}