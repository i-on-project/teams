import * as React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import { HorizontalMenu } from "./common/components/Menu";
import { MenuContainer } from "./common/components/MenuContext";
import { MenuItemNameContainer } from "./common/components/MenuItemNameContext";
import * as Home from "./Home/Page"
import * as About from "./About/Page"

export function Router({logged}: {logged : boolean}){
    return (
        <HashRouter>
            <MenuContainer>
                <HorizontalMenu/>
                <MenuItemNameContainer>
                    {
                        logged ?
                            <Routes>
                                <Route path='' element={<Home.Page/>}/>
                            </Routes>
                            :
                            <Routes>
                                <Route path='' element={<About.Page/>}/>
                            </Routes>
                    }
                </MenuItemNameContainer>
            </MenuContainer>
        </HashRouter>
    )
}