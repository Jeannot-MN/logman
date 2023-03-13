import {createContext} from "react";
import axios, {Axios, AxiosRequestConfig, AxiosResponse} from "axios";
import {getApiServerUrl} from "../services/Utils";
import {useAuthContext} from "./AuthContext";

interface Props {
    children: JSX.Element
}

interface CType{
    get<R>(url: string): Promise<R>;
}

const t: CType = {
    async get<R>(url: string): Promise<R> {
        try{
            const axiosResponse = await axios.get<R>(url);
            return axiosResponse.data;
        } catch (error) {

        }
    }
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