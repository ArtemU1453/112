import keycloak from './keycloak';

const BASE_URL = import.meta.env.VITE_API_BASE ?? '';

async function authHeaders(): Promise<Record<string, string>> {
  try {
    await keycloak.updateToken(30);
  } catch {
    keycloak.login();
  }
  const headers: Record<string, string> = { 'Content-Type': 'application/json' };
  if (keycloak.token) {
    headers.Authorization = `Bearer ${keycloak.token}`;
  }
  return headers;
}

export async function apiGet<T>(path: string): Promise<T> {
  const response = await fetch(`${BASE_URL}${path}`, { headers: await authHeaders() });
  if (!response.ok) throw await toError(response);
  return response.json() as Promise<T>;
}

export async function apiSend<T>(method: string, path: string, body?: unknown): Promise<T> {
  const response = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: await authHeaders(),
    body: body === undefined ? undefined : JSON.stringify(body),
  });
  if (!response.ok) throw await toError(response);
  const text = await response.text();
  return (text ? JSON.parse(text) : undefined) as T;
}

async function toError(response: Response): Promise<Error> {
  let detail = response.statusText;
  try {
    const data = await response.json();
    detail = data.detail ?? detail;
  } catch {
    /* ignore parse errors */
  }
  return new Error(`${response.status}: ${detail}`);
}
