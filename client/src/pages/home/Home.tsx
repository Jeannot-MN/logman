import {Box, Typography} from "@mui/material";
import {useAuthContext} from "../../context/AuthContext";
import {useEffect} from "react";
import {useNavigate} from "react-router";
import {useScreenSize} from "../../hooks/useScreenSize";

export function Home() {
    const {auth} = useAuthContext();
    const navigate = useNavigate();
    const isDesktop = useScreenSize(600);

    useEffect(() => {
        if (!auth.authenticated) {
            navigate("/login");
        }
    })

    return (
        <Box width={"100%"} display={"flex"} flexDirection={"column"} justifyContent={"center"} alignItems={"center"}>
            <Typography variant={isDesktop ? "h3" : "h4"}>Oops😅 my guy/girl.</Typography>
            <Typography variant={isDesktop ? "h3" : "h4"}>I am still being built🦾🧷🛑.</Typography>
        </Box>
    )
}