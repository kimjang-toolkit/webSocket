import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Client, StompSubscription } from '@stomp/stompjs';

interface webSocketState {
  client: Client | null;
  isConnected: boolean;
  subscriptions: Record<string, StompSubscription>;
}
const initialState: webSocketState = {
  client: null,
  isConnected: false,
  subscriptions: {},
};

export const initializeWebSocket = createAsyncThunk(
  'webSocket/initializeWebSocket',
  async ({ userId, accessToken }: { userId: number; accessToken: string }, { dispatch }) => {
    const client = new Client({
      brokerURL: `${import.meta.env.VITE_BROKER_URL}/gs`,
      connectHeaders: { Authorization: accessToken },
      debug: (str) => {
        console.log('bug', str);
      },
      reconnectDelay: 0,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
    });

    client.onConnect = () => {
      client.subscribe(`/notification/room/${userId}`, (message) => {
        console.log('Chat room created:', message.body);
      });
      dispatch(setConnected(true));
    };

    client.activate();

    return client;
  },
);

//reducer(${payload}')
// onCOnnect( ){
//subscirb${payload.}}
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
    addSubscription(state, action: PayloadAction<{ roomId: string; subscription: StompSubscription }>) {
      const { roomId, subscription } = action.payload;
      state.subscriptions[roomId] = subscription;
    },
    removeSubscription(state, action: PayloadAction<{ roomId: string }>) {
      const { roomId } = action.payload;
      if (state.subscriptions[roomId]) {
        state.subscriptions[roomId].unsubscribe();
        delete state.subscriptions[roomId];
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(initializeWebSocket.pending, () => {
        console.log('initializing');
      })
      .addCase(initializeWebSocket.fulfilled, (state, action) => {
        state.client = action.payload;
      })
      .addCase(initializeWebSocket.rejected, (state, action) => {
        state.isConnected = false;
        console.error('WebSocket connection failed:', action.error);
      });
  },
});

// Action creators are generated for each case reducer function
export const { setClient, setConnected, addSubscription, removeSubscription } = webSocketSlice.actions;

export default webSocketSlice.reducer;
