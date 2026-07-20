import Chip from '@mui/material/Chip';
import type { IncidentPriority } from '@/types/domain';

const COLORS: Record<IncidentPriority, 'error' | 'warning' | 'info' | 'default'> = {
  CRITICAL: 'error',
  HIGH: 'warning',
  MEDIUM: 'info',
  LOW: 'default',
};

const LABELS: Record<IncidentPriority, string> = {
  CRITICAL: 'Критический',
  HIGH: 'Высокий',
  MEDIUM: 'Средний',
  LOW: 'Низкий',
};

export default function PriorityChip({ priority }: { priority: IncidentPriority }) {
  return <Chip size="small" color={COLORS[priority]} label={LABELS[priority]} />;
}
