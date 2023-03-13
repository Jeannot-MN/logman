import UserService from "../services/UserService";
import {createContext, useContext} from "react";
import {ApiClientContext} from "./ApiClientContext";
import {useCustomContext} from "../hooks/useCustomContext";

interface Props {
    children: JSX.Element
}

export interface ServiceContextType {
    userService: UserService
}

export const ServiceContext = createContext<ServiceContextType | null>(null);

export function ServiceContextProvider({children}: Props) {

    const apiClientContext = useCustomContext(ApiClientContext);

    return (
        <ServiceContext.Provider
            value={{
                userService: new UserService(apiClientContext)
            }}
        >
            {children}
        </ServiceContext.Provider>
    )
}