import { configureStore } from '@reduxjs/toolkit';
import webSocketReducer from '@/redux/webSocketSlice';
import usersReducer from '@/redux/userSlice';

const store = configureStore({
  reducer: { webSocket: webSocketReducer, user: usersReducer },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['webSocket/setClient', 'webSocket/setConnected', 'webSocket/initializeWebSocket/fulfilled'],
        ignoredPaths: ['webSocket.client'],
      },
    }),
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
