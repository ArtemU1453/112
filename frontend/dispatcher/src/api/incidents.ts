import { apiGet, apiSend } from './http';
import type {
  Incident, IncidentHistory, IncidentPriority, IncidentStats, IncidentStatus,
  IncidentType, Page,
} from '@/types/domain';

export interface IncidentCreatePayload {
  type: IncidentType;
  priority: IncidentPriority;
  description: string;
  address: string;
  latitude?: number;
  longitude?: number;
  callerPhone?: string;
  callerName?: string;
  casualtiesCount: number;
  callId?: string;
}

export const incidentsApi = {
  list: (page = 0, size = 20) =>
    apiGet<Page<Incident>>(`/api/v1/incidents?page=${page}&size=${size}`),
  active: () => apiGet<Incident[]>('/api/v1/incidents/active'),
  stats: () => apiGet<IncidentStats>('/api/v1/incidents/stats'),
  byId: (id: string) => apiGet<Incident>(`/api/v1/incidents/${id}`),
  history: (id: string) => apiGet<IncidentHistory[]>(`/api/v1/incidents/${id}/history`),
  create: (payload: IncidentCreatePayload) =>
    apiSend<Incident>('POST', '/api/v1/incidents', payload),
  classify: (id: string, type: IncidentType, priority: IncidentPriority) =>
    apiSend<Incident>('PUT', `/api/v1/incidents/${id}/classify`, { type, priority }),
  changeStatus: (id: string, status: IncidentStatus, comment?: string) =>
    apiSend<Incident>('PUT', `/api/v1/incidents/${id}/status`, { status, comment }),
};
