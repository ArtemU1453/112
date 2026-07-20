import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { incidentsApi, IncidentCreatePayload } from '@/api/incidents';
import type { Incident, IncidentStats } from '@/types/domain';

interface IncidentsState {
  active: Incident[];
  stats?: IncidentStats;
  selected?: Incident;
  loading: boolean;
  error?: string;
}

const initialState: IncidentsState = { active: [], loading: false };

export const fetchActiveIncidents = createAsyncThunk('incidents/active', () =>
  incidentsApi.active());

export const fetchIncidentStats = createAsyncThunk('incidents/stats', () =>
  incidentsApi.stats());

export const createIncident = createAsyncThunk('incidents/create',
  (payload: IncidentCreatePayload) => incidentsApi.create(payload));

export const selectIncident = createAsyncThunk('incidents/byId', (id: string) =>
  incidentsApi.byId(id));

const incidentsSlice = createSlice({
  name: 'incidents',
  initialState,
  reducers: {
    upsertIncident(state, action: PayloadAction<Incident>) {
      const index = state.active.findIndex((i) => i.id === action.payload.id);
      const terminal = ['CLOSED', 'CANCELLED'].includes(action.payload.status);
      if (terminal) {
        state.active = state.active.filter((i) => i.id !== action.payload.id);
      } else if (index >= 0) {
        state.active[index] = action.payload;
      } else {
        state.active.unshift(action.payload);
      }
    },
    clearSelected(state) {
      state.selected = undefined;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchActiveIncidents.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(fetchActiveIncidents.fulfilled, (state, action) => {
        state.loading = false;
        state.active = action.payload;
      })
      .addCase(fetchActiveIncidents.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(fetchIncidentStats.fulfilled, (state, action) => {
        state.stats = action.payload;
      })
      .addCase(createIncident.fulfilled, (state, action) => {
        state.active.unshift(action.payload);
      })
      .addCase(selectIncident.fulfilled, (state, action) => {
        state.selected = action.payload;
      });
  },
});

export const { upsertIncident, clearSelected } = incidentsSlice.actions;
export default incidentsSlice.reducer;
