import * as React from 'react'
import { Route, Routes, HashRouter } from "react-router-dom";
import * as Home from "./home/Page"
import * as Organizations from "./Organizations/Page"
import * as Organization from "./Organization/Page"

import * as Classroom from "./Classroom/Page"



export default function App() {

  return (
    <HashRouter>
      <Routes>
        <Route path='' element={<Home.Page />} />
        <Route path='/orgs' element={<Organizations.Page />} />
        <Route path='/orgs/:orgId' element={<Organization.Page />} />
        <Route path='/orgs/:orgId/classrooms/:classId' element={<Classroom.Page />} />
      </Routes>
    </HashRouter >
  )
}