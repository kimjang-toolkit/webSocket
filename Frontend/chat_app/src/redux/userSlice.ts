import { PayloadAction, createSlice } from '@reduxjs/toolkit';

interface userState {
  id: number | null;
  name: string | null;
  profileImg: string | null;
  accessToken: string | null;
}
interface tokenState {
  accessToken: string;
}
const initialState: userState = {
  id: null,
  name: null,
  profileImg: null,
  accessToken: null,
};

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    initUser: (state) => {
      state.id = null;
      state.name = null;
      state.profileImg = null;
      state.accessToken = null;
    },
    setUser: (state, action: PayloadAction<userState>) => {
      state.id = action.payload.id;
      state.name = action.payload.name;
      state.profileImg = action.payload.profileImg;
      state.accessToken = action.payload.accessToken;
    },
    setToken: (state, action: PayloadAction<tokenState>) => {
      state.accessToken = action.payload.accessToken;
    },
  },
});
export const { setUser, setToken, initUser } = userSlice.actions;
export default userSlice.reducer;
