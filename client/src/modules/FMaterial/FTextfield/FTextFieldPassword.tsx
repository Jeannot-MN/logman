import React, {useState} from 'react';
import {FTextField, FTextFieldProps} from './FTextField';
import {IconButton, InputAdornment} from "@mui/material";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';

//eslint-disable-next-line
export function FTextFieldPassword<Val = any>(props: FTextFieldProps<Val>) {
  const [showPassword, setShowPassword] = useState(false);
  return (
    <FTextField
      InputProps={{
        type: showPassword ? 'text' : 'password',
        endAdornment: (
          <InputAdornment position="end">
            <IconButton
              tabIndex={-1}
              aria-label="toggle password visibility"
              onClick={() => {
                setShowPassword(!showPassword);
              }}
              onMouseDown={(e) => {
                e.preventDefault();
              }}
              edge="end"
            >
              {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
            </IconButton>
          </InputAdornment>
        ),
      }}
      {...props}
    />
  );
}
