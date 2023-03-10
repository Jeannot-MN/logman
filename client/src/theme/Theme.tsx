import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      main: '#276552',
      contrastText: 'white',
    },
    secondary: {
      main: '#ccac7c',
      contrastText: 'white',
    },
  },
  typography: {
    fontFamily: 'Poppins',
    h1: {
      fontWeight: 600,
      fontSize: '80px',
      lineHeight: '112px',
      fontFamily: 'Poppins',
      letterSpacing: '-1.5px'
    },
    h2: {
      fontWeight: 600,
      fontSize: '64px',
      lineHeight: '72px',
      fontFamily: 'Poppins',
      letterSpacing: '-0.5px',
    },
    h3: {
      fontWeight: 600,
      fontSize: '48px',
      lineHeight: '56px',
      fontFamily: 'Poppins',
      letterSpacing: '0',
    },
    h4: {
      fontWeight: 600,
      fontSize: '34px',
      lineHeight: '40px',
      fontFamily: 'Poppins',
      letterSpacing: '0.25px',
    },
    h5: {
      fontWeight: 600,
      fontSize: '24px',
      lineHeight: '32px',
      fontFamily: 'Poppins',
      letterSpacing: '0',
    },
    h6: {
      fontWeight: 600,
      fontSize: '20px',
      lineHeight: '24px',
      fontFamily: 'Poppins',
      letterSpacing: '0.15px',
    },
    button: {
      fontFamily: 'Poppins',
      fontStyle: 'normal',
      fontWeight: 500,
      fontSize: '14px',
      lineHeight: '30px',
      letterSpacing: '1.35px',
      textTransform: 'uppercase',
    },
    body1: {
      fontFamily: 'Poppins',
      fontStyle: 'normal',
      fontWeight: 'normal',
      fontSize: '16px',
      lineHeight: '24px',
      letterSpacing: '0.444444px',
    },
    body2: {
      fontFamily: 'Poppins',
      fontStyle: 'normal',
      fontWeight: 'normal',
      fontSize: '14px',
      lineHeight: '20px',
      letterSpacing: '0.25px',
    },
  },
  components:{
  }
});
