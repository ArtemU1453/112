import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { callsApi } from '@/api/calls';
import type { CallRecord } from '@/types/domain';

interface CallsState {
  active: CallRecord[];
  loading: boolean;
}

const initialState: CallsState = { active: [], loading: false };

export const fetchActiveCalls = createAsyncThunk('calls/active', () => callsApi.active());

const callsSlice = createSlice({
  name: 'calls',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchActiveCalls.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchActiveCalls.fulfilled, (state, action) => {
        state.loading = false;
        state.active = action.payload;
      });
  },
});

export default callsSlice.reducer;
