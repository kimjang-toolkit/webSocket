import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type modalType = 'CREATE_CHAT_ROOM' | null;
interface ModalState {
  isOpen: boolean;
  type: modalType;
}

const initialState: ModalState = {
  isOpen: false,
  type: null,
};

const modalSlice = createSlice({
  name: 'modal',
  initialState,
  reducers: {
    openModal: (state, action: PayloadAction<{ type: modalType }>) => {
      state.isOpen = true;
      state.type = action.payload.type;
    },
    closeModal: (state) => {
      state.isOpen = false;
      state.type = null;
    },
  },
});

export const { openModal, closeModal } = modalSlice.actions;
export default modalSlice.reducer;
