import * as React from 'react'
import { createRoot } from 'react-dom/client'
import {
    Routes,
    Route,
    HashRouter,
} from "react-router-dom";
import App from "./App";
//import 'bootstrap/dist/css/bootstrap.min.css';

const root = createRoot(
    document.getElementById('root')
)

root.render(

    <HashRouter>
        <Routes>
            <Route path="/" element={<App />}/>
        </Routes>
    </HashRouter>
)
