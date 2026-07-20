import { Box, Typography } from '@mui/material';
import AuditTable from '@/features/audit/AuditTable';

export default function AuditPage() {
  return (
    <Box>
      <Typography variant="h5" sx={{ mb: 2 }}>Журнал аудита</Typography>
      <Box sx={{ height: 'calc(100vh - 180px)' }}><AuditTable /></Box>
    </Box>
  );
}
