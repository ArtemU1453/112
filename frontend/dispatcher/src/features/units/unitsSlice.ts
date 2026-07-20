import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { unitsApi } from '@/api/units';
import type { Unit } from '@/types/domain';

interface UnitsState {
  units: Unit[];
  loading: boolean;
  error?: string;
}

const initialState: UnitsState = { units: [], loading: false };

export const fetchUnits = createAsyncThunk('units/list', () => unitsApi.list());

const unitsSlice = createSlice({
  name: 'units',
  initialState,
  reducers: {
    updateUnitStatus(state, action: PayloadAction<{ unitId: string; status: string }>) {
      const unit = state.units.find((u) => u.id === action.payload.unitId);
      if (unit) {
        unit.status = action.payload.status as Unit['status'];
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchUnits.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchUnits.fulfilled, (state, action) => {
        state.loading = false;
        state.units = action.payload;
      })
      .addCase(fetchUnits.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export const { updateUnitStatus } = unitsSlice.actions;
export default unitsSlice.reducer;
