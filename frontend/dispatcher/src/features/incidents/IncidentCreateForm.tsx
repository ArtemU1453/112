import { useState } from 'react';
import {
  Button, Dialog, DialogActions, DialogContent, DialogTitle, Grid, MenuItem, TextField,
} from '@mui/material';
import { useAppDispatch } from '@/app/hooks';
import { createIncident } from './incidentsSlice';
import type { IncidentPriority, IncidentType } from '@/types/domain';

const TYPES: { value: IncidentType; label: string }[] = [
  { value: 'FIRE', label: 'Пожар' },
  { value: 'MEDICAL', label: 'Медицина' },
  { value: 'POLICE', label: 'Правонарушение' },
  { value: 'GAS_LEAK', label: 'Утечка газа' },
  { value: 'TRAFFIC_ACCIDENT', label: 'ДТП' },
  { value: 'WATER_RESCUE', label: 'На воде' },
  { value: 'HAZMAT', label: 'ХОВ' },
  { value: 'TECHNOLOGICAL', label: 'Техногенная' },
  { value: 'NATURAL', label: 'Природная' },
  { value: 'OTHER', label: 'Прочее' },
];

const PRIORITIES: { value: IncidentPriority; label: string }[] = [
  { value: 'CRITICAL', label: 'Критический' },
  { value: 'HIGH', label: 'Высокий' },
  { value: 'MEDIUM', label: 'Средний' },
  { value: 'LOW', label: 'Низкий' },
];

interface Props {
  open: boolean;
  onClose: () => void;
}

export default function IncidentCreateForm({ open, onClose }: Props) {
  const dispatch = useAppDispatch();
  const [type, setType] = useState<IncidentType>('FIRE');
  const [priority, setPriority] = useState<IncidentPriority>('HIGH');
  const [description, setDescription] = useState('');
  const [address, setAddress] = useState('');
  const [callerPhone, setCallerPhone] = useState('');
  const [casualties, setCasualties] = useState(0);
  const [error, setError] = useState<string>();

  const submit = async () => {
    try {
      await dispatch(createIncident({
        type, priority, description, address,
        callerPhone: callerPhone || undefined,
        casualtiesCount: casualties,
      })).unwrap();
      onClose();
      setDescription('');
      setAddress('');
      setCallerPhone('');
      setCasualties(0);
    } catch (e) {
      setError((e as Error).message);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>Новая карточка происшествия</DialogTitle>
      <DialogContent>
        <Grid container spacing={2} sx={{ mt: 0 }}>
          <Grid item xs={6}>
            <TextField select label="Тип" fullWidth value={type}
              onChange={(e) => setType(e.target.value as IncidentType)}>
              {TYPES.map((t) => <MenuItem key={t.value} value={t.value}>{t.label}</MenuItem>)}
            </TextField>
          </Grid>
          <Grid item xs={6}>
            <TextField select label="Приоритет" fullWidth value={priority}
              onChange={(e) => setPriority(e.target.value as IncidentPriority)}>
              {PRIORITIES.map((p) => <MenuItem key={p.value} value={p.value}>{p.label}</MenuItem>)}
            </TextField>
          </Grid>
          <Grid item xs={12}>
            <TextField label="Адрес" fullWidth value={address}
              onChange={(e) => setAddress(e.target.value)} required />
          </Grid>
          <Grid item xs={12}>
            <TextField label="Описание" fullWidth multiline rows={3} value={description}
              onChange={(e) => setDescription(e.target.value)} required />
          </Grid>
          <Grid item xs={6}>
            <TextField label="Телефон заявителя" fullWidth value={callerPhone}
              onChange={(e) => setCallerPhone(e.target.value)} placeholder="+375291234567" />
          </Grid>
          <Grid item xs={6}>
            <TextField label="Пострадавших" type="number" fullWidth value={casualties}
              onChange={(e) => setCasualties(Number(e.target.value))}
              inputProps={{ min: 0 }} />
          </Grid>
          {error && <Grid item xs={12} sx={{ color: 'error.main' }}>{error}</Grid>}
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Отмена</Button>
        <Button variant="contained" onClick={submit}
          disabled={!address || !description}>Создать</Button>
      </DialogActions>
    </Dialog>
  );
}
