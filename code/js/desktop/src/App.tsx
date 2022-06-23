import * as React from 'react'
import { Route, Routes, HashRouter } from "react-router-dom";
import { MenuContainer } from './common/components/MenuStatus';
import { BuildMenu } from './common/components/Menu';
import * as Home from "./home/Page"
import * as Organizations from "./Organizations/Page"
import * as Organization from "./Organization/Page"
import * as Classroom from "./Classroom/Page"
import * as Teams from "./Teams/Page"
import * as Students from "./Students/Page"
import * as Assignments from "./Assignments/Page"
import * as Assignment from "./Assignment/Page"
import * as Delivery from "./Delivery/Page"

export default function App() {

  return (
    
      <HashRouter>
       <MenuContainer> 
      <BuildMenu />
        <Routes>
          <Route path='' element={<Home.Page />} />
          <Route path='/orgs' element={<Organizations.Page />} />
          <Route path='/orgs/:orgId' element={<Organization.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId' element={<Classroom.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId/Teams' element={<Teams.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId/students' element={<Students.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId/assignments' element={<Assignments.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId' element={<Assignment.Page />} />
          <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId/deliveries/:delId' element={<Delivery.Page />} />
        </Routes>
        </MenuContainer>
      </HashRouter >
    
  )
}