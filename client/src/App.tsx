import React from 'react';
import {BrowserRouter} from "react-router-dom";
import {Navigation} from './modules/navigation/Navigation';
import {ThemeController} from "./context/ThemeContext";
import AuthContextProvider from "./context/AuthContext";
import {ToastContainer} from "react-toastify";

function App() {
    return (
        <React.StrictMode>
            <AuthContextProvider>
                <ThemeController>
                    <ToastContainer position="top-right" autoClose={5000}/>
                    <BrowserRouter>
                        <Navigation/>
                    </BrowserRouter>
                </ThemeController>
            </AuthContextProvider>
        </React.StrictMode>
    )
}

export default App
