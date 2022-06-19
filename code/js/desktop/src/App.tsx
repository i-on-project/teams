import * as React from 'react'
import { Route, Routes, HashRouter } from "react-router-dom";
import { BuildMenu, MenuItem } from './commons/components/Menu';
import * as Home from "./home/Page"
import * as Organizations from "./Organizations/Page"

const menuItems: MenuItem[] = [
  {
    name: "Home",
    href: ""
  }
]

export default function App() {

  return (
    <HashRouter>
      <BuildMenu items={menuItems}></BuildMenu>
      <Routes>
        <Route path='' element={<Home.Page />} />
        <Route path='/orgs' element={<Organizations.Page />} />
      </Routes>
    </HashRouter >
  )
}