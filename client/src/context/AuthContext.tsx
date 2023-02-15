import jwtDecode from 'jwt-decode';
import { createContext, useCallback, useContext, useEffect, useMemo, useRef, useState } from "react";
import useLocalStorage from 'react-use/lib/useLocalStorage';
import {Toast} from "../modules/Toast/Toast";
import {getApiServerUrl} from "../services/Utils";
import {Role} from "../types/types";
// import { Toast } from '../modules/Toast/Toast';

interface Props {
    children: JSX.Element
}

interface LoginResponse {
    access_token: string,
    token_type: string,
    expires_in: number,
    scope: string,
    jti: string
}

export function AuthContextProvider({ children }: Props) {
    const loggingOut = useRef(false);
    const authTokenLoaded = useRef<boolean>(false);

    const [localAuth, setLocalAuth] = useLocalStorage<AuthContextType>('auth', {
        authenticated: false,
    });

    const [auth, setAuth] = useState<AuthContextType | undefined>(() => {
        authTokenLoaded.current = true;
        return localAuth;
    });

    // const [login] = useLoginMutation();

    const handleLogin = useCallback(
        async function (username: string, password: string) {
            loggingOut.current = false;
            const result = await fetch(`${getApiServerUrl()}/auth/token`,{
                method: 'POST',
                headers:{
                    "Authorization": `Basic ${btoa(username+':'+password)}`
                }
            })

            if(result.ok){
                const data = (await result.json()) as LoginResponse;
                const token = data.access_token;

                const decoded = jwtDecode(token) as {
                    exp: number,
                    iat: number,
                    client_id: string,
                    authorities: RoleProps[],
                };

                const newAuth: AuthContextType = {
                    authenticated: true,
                    token,
                    roles: decoded.authorities
                        ? parseRoles(decoded.authorities)
                        : [],
                    expiration: decoded.exp,
                };

                setAuth(newAuth);
                setLocalAuth(newAuth);
            }
        },
        [setLocalAuth]
    );

    const handleLogout = useCallback(() => {
        if (auth?.authenticated) {
            setAuth({ authenticated: false });
            setLocalAuth({ authenticated: false });
        }
    }, [auth?.authenticated, setLocalAuth]);

    useEffect(() => {
        if (loggingOut.current) {
            handleLogout();
            loggingOut.current = false;
        }
    }, [loggingOut, handleLogout]);

    useEffect(() => {
        let innerAuth: AuthContextType | undefined = { authenticated: false };

        if (localAuth !== null) {
            if (localAuth?.authenticated && localAuth?.expiration * 1000 < Date.now()) {
                handleLogout();
                loggingOut.current = false;
            }

            innerAuth = localAuth;

            setAuth(innerAuth);
        }

    }, [localAuth, auth, handleLogout]);

    const hasRole = useCallback(
        (role: Role) => {
            if (!auth?.authenticated) {
                return false;
            }
            if (
                /*auth.roles?.indexOf(Role.ADMIN) !== -1 ||*/
                auth.roles?.indexOf(role) !== -1
            ) {
                return true;
            }

            return false;
        },
        [auth]
    );

    const value = useMemo(
        () => ({ auth, handleLogin, handleLogout, hasRole }),
        [auth, handleLogin, handleLogout, hasRole]
    );

    // Prevent double render before auth token loaded
    if (!authTokenLoaded.current) {
        return null;
    }

    //@ts-ignore
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

interface RoleProps {
    authority: string;
}

interface UserProps {
    id: number;
    name: string;
    profileImageUri: string;
}

function parseRoles(roles: RoleProps[]) {
    return roles.map((role) => {
        switch (role.authority) {
            case Role.SYSTEM_ADMIN:
                return Role.SYSTEM_ADMIN;
            case Role.COMPANY_ADMIN:
                return Role.COMPANY_ADMIN;
            case Role.DRIVER:
                return Role.DRIVER;
            case Role.ACCOUNTANT:
                return Role.ACCOUNTANT;
            default:
                return Role.UNKNOWN;
        }
    });
}


export const AuthContext = createContext<AuthContextStateType>({
    auth: { authenticated: false },
    handleLogin: async () => new Promise(() => { }),
    handleLogout: () => { },
    hasRole: () => true,
});

export type AuthContextType =
    | {
        token: string;
        authenticated: true;
        roles?: Role[];
        expiration: number;
    }
    | {
        authenticated: false;
    };

export interface AuthContextStateType {
    auth: AuthContextType;
    handleLogin: (username: string, password: string) => Promise<Response>;
    handleLogout: () => void;
    hasRole(role: Role): boolean;
}
export default AuthContextProvider;
export const useAuthContext = () => useContext(AuthContext);