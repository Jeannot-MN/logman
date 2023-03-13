import {createContext} from "react";
import {Axios} from "axios";
import {getApiServerUrl} from "../services/Utils";
import {useAuthContext} from "./AuthContext";

interface Props {
    children: JSX.Element
}

export const ApiClientContext = createContext<Axios>(new Axios({
    baseURL: `${getApiServerUrl()}/api/v1`
}));

export function ApiClientContextProvider({children}: Props) {

    const {auth} = useAuthContext();

    return (
        <ApiClientContext.Provider
            value={new Axios({
                baseURL: `${getApiServerUrl()}/api/v1`,
                headers: {
                    "Authorization": auth.authenticated ? `Bearer ${auth.token}` : null
                }
            })}
        >
            {children}
        </ApiClientContext.Provider>
    )
}