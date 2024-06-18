import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Client } from '@stomp/stompjs';

interface webSocketState {
  client: Client | null;
  isConnected: boolean;
}
const initialState: webSocketState = {
  client: null,
  isConnected: false,
};

export const initializeWebSocket = createAsyncThunk('webSocket/initializeWebSocket', async (_, { dispatch }) => {
  const client = new Client({
    brokerURL: `${import.meta.env.VITE_BROKER_URL}/gs-guide-websocket`,
    debug: (str) => {
      console.log('bug', str);
    },
    reconnectDelay: 0,
    heartbeatIncoming: 1000,
    heartbeatOutgoing: 1000,
  });

  client.onConnect = () => {
    console.log('WebSocket connected');
    // Subscribe to any topics here
    client.subscribe('/notification/room/1', (message) => {
      console.log('Chat room created:', message.body);
    });
    dispatch(setConnected(true));
  };

  client.activate();

  return client;
});

//TypeScript에선 PayloadAction을 통해 action타입 정의
export const webSocketSlice = createSlice({
  name: 'webSocket',
  initialState,
  reducers: {
    setClient: (state, action: PayloadAction<Client>) => {
      state.client = action.payload;
    },
    setConnected: (state, action: PayloadAction<boolean>) => {
      state.isConnected = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(initializeWebSocket.pending, () => {
        console.log('initializing');
      })
      .addCase(initializeWebSocket.fulfilled, (state, action) => {
        console.log('fullfiled action', action);
        state.client = action.payload;
        console.log('Wbsocket connection established');
      })
      .addCase(initializeWebSocket.rejected, (state, action) => {
        state.isConnected = false;
        console.error('WebSocket connection failed:', action.error);
      });
  },
});

// Action creators are generated for each case reducer function
export const { setClient, setConnected } = webSocketSlice.actions;

export default webSocketSlice.reducer;
