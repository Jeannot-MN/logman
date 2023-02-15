import React from 'react';
import {Box, BoxProps} from "@mui/material";

export default function FormItem({children}: BoxProps) {
  return (
    <Box px={2} pb={2} style={{boxSizing: 'border-box'}} width="100%">
      {children}
    </Box>
  );
}
