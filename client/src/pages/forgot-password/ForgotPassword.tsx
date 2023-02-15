import {Form, Formik} from "formik";
import {Box, Button, Link, Typography} from "@mui/material";
import * as yup from "yup";
import {Toast} from "../../modules/Toast/Toast";
import FormItem from "../../atoms/FormItem";
import {FTextField} from "../../modules/FMaterial/FTextfield/FTextField";
import React from "react";
import {useNavigate} from "react-router";

const formSchema = yup.object().shape({
    email: yup.string().required('Email is required.'),
});

export function ForgotPassword() {

    const navigate = useNavigate();
    return (
        <Box display="flex" justifyContent="center">
            <Formik
                initialValues={{
                    email: ''
                }}
                validationSchema={formSchema}
                onSubmit={async () => {
                    await new Promise((res) => {
                        setTimeout(res, 1000);
                    })
                    Toast('success', "You will be receive a password reset link on your email.");
                }}
            >
                {({submitForm}) => {
                    return (
                        <Box width="100%" display={'flex'}>
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
                                    width="400px"
                                    pb={10}
                                >
                                    <Box pb={3} textAlign="center" maxWidth="500px">
                                        <Typography variant="h5">Reset Password</Typography>
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
                                        <Button
                                            variant="contained"
                                            size="large"
                                            color="primary"
                                            fullWidth
                                            onClick={() => {
                                                submitForm();
                                            }}
                                        >
                                            Reset Password
                                        </Button>
                                    </FormItem>
                                    <FormItem>
                                        <Box display="flex" justifyContent="end" pb={3}>
                                            <Link
                                                component={'button'}
                                                color="secondary"
                                                onClick={() => {
                                                    navigate('/login');
                                                }}
                                            >
                                                Back to Login
                                            </Link>
                                        </Box>
                                    </FormItem>
                                </Box>
                            </Form>
                        </Box>
                    )
                }}
            </Formik>
        </Box>
    )
}