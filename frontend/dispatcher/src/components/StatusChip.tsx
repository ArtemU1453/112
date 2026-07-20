import Chip from '@mui/material/Chip';
import type { IncidentStatus } from '@/types/domain';

const LABELS: Record<IncidentStatus, string> = {
  RECEIVED: 'Принят',
  CLASSIFIED: 'Классифицирован',
  DISPATCHED: 'Наряд назначен',
  IN_PROGRESS: 'В работе',
  RESOLVED: 'Ликвидирован',
  CLOSED: 'Закрыт',
  CANCELLED: 'Отменён',
};

export default function StatusChip({ status }: { status: IncidentStatus }) {
  return <Chip size="small" variant="outlined" label={LABELS[status]} />;
}
