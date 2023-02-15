import {Box, Typography} from "@mui/material";
import { Route, Routes } from 'react-router-dom';
import {Login} from "../../pages/login/Login";
import {Header} from "../Header/Header";
import { makeStyles } from 'tss-react/mui';


const useStyles = makeStyles()((theme) => {
    return {
        root: {
            width: '100vw',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: 'white'
        },
    }
});

export function Navigation() {
    const {classes} = useStyles();
    return (
        <Box className={classes.root}>
            <Header/>
            <Routes>
                <Route path="/login" element={<Login />} />
            </Routes>
        </Box>
    )
}