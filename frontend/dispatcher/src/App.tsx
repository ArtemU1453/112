import { useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AppLayout from '@/components/AppLayout';
import DashboardPage from '@/pages/DashboardPage';
import MapPage from '@/pages/MapPage';
import UnitsPage from '@/pages/UnitsPage';
import AuditPage from '@/pages/AuditPage';
import { connectRealtime, disconnectRealtime } from '@/ws/realtime';

export default function App() {
  useEffect(() => {
    connectRealtime();
    return () => disconnectRealtime();
  }, []);

  return (
    <BrowserRouter>
      <AppLayout>
        <Routes>
          <Route path="/" element={<DashboardPage />} />
          <Route path="/map" element={<MapPage />} />
          <Route path="/units" element={<UnitsPage />} />
          <Route path="/audit" element={<AuditPage />} />
        </Routes>
      </AppLayout>
    </BrowserRouter>
  );
}
