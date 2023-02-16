import {Box, Typography} from "@mui/material";
import { Route, Routes } from 'react-router-dom';
import {Login} from "../../pages/login/Login";
import {Header} from "../Header/Header";
import { makeStyles } from 'tss-react/mui';
import {ForgotPassword} from "../../pages/forgot-password/ForgotPassword";
import {Home} from "../../pages/home/Home";


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
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/forgotpassword" element={<ForgotPassword />} />
            </Routes>
        </Box>
    )
}