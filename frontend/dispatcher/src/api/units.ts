import { apiGet, apiSend } from './http';
import type { Assignment, Unit, UnitStatus, UnitType } from '@/types/domain';

export const unitsApi = {
  list: () => apiGet<Unit[]>('/api/v1/units'),
  available: () => apiGet<Unit[]>('/api/v1/units/available'),
  changeStatus: (id: string, status: UnitStatus) =>
    apiSend<Unit>('PUT', `/api/v1/units/${id}/status`, { status }),
  assign: (incidentId: string, unitId: string) =>
    apiSend<Assignment>('POST', '/api/v1/assignments', { incidentId, unitId }),
  autoDispatch: (incidentId: string, requiredType: UnitType, latitude: number, longitude: number) =>
    apiSend<Assignment>('POST', '/api/v1/assignments/auto',
      { incidentId, requiredType, latitude, longitude }),
  byIncident: (incidentId: string) =>
    apiGet<Assignment[]>(`/api/v1/incidents/${incidentId}/assignments`),
};
