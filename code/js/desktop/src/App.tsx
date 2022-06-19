import * as React from 'react'
import { Route, Routes, HashRouter } from "react-router-dom";
import * as Home from "./home/Page"
import * as Organizations from "./Organizations/Page"

export default function App() {

  return (
    <HashRouter>
      <Routes>
        <Route path='' element={<Home.Page />} />
        <Route path='/orgs' element={<Organizations.Page />} />
      </Routes>
    </HashRouter >
  )
}