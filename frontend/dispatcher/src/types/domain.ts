export type IncidentType =
  | 'FIRE' | 'MEDICAL' | 'POLICE' | 'GAS_LEAK' | 'TRAFFIC_ACCIDENT'
  | 'WATER_RESCUE' | 'HAZMAT' | 'TECHNOLOGICAL' | 'NATURAL' | 'FALSE_ALARM' | 'OTHER';

export type IncidentPriority = 'CRITICAL' | 'HIGH' | 'MEDIUM' | 'LOW';

export type IncidentStatus =
  | 'RECEIVED' | 'CLASSIFIED' | 'DISPATCHED' | 'IN_PROGRESS'
  | 'RESOLVED' | 'CLOSED' | 'CANCELLED';

export interface Incident {
  id: string;
  number: string;
  type: IncidentType;
  priority: IncidentPriority;
  status: IncidentStatus;
  description: string;
  address: string;
  latitude?: number;
  longitude?: number;
  callerPhone?: string;
  callerName?: string;
  casualtiesCount: number;
  callId?: string;
  createdBy: string;
  closedReason?: string;
  createdAt: string;
  updatedAt: string;
}

export interface IncidentHistory {
  action: string;
  actor: string;
  comment?: string;
  occurredAt: string;
}

export interface IncidentStats {
  totalActive: number;
  totalToday: number;
  byStatus: Record<string, number>;
  byType: Record<string, number>;
  byPriority: Record<string, number>;
}

export type UnitStatus =
  | 'AVAILABLE' | 'DISPATCHED' | 'EN_ROUTE' | 'ON_SCENE' | 'RETURNING' | 'OUT_OF_SERVICE';

export type UnitType =
  | 'FIRE_TRUCK' | 'LADDER_TRUCK' | 'AMBULANCE' | 'RESCUE_SQUAD' | 'POLICE_PATROL'
  | 'GAS_SERVICE' | 'HAZMAT_UNIT' | 'WATER_RESCUE' | 'COMMAND_VEHICLE';

export interface Unit {
  id: string;
  callSign: string;
  type: UnitType;
  status: UnitStatus;
  stationId: string;
  stationName: string;
  crewSize: number;
  baseLatitude: number;
  baseLongitude: number;
  currentLatitude?: number;
  currentLongitude?: number;
  activeIncidentId?: string;
  updatedAt: string;
}

export interface Assignment {
  id: string;
  incidentId: string;
  unitId: string;
  unitCallSign: string;
  status: 'ACTIVE' | 'COMPLETED' | 'RECALLED';
  assignedBy: string;
  distanceKm?: number;
  assignedAt: string;
  completedAt?: string;
}

export interface CallRecord {
  id: string;
  callerPhone: string;
  direction: 'INBOUND' | 'OUTBOUND';
  status: string;
  operator?: string;
  recordingUrl?: string;
  transcript?: string;
  incidentId?: string;
  startedAt: string;
  answeredAt?: string;
  endedAt?: string;
  durationSeconds?: number;
}

export interface AuditEvent {
  id: string;
  service: string;
  action: string;
  entityType: string;
  entityId: string;
  actor: string;
  details: Record<string, unknown>;
  occurredAt: string;
  recordedAt: string;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}
