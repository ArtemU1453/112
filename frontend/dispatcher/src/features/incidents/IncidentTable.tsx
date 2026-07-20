import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useAppDispatch, useAppSelector } from '@/app/hooks';
import { selectIncident } from './incidentsSlice';
import PriorityChip from '@/components/PriorityChip';
import StatusChip from '@/components/StatusChip';
import type { Incident } from '@/types/domain';

const TYPE_LABELS: Record<string, string> = {
  FIRE: 'Пожар', MEDICAL: 'Медицина', POLICE: 'Правонарушение', GAS_LEAK: 'Газ',
  TRAFFIC_ACCIDENT: 'ДТП', WATER_RESCUE: 'На воде', HAZMAT: 'ХОВ',
  TECHNOLOGICAL: 'Техно', NATURAL: 'Природа', FALSE_ALARM: 'Ложный', OTHER: 'Прочее',
};

const columns: GridColDef<Incident>[] = [
  { field: 'number', headerName: 'Номер', width: 150 },
  {
    field: 'type', headerName: 'Тип', width: 130,
    valueFormatter: (value) => TYPE_LABELS[value as string] ?? value,
  },
  {
    field: 'priority', headerName: 'Приоритет', width: 140,
    renderCell: (params) => <PriorityChip priority={params.value} />,
  },
  {
    field: 'status', headerName: 'Статус', width: 160,
    renderCell: (params) => <StatusChip status={params.value} />,
  },
  { field: 'address', headerName: 'Адрес', flex: 1, minWidth: 200 },
  { field: 'casualtiesCount', headerName: 'Постр.', width: 80 },
  {
    field: 'createdAt', headerName: 'Создано', width: 160,
    valueFormatter: (value) => new Date(value as string).toLocaleString('ru-RU'),
  },
];

export default function IncidentTable() {
  const dispatch = useAppDispatch();
  const incidents = useAppSelector((s) => s.incidents.active);
  const loading = useAppSelector((s) => s.incidents.loading);

  return (
    <DataGrid
      rows={incidents}
      columns={columns}
      loading={loading}
      getRowId={(row) => row.id}
      onRowClick={(params) => dispatch(selectIncident(params.id as string))}
      pageSizeOptions={[10, 25, 50]}
      initialState={{ pagination: { paginationModel: { pageSize: 25 } } }}
      density="compact"
      sx={{ height: '100%', cursor: 'pointer' }}
    />
  );
}
