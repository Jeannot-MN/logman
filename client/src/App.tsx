import React from 'react';
import {BrowserRouter} from "react-router-dom";
import { Navigation } from './modules/navigation/Navigation';
import {ThemeController} from "./modules/context/ThemeContext";

function App() {
    return (
        <React.StrictMode>
            <ThemeController>
                <BrowserRouter>
                    <Navigation/>
                </BrowserRouter>
                <></>
            </ThemeController>
        </React.StrictMode>
    )
}

export default App
