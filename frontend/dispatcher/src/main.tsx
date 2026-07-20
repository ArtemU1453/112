import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { store } from '@/app/store';
import theme from '@/theme/theme';
import keycloak from '@/api/keycloak';
import App from './App';

function renderApp() {
  ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
      <Provider store={store}>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <App />
        </ThemeProvider>
      </Provider>
    </React.StrictMode>,
  );
}

const authRequired = import.meta.env.VITE_AUTH_DISABLED !== 'true';

if (authRequired) {
  keycloak
    .init({ onLoad: 'login-required', pkceMethod: 'S256', checkLoginIframe: false })
    .then((authenticated) => {
      if (authenticated) {
        renderApp();
      }
    })
    .catch(() => {
      document.body.innerHTML =
        '<h2 style="font-family:sans-serif">Ошибка подключения к Keycloak</h2>';
    });
} else {
  renderApp();
}
