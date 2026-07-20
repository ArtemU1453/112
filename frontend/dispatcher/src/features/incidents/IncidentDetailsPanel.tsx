import { useEffect, useState } from 'react';
import {
  Box, Button, Card, CardContent, Divider, MenuItem, Stack, TextField, Typography,
} from '@mui/material';
import { useAppDispatch, useAppSelector } from '@/app/hooks';
import { incidentsApi } from '@/api/incidents';
import { unitsApi } from '@/api/units';
import { selectIncident, clearSelected } from './incidentsSlice';
import PriorityChip from '@/components/PriorityChip';
import StatusChip from '@/components/StatusChip';
import type { Assignment, IncidentHistory, IncidentStatus, UnitType } from '@/types/domain';

const NEXT_STATUS: Record<IncidentStatus, IncidentStatus[]> = {
  RECEIVED: ['CLASSIFIED', 'DISPATCHED', 'CANCELLED'],
  CLASSIFIED: ['DISPATCHED', 'CANCELLED'],
  DISPATCHED: ['IN_PROGRESS', 'CANCELLED'],
  IN_PROGRESS: ['RESOLVED', 'CANCELLED'],
  RESOLVED: ['CLOSED', 'IN_PROGRESS'],
  CLOSED: [],
  CANCELLED: [],
};

const TYPE_TO_UNIT: Record<string, UnitType> = {
  FIRE: 'FIRE_TRUCK', MEDICAL: 'AMBULANCE', POLICE: 'POLICE_PATROL', GAS_LEAK: 'GAS_SERVICE',
  TRAFFIC_ACCIDENT: 'RESCUE_SQUAD', WATER_RESCUE: 'WATER_RESCUE', HAZMAT: 'HAZMAT_UNIT',
};

export default function IncidentDetailsPanel() {
  const dispatch = useAppDispatch();
  const incident = useAppSelector((s) => s.incidents.selected);
  const [history, setHistory] = useState<IncidentHistory[]>([]);
  const [assignments, setAssignments] = useState<Assignment[]>([]);
  const [nextStatus, setNextStatus] = useState<IncidentStatus | ''>('');
  const [comment, setComment] = useState('');
  const [message, setMessage] = useState<string>();

  useEffect(() => {
    if (!incident) return;
    incidentsApi.history(incident.id).then(setHistory).catch(() => setHistory([]));
    unitsApi.byIncident(incident.id).then(setAssignments).catch(() => setAssignments([]));
    setNextStatus('');
    setMessage(undefined);
  }, [incident]);

  if (!incident) {
    return (
      <Card sx={{ height: '100%' }}>
        <CardContent>
          <Typography color="text.secondary">Выберите карточку в журнале</Typography>
        </CardContent>
      </Card>
    );
  }

  const applyStatus = async () => {
    if (!nextStatus) return;
    try {
      await incidentsApi.changeStatus(incident.id, nextStatus, comment || undefined);
      dispatch(selectIncident(incident.id));
      setComment('');
      setMessage('Статус обновлён');
    } catch (e) {
      setMessage((e as Error).message);
    }
  };

  const autoDispatch = async () => {
    if (!incident.latitude || !incident.longitude) {
      setMessage('Нет координат для автодиспетчеризации');
      return;
    }
    try {
      const unitType = TYPE_TO_UNIT[incident.type] ?? 'RESCUE_SQUAD';
      await unitsApi.autoDispatch(incident.id, unitType, incident.latitude, incident.longitude);
      unitsApi.byIncident(incident.id).then(setAssignments);
      setMessage('Наряд назначен автоматически');
    } catch (e) {
      setMessage((e as Error).message);
    }
  };

  return (
    <Card sx={{ height: '100%', overflow: 'auto' }}>
      <CardContent>
        <Stack direction="row" justifyContent="space-between" alignItems="center">
          <Typography variant="h6">{incident.number}</Typography>
          <Button size="small" onClick={() => dispatch(clearSelected())}>Закрыть</Button>
        </Stack>
        <Stack direction="row" spacing={1} sx={{ my: 1 }}>
          <PriorityChip priority={incident.priority} />
          <StatusChip status={incident.status} />
        </Stack>
        <Typography variant="body2"><b>Адрес:</b> {incident.address}</Typography>
        <Typography variant="body2"><b>Описание:</b> {incident.description}</Typography>
        <Typography variant="body2"><b>Пострадавших:</b> {incident.casualtiesCount}</Typography>
        {incident.callerPhone && (
          <Typography variant="body2"><b>Телефон:</b> {incident.callerPhone}</Typography>
        )}

        <Divider sx={{ my: 2 }} />
        <Typography variant="subtitle2" gutterBottom>Изменение статуса</Typography>
        <Stack direction="row" spacing={1}>
          <TextField select size="small" label="Новый статус" value={nextStatus}
            onChange={(e) => setNextStatus(e.target.value as IncidentStatus)}
            sx={{ minWidth: 160 }}>
            {NEXT_STATUS[incident.status].map((s) => (
              <MenuItem key={s} value={s}>{s}</MenuItem>
            ))}
          </TextField>
          <Button variant="contained" size="small" onClick={applyStatus}
            disabled={!nextStatus}>Применить</Button>
        </Stack>
        <TextField size="small" label="Комментарий" fullWidth sx={{ mt: 1 }}
          value={comment} onChange={(e) => setComment(e.target.value)} />

        <Divider sx={{ my: 2 }} />
        <Stack direction="row" justifyContent="space-between" alignItems="center">
          <Typography variant="subtitle2">Назначенные наряды</Typography>
          <Button size="small" variant="outlined" onClick={autoDispatch}>Авто-наряд</Button>
        </Stack>
        {assignments.length === 0 && (
          <Typography variant="body2" color="text.secondary">Нет назначений</Typography>
        )}
        {assignments.map((a) => (
          <Typography key={a.id} variant="body2">
            {a.unitCallSign} — {a.status}
            {a.distanceKm != null && ` (${a.distanceKm.toFixed(1)} км)`}
          </Typography>
        ))}

        <Divider sx={{ my: 2 }} />
        <Typography variant="subtitle2" gutterBottom>История</Typography>
        {history.map((h, idx) => (
          <Box key={idx} sx={{ mb: 0.5 }}>
            <Typography variant="caption" color="text.secondary">
              {new Date(h.occurredAt).toLocaleString('ru-RU')} · {h.actor}
            </Typography>
            <Typography variant="body2">{h.action}{h.comment ? ` — ${h.comment}` : ''}</Typography>
          </Box>
        ))}

        {message && (
          <Typography variant="body2" color="secondary" sx={{ mt: 1 }}>{message}</Typography>
        )}
      </CardContent>
    </Card>
  );
}
