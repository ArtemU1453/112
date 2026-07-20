import { useEffect } from 'react';
import { Box, Typography } from '@mui/material';
import { useAppDispatch } from '@/app/hooks';
import { fetchUnits } from '@/features/units/unitsSlice';
import UnitsTable from '@/features/units/UnitsTable';

export default function UnitsPage() {
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(fetchUnits());
    const interval = setInterval(() => dispatch(fetchUnits()), 20000);
    return () => clearInterval(interval);
  }, [dispatch]);

  return (
    <Box>
      <Typography variant="h5" sx={{ mb: 2 }}>Подразделения</Typography>
      <Box sx={{ height: 'calc(100vh - 180px)' }}><UnitsTable /></Box>
    </Box>
  );
}
