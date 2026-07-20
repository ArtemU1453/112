import { configureStore } from '@reduxjs/toolkit';
import incidentsReducer from '@/features/incidents/incidentsSlice';
import unitsReducer from '@/features/units/unitsSlice';
import callsReducer from '@/features/calls/callsSlice';

export const store = configureStore({
  reducer: {
    incidents: incidentsReducer,
    units: unitsReducer,
    calls: callsReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
