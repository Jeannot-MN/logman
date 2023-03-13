import React from 'react';
import {BrowserRouter} from "react-router-dom";
import {Navigation} from './modules/navigation/Navigation';
import {ThemeController} from "./context/ThemeContext";
import AuthContextProvider from "./context/AuthContext";
import {ToastContainer} from "react-toastify";
import {ApiClientContextProvider} from "./context/ApiClientContext";

function App() {
    return (
        <React.StrictMode>
            <AuthContextProvider>
                <ApiClientContextProvider>
                    <ThemeController>
                        <ToastContainer position="top-right" autoClose={5000}/>
                        <BrowserRouter>
                            <Navigation/>
                        </BrowserRouter>
                    </ThemeController>
                </ApiClientContextProvider>
            </AuthContextProvider>
        </React.StrictMode>
    )
}

export default App
