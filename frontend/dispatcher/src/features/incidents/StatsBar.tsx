import { Card, CardContent, Grid, Typography } from '@mui/material';
import { useAppSelector } from '@/app/hooks';

function StatCard({ label, value, color }: { label: string; value: number; color?: string }) {
  return (
    <Card>
      <CardContent sx={{ py: 1.5 }}>
        <Typography variant="h4" sx={{ color }}>{value}</Typography>
        <Typography variant="caption" color="text.secondary">{label}</Typography>
      </CardContent>
    </Card>
  );
}

export default function StatsBar() {
  const stats = useAppSelector((s) => s.incidents.stats);
  const active = useAppSelector((s) => s.incidents.active);
  const critical = active.filter((i) => i.priority === 'CRITICAL').length;

  return (
    <Grid container spacing={2}>
      <Grid item xs={3}><StatCard label="Активных" value={stats?.totalActive ?? active.length} /></Grid>
      <Grid item xs={3}><StatCard label="Критических" value={critical} color="#d32f2f" /></Grid>
      <Grid item xs={3}><StatCard label="За сутки" value={stats?.totalToday ?? 0} /></Grid>
      <Grid item xs={3}>
        <StatCard label="Назначено нарядов"
          value={stats?.byStatus?.DISPATCHED ?? 0} color="#f57c00" />
      </Grid>
    </Grid>
  );
}
