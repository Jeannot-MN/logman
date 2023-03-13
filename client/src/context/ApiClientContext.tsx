import {createContext, useContext} from "react";
import {Axios} from "axios";
import {getApiServerUrl} from "../services/Utils";
import {useAuthContext} from "./AuthContext";

interface Props {
    children: JSX.Element
}

export interface ApiClientContextType {
    client: Axios
}

export const ApiClientContext = createContext<ApiClientContextType|null>(null);

export function ApiClientContextProvider({children}: Props) {

    const {auth} = useAuthContext();

    return (
        <ApiClientContext.Provider
            value={{
                client: new Axios({
                    baseURL: `${getApiServerUrl()}/api/v1`,
                    headers: {
                        "Authorization": auth.authenticated ? `Bearer ${auth.token}` : null
                    }
                })
            }}
        >
            {children}
        </ApiClientContext.Provider>
    )
}