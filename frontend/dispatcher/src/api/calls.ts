import { apiGet, apiSend } from './http';
import type { CallRecord } from '@/types/domain';

export const callsApi = {
  active: () => apiGet<CallRecord[]>('/api/v1/calls/active'),
  byId: (id: string) => apiGet<CallRecord>(`/api/v1/calls/${id}`),
  start: (callerPhone: string, direction: 'INBOUND' | 'OUTBOUND') =>
    apiSend<CallRecord>('POST', '/api/v1/calls', { callerPhone, direction }),
  answer: (id: string) => apiSend<CallRecord>('PUT', `/api/v1/calls/${id}/answer`),
  complete: (id: string, recordingUrl?: string) =>
    apiSend<CallRecord>('PUT', `/api/v1/calls/${id}/complete`, { recordingUrl }),
  linkIncident: (id: string, incidentId: string) =>
    apiSend<CallRecord>('PUT', `/api/v1/calls/${id}/incident`, { incidentId }),
};
