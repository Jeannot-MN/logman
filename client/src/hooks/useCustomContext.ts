import {Context, useContext} from "react";

export function useCustomContext<T>(context: Context<T>){
    const ctx = useContext<T>(context);

    if (ctx === null) {
        throw new Error("Context is not available");
    }

    return ctx
}