import React, {useEffect} from 'react';
import {useAuthContext} from "../../context/AuthContext";
import {Box, Button, Link, Typography} from "@mui/material";
import {Form, Formik} from "formik";
import * as yup from 'yup';
import {useNavigate} from 'react-router';
import {Toast} from "../../modules/Toast/Toast";
import {FTextField} from "../../modules/FMaterial/FTextfield/FTextField";
import {FTextFieldPassword} from "../../modules/FMaterial/FTextfield/FTextFieldPassword";
import FormItem from "../../atoms/FormItem";
import LoginPageImage from '../../assets/LoginPageImage.jpg';
import {useScreenSize} from "../../hooks/useScreenSize";

const signInSchema = yup.object().shape({
    email: yup.string().required('Email is required.'),
    password: yup.string().required('Password is required.'),
});

export function Login() {
    const navigate = useNavigate();
    const {auth, handleLogin} = useAuthContext();
    const showPageImage = useScreenSize(800);
    const screenSizeGT450px = useScreenSize(400);

    useEffect(() => {
        if (auth.authenticated) {
            navigate('/')
        }
    }, [auth]);

    return (
        <Box display="flex" justifyContent="center">
            <Formik
                initialValues={{
                    email: '',
                    password: '',
                }}
                validationSchema={signInSchema}
                onSubmit={async ({email, password}) => {
                    try {
                        await handleLogin(email, password);
                        navigate('/');
                    } catch (error: any) {
                        Toast('error', error);
                    }
                }}
            >
                {({submitForm}) => {
                    return (
                        <Box width="100%" display={'flex'}>
                            <>
                                <Box width={showPageImage ? "50%" : "100%"} display="flex" justifyContent="center"
                                     alignItems="center">
                                    <Form
                                        style={{display: 'flex', justifyContent: 'center'}}
                                        onKeyDown={(event) => {
                                            if (event.keyCode === 13) {
                                                event.preventDefault();
                                                submitForm();
                                            }
                                        }}
                                    >
                                        <Box
                                            display="flex"
                                            flexDirection="column"
                                            width={screenSizeGT450px ? "400px" : "250px"}
                                            pb={10}
                                        >
                                            <Box pb={3} textAlign="center" maxWidth="500px">
                                                <Typography variant="h5">Welcome</Typography>
                                            </Box>

                                            <FormItem>
                                                <FTextField
                                                    fullWidth
                                                    field={{name: 'email'}}
                                                    label="Email"
                                                    placeholder="Email"
                                                />
                                            </FormItem>

                                            <FormItem>
                                                <FTextFieldPassword
                                                    fullWidth
                                                    field={{
                                                        name: 'password',
                                                    }}
                                                    type="password"
                                                    label="Password"
                                                    placeholder="Password"
                                                />
                                            </FormItem>
                                            <FormItem>
                                                <Button
                                                    variant="contained"
                                                    size="large"
                                                    color="primary"
                                                    fullWidth
                                                    onClick={() => {
                                                        submitForm();
                                                    }}
                                                >
                                                    Log In
                                                </Button>
                                            </FormItem>
                                            <FormItem>
                                                <Box display="flex" justifyContent="end" pb={3}>
                                                    <Link
                                                        component={'button'}
                                                        color="secondary"
                                                        onClick={() => {
                                                            navigate('/forgotpassword');
                                                        }}
                                                    >
                                                        Forgot Password?
                                                    </Link>
                                                </Box>
                                            </FormItem>
                                        </Box>
                                    </Form>
                                </Box>

                                {showPageImage && (
                                    <Box width="50%">
                                        <Box>
                                            <img
                                                style={{
                                                    width: '100%',
                                                    height: '100vh',
                                                    objectFit: "cover"
                                                }}
                                                src={LoginPageImage}
                                            />
                                        </Box>
                                    </Box>
                                )}
                            </>
                        </Box>
                    );
                }}
            </Formik>
        </Box>
    );
}