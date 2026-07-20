import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: { main: '#d32f2f' },
    secondary: { main: '#1976d2' },
    background: { default: '#0d1117', paper: '#161b22' },
  },
  typography: {
    fontFamily: 'Roboto, "Segoe UI", Arial, sans-serif',
  },
  components: {
    MuiCard: { styleOverrides: { root: { borderRadius: 12 } } },
  },
});

export default theme;
