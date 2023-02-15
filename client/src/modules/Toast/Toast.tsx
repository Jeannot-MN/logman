import React from 'react';
import 'react-toastify/dist/ReactToastify.css';
import {Alert, AlertProps} from "@mui/material";
import {toast} from "react-toastify";

type Props = Omit<AlertProps, 'severity' | 'children'>;
export function Toast(
  type: AlertProps['severity'],
  message: React.ReactNode,
  alertProps?: Props
) {
  const toastId = toast(
    <Alert
      severity={type}
      {...alertProps}
      onClose={() => {
        toast.dismiss(toastId);
      }}
    >
      {message}
    </Alert>,
    {
      hideProgressBar: true,
      closeButton: false,
    }
  );
  return toastId;
}
