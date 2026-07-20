import { DataGrid, GridColDef } from '@mui/x-data-grid';
import Chip from '@mui/material/Chip';
import { useAppSelector } from '@/app/hooks';
import type { Unit } from '@/types/domain';

const UNIT_TYPE_LABELS: Record<string, string> = {
  FIRE_TRUCK: 'Автоцистерна', LADDER_TRUCK: 'Автолестница', AMBULANCE: 'Скорая',
  RESCUE_SQUAD: 'АСО', POLICE_PATROL: 'Патруль', GAS_SERVICE: 'Газовая',
  HAZMAT_UNIT: 'РХБЗ', WATER_RESCUE: 'Водолазы', COMMAND_VEHICLE: 'Штаб',
};

const STATUS_COLOR: Record<string, 'success' | 'warning' | 'error' | 'info' | 'default'> = {
  AVAILABLE: 'success', DISPATCHED: 'warning', EN_ROUTE: 'warning',
  ON_SCENE: 'error', RETURNING: 'info', OUT_OF_SERVICE: 'default',
};

const columns: GridColDef<Unit>[] = [
  { field: 'callSign', headerName: 'Позывной', width: 120 },
  {
    field: 'type', headerName: 'Тип', width: 140,
    valueFormatter: (value) => UNIT_TYPE_LABELS[value as string] ?? value,
  },
  {
    field: 'status', headerName: 'Статус', width: 140,
    renderCell: (params) => (
      <Chip size="small" color={STATUS_COLOR[params.value] ?? 'default'} label={params.value} />
    ),
  },
  { field: 'stationName', headerName: 'Часть', flex: 1, minWidth: 160 },
  { field: 'crewSize', headerName: 'Расчёт', width: 90 },
];

export default function UnitsTable() {
  const units = useAppSelector((s) => s.units.units);
  const loading = useAppSelector((s) => s.units.loading);
  return (
    <DataGrid
      rows={units}
      columns={columns}
      loading={loading}
      getRowId={(row) => row.id}
      density="compact"
      pageSizeOptions={[10, 25, 50]}
      initialState={{ pagination: { paginationModel: { pageSize: 25 } } }}
      sx={{ height: '100%' }}
    />
  );
}
