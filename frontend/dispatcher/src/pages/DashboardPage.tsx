import { useEffect, useState } from 'react';
import { Box, Button, Grid, Stack, Typography } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { useAppDispatch } from '@/app/hooks';
import { fetchActiveIncidents, fetchIncidentStats } from '@/features/incidents/incidentsSlice';
import { fetchUnits } from '@/features/units/unitsSlice';
import StatsBar from '@/features/incidents/StatsBar';
import IncidentTable from '@/features/incidents/IncidentTable';
import IncidentDetailsPanel from '@/features/incidents/IncidentDetailsPanel';
import IncidentCreateForm from '@/features/incidents/IncidentCreateForm';

export default function DashboardPage() {
  const dispatch = useAppDispatch();
  const [formOpen, setFormOpen] = useState(false);

  useEffect(() => {
    dispatch(fetchActiveIncidents());
    dispatch(fetchIncidentStats());
    dispatch(fetchUnits());
    const interval = setInterval(() => {
      dispatch(fetchIncidentStats());
    }, 30000);
    return () => clearInterval(interval);
  }, [dispatch]);

  return (
    <Box>
      <Stack direction="row" justifyContent="space-between" alignItems="center" sx={{ mb: 2 }}>
        <Typography variant="h5">Оперативная обстановка</Typography>
        <Button variant="contained" startIcon={<AddIcon />} onClick={() => setFormOpen(true)}>
          Новая карточка
        </Button>
      </Stack>
      <StatsBar />
      <Grid container spacing={2} sx={{ mt: 0.5 }}>
        <Grid item xs={12} md={8}>
          <Box sx={{ height: 560 }}><IncidentTable /></Box>
        </Grid>
        <Grid item xs={12} md={4}>
          <Box sx={{ height: 560 }}><IncidentDetailsPanel /></Box>
        </Grid>
      </Grid>
      <IncidentCreateForm open={formOpen} onClose={() => setFormOpen(false)} />
    </Box>
  );
}
