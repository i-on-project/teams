import * as React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import { HorizontalMenu } from "./common/components/Menu";
import { MenuContainer } from "./common/components/MenuContext";
import { MenuItemNameContainer } from "./common/components/MenuItemNameContext";
import * as Home from "./Home/Page"
import * as About from "./About/Page"
import * as Introduction from "./Introduction/Page"
import * as LoginSignup from "./LoginSignup/LoginSignup"
import * as InviteCode from "./Invite-code/Page"

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
                                <Route path='' element={<Introduction.Page/>}/>
                                <Route path='/about' element={<About.Page/>}/>
                                <Route path='/start' element={<LoginSignup.Page/>}/>
                                <Route path='/invite-code/:code' element={<InviteCode.Page/>}/>
                            </Routes>
                    }
                </MenuItemNameContainer>
            </MenuContainer>
        </HashRouter>
    )
}