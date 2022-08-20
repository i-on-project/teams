import * as React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import { HorizontalMenu } from "./common/components/Menu";
import * as Home from "./Home/Page"

export function Router(){
    return (
        <HashRouter>
            <HorizontalMenu/>
            <Routes>
                <Route path='' element={<Home.Page/>}/>
            </Routes>
        </HashRouter>
    )
}