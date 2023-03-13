import React from 'react';
import {BrowserRouter} from "react-router-dom";
import {Navigation} from './modules/navigation/Navigation';
import {ThemeController} from "./context/ThemeContext";
import AuthContextProvider from "./context/AuthContext";
import {ToastContainer} from "react-toastify";
import {ApiClientContextProvider} from "./context/ApiClientContext";
import {ServiceContextProvider} from "./context/ServiceContext";

function App() {
    return (
        <React.StrictMode>
            <AuthContextProvider>
                <ApiClientContextProvider>
                    <ServiceContextProvider>
                        <ThemeController>
                            <ToastContainer position="top-right" autoClose={5000}/>
                            <BrowserRouter>
                                <Navigation/>
                            </BrowserRouter>
                        </ThemeController>
                    </ServiceContextProvider>
                </ApiClientContextProvider>
            </AuthContextProvider>
        </React.StrictMode>
    )
}

export default App
