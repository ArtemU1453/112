import { apiGet } from './http';
import type { AuditEvent, Page } from '@/types/domain';

export const auditApi = {
  search: (params: Record<string, string>, page = 0, size = 50) => {
    const query = new URLSearchParams({ ...params, page: String(page), size: String(size) });
    return apiGet<Page<AuditEvent>>(`/api/v1/audit/events?${query.toString()}`);
  },
};
