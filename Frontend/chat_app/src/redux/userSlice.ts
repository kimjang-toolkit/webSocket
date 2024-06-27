import { PayloadAction, createSlice } from '@reduxjs/toolkit';

interface userState {
  id: number | null;
  name: string | null;
  accessToken: string | null;
}
const initialState: userState = {
  id: null,
  name: null,
  accessToken: null,
};

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser: (state, action: PayloadAction<userState>) => {
      state.id = action.payload.id;
      state.name = action.payload.name;
      state.accessToken = action.payload.accessToken;
    },
  },
});
export const { setUser } = userSlice.actions;
export default userSlice.reducer;
