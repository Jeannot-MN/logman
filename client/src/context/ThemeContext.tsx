import {ThemeProvider} from '@mui/material';
import * as React from 'react';
import {theme} from '../theme/Theme';

//import { theme } from '../theme/Theme';

interface Props {
    children: JSX.Element[]
}

export function ThemeController({children}: Props) {
    return (
        <ThemeProvider theme={theme}>
            {children.map(child => child)}
        </ThemeProvider>
    )
}