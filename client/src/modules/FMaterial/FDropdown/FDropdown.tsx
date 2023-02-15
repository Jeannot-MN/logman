/*
import React, {useState} from 'react';
import TextField, {TextFieldProps} from '@material-ui/core/TextField';
import {useField, FieldHookConfig} from 'formik';
import MenuItem from '@material-ui/core/MenuItem';
import {Box, Button, SelectProps} from '@material-ui/core';
import {useScrollbarWidth} from 'react-use/lib/useScrollbarWidth';
import './FDropdown.css';

interface CO {
  id: string;
  description: string;
}
interface Props {
  field: string | FieldHookConfig<string | string[]>;
  data?: CO[];
  SelectProps?: Partial<SelectProps>;
}
//FDropdown only takes in strings
export function FDropdown(props: Props & TextFieldProps) {
  const [{onChange, ...field}, meta, helpers] = useField(props.field);
  const multiple = props.SelectProps ? props.SelectProps.multiple : false;
  const [open, setOpen] = useState(false);

  const sbw = useScrollbarWidth();
  function Buttons() {
    const pr = (sbw || 8) / 8;
    return (
      <>
        <Box pl={1} flexGrow={1}>
          <Button
            fullWidth
            size="small"
            variant="outlined"
            color="secondary"
            onClick={(e) => {
              e.preventDefault();
              e.stopPropagation();
              if (multiple) {
                helpers.setValue([]);
              } else {
                helpers.setValue('');
              }
            }}
          >
            Clear
          </Button>
        </Box>
        <Box pl={1} pr={pr} flexGrow={1}>
          <Button
            fullWidth
            size="small"
            variant="contained"
            color="secondary"
            onClick={(e) => {
              e.preventDefault();
              e.stopPropagation();
              setOpen(false);
            }}
          >
            Done
          </Button>
        </Box>
      </>
    );
  }

  return (
    <>
      <TextField
        {...props}
        {...field}
        SelectProps={{
          native: false,
          open: open,
          onOpen: () => {
            setOpen(true);
          },
          onClose: () => {
            setOpen(false);
          },
          ...props.SelectProps,
        }}
        onChange={(e) => {
          onChange(e);
          props?.onChange && props.onChange(e);
        }}
        error={Boolean(meta.touched && meta.error)}
        select
        color="primary"
        helperText={meta.touched && meta.error ? meta.error : ''}
      >
        {multiple && (
          <Box
            position="absolute"
            top={1}
            pt={0}
            zIndex={100}
            width="100%"
            display="flex"
          >
            <Buttons />
          </Box>
        )}

        {multiple && [
          <MenuItem key={0} disabled></MenuItem>,
          <MenuItem key={1} disabled></MenuItem>,
          <MenuItem key={2} disabled></MenuItem>,
          <MenuItem key={2} disabled></MenuItem>,
          <MenuItem key={2} disabled></MenuItem>,
        ]}

        {multiple && <MenuItem disabled>Select 1 or many</MenuItem>}
        {props.data?.map(({id, description}) => {
          return (
            <MenuItem key={id} value={id}>
              {description}
            </MenuItem>
          );
        })}
      </TextField>
    </>
  );
}
*/

export function FDropdown(){
    return "Hello";
}
