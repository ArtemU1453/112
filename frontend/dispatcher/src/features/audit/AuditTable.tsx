import { useEffect, useState } from 'react';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { auditApi } from '@/api/audit';
import type { AuditEvent } from '@/types/domain';

const columns: GridColDef<AuditEvent>[] = [
  {
    field: 'occurredAt', headerName: 'Время', width: 180,
    valueFormatter: (value) => new Date(value as string).toLocaleString('ru-RU'),
  },
  { field: 'service', headerName: 'Сервис', width: 160 },
  { field: 'action', headerName: 'Действие', width: 180 },
  { field: 'entityType', headerName: 'Сущность', width: 120 },
  { field: 'actor', headerName: 'Пользователь', width: 140 },
  {
    field: 'details', headerName: 'Детали', flex: 1, minWidth: 200,
    valueGetter: (_value, row) => JSON.stringify(row.details),
  },
];

export default function AuditTable() {
  const [rows, setRows] = useState<AuditEvent[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    auditApi.search({})
      .then((page) => setRows(page.content))
      .catch(() => setRows([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <DataGrid
      rows={rows}
      columns={columns}
      loading={loading}
      getRowId={(row) => row.id}
      density="compact"
      pageSizeOptions={[25, 50, 100]}
      initialState={{ pagination: { paginationModel: { pageSize: 50 } } }}
      sx={{ height: '100%' }}
    />
  );
}
