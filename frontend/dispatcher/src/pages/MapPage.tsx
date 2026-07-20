import { useEffect } from 'react';
import { Box, Typography } from '@mui/material';
import { useAppDispatch } from '@/app/hooks';
import { fetchActiveIncidents } from '@/features/incidents/incidentsSlice';
import { fetchUnits } from '@/features/units/unitsSlice';
import IncidentMap from '@/features/map/IncidentMap';

export default function MapPage() {
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(fetchActiveIncidents());
    dispatch(fetchUnits());
  }, [dispatch]);

  return (
    <Box>
      <Typography variant="h5" sx={{ mb: 2 }}>Карта происшествий и сил</Typography>
      <Box sx={{ height: 'calc(100vh - 160px)', borderRadius: 2, overflow: 'hidden' }}>
        <IncidentMap />
      </Box>
    </Box>
  );
}
