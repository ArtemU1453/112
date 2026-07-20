import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { store } from '@/app/store';
import { upsertIncident } from '@/features/incidents/incidentsSlice';
import { updateUnitStatus } from '@/features/units/unitsSlice';
import type { Incident } from '@/types/domain';
import keycloak from '@/api/keycloak';

interface RealtimeEvent<T = Record<string, unknown>> {
  type: string;
  payload: T;
  timestamp: string;
}

const WS_URL = import.meta.env.VITE_WS_URL ?? '/ws';

let client: Client | null = null;

export function connectRealtime(): void {
  if (client?.active) return;

  client = new Client({
    webSocketFactory: () => new SockJS(WS_URL),
    connectHeaders: keycloak.token ? { Authorization: `Bearer ${keycloak.token}` } : {},
    reconnectDelay: 5000,
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,
    onConnect: () => {
      client?.subscribe('/topic/incidents', onIncidentMessage);
      client?.subscribe('/topic/units', onUnitMessage);
      client?.subscribe('/topic/dispatch', onDispatchMessage);
    },
  });

  client.activate();
}

export function disconnectRealtime(): void {
  client?.deactivate();
  client = null;
}

function onIncidentMessage(message: IMessage): void {
  const event = JSON.parse(message.body) as RealtimeEvent;
  const p = event.payload as Record<string, unknown>;
  const incident: Partial<Incident> = {
    id: String(p.incidentId ?? p.id ?? ''),
    number: String(p.number ?? ''),
    type: (p.type as Incident['type']) ?? 'OTHER',
    priority: (p.priority as Incident['priority']) ?? 'MEDIUM',
    status: (p.status as Incident['status']) ?? 'RECEIVED',
    address: String(p.address ?? ''),
    latitude: p.latitude as number | undefined,
    longitude: p.longitude as number | undefined,
    description: String(p.description ?? ''),
    casualtiesCount: Number(p.casualtiesCount ?? 0),
  };
  if (incident.id) {
    store.dispatch(upsertIncident(incident as Incident));
  }
}

function onUnitMessage(message: IMessage): void {
  const event = JSON.parse(message.body) as RealtimeEvent;
  const p = event.payload as Record<string, unknown>;
  if (p.unitId && p.status) {
    store.dispatch(updateUnitStatus({ unitId: String(p.unitId), status: String(p.status) }));
  }
}

function onDispatchMessage(message: IMessage): void {
  const event = JSON.parse(message.body) as RealtimeEvent;
  // Событие назначения наряда отражается обновлением статуса подразделения и карточки.
  console.info('dispatch event', event.type, event.payload);
}
