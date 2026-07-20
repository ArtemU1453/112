import { ReactNode } from 'react';
import {
  AppBar, Box, Drawer, List, ListItemButton, ListItemIcon, ListItemText, Toolbar, Typography,
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import MapIcon from '@mui/icons-material/Map';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import HistoryIcon from '@mui/icons-material/History';
import LogoutIcon from '@mui/icons-material/Logout';
import { Link, useLocation } from 'react-router-dom';
import keycloak from '@/api/keycloak';

const DRAWER_WIDTH = 220;

const NAV = [
  { to: '/', label: 'Обстановка', icon: <DashboardIcon /> },
  { to: '/map', label: 'Карта', icon: <MapIcon /> },
  { to: '/units', label: 'Подразделения', icon: <LocalShippingIcon /> },
  { to: '/audit', label: 'Аудит', icon: <HistoryIcon /> },
];

export default function AppLayout({ children }: { children: ReactNode }) {
  const location = useLocation();
  const userName = keycloak.tokenParsed?.preferred_username ?? 'диспетчер';

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar position="fixed" sx={{ zIndex: (t) => t.zIndex.drawer + 1 }}>
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Система-112 · АРМ диспетчера
          </Typography>
          <Typography variant="body2" sx={{ mr: 2 }}>{userName}</Typography>
          <LogoutIcon sx={{ cursor: 'pointer' }} onClick={() => keycloak.logout()} />
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: DRAWER_WIDTH,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: DRAWER_WIDTH, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <List>
          {NAV.map((item) => (
            <ListItemButton
              key={item.to}
              component={Link}
              to={item.to}
              selected={location.pathname === item.to}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.label} />
            </ListItemButton>
          ))}
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3, mt: 8, minHeight: '100vh' }}>
        {children}
      </Box>
    </Box>
  );
}
