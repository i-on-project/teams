import * as React from "react"
import { HashRouter, Route, Routes } from "react-router-dom";
import { Grid } from "semantic-ui-react";
import { VerticalFixedMenu } from "../common/components/Menu";
import { MenuContainer } from "../common/components/MenuContext";
import { MenuItemNameContainer } from "../common/components/MenuItemNameContext";
import * as Home from "../home/Page"
import * as Organizations from "../Organizations/Page"
import * as Organization from "../Organization/Page"
import * as Classroom from "../Classroom/Page"
import * as Teams from "../Teams/Page"
import * as Team from "../Team/Page"
import * as Students from "../Students/Page"
import * as Assignments from "../Assignments/Page"
import * as Assignment from "../Assignment/Page"
import * as Delivery from "../Delivery/Page"
import * as Requests from "../Requests/Page"
import { ChangedContainer } from "../common/components/changedStatus";

/**
 * App's implementation of a router, allows navigation between pages.
 */
export function Router() {

  return (
    <HashRouter>
      <MenuContainer>
        <ChangedContainer>
          <Grid columns={2}>
            <Grid.Column width={3}>
              <VerticalFixedMenu />
            </Grid.Column>
            <Grid.Column width={12} style={{ marginTop: "16px" }}>
              <MenuItemNameContainer>
                <Routes>
                  <Route path='' element={<Home.Page />} />
                  <Route path='/orgs' element={<Organizations.Page />} />
                  <Route path='/orgs/:orgId' element={<Organization.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId' element={<Classroom.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/teams' element={<Teams.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/teams/:teamId' element={<Team.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/students' element={<Students.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/assignments' element={<Assignments.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId' element={<Assignment.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId/deliveries/:delId' element={<Delivery.Page />} />
                  <Route path='/orgs/:orgId/classrooms/:classId/requests' element={<Requests.Page />} />
                </Routes>
              </MenuItemNameContainer>
            </Grid.Column>
          </Grid>
        </ChangedContainer>

      </MenuContainer>
    </HashRouter >
  )
}