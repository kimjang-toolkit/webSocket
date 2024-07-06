import { Button } from '@/styles/Common';
import React from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { closeModal } from '@/redux/modalSlice';
import { createPortal } from 'react-dom';

interface ModalProps {
  content: React.ReactNode;
  onClickConfirm: () => void;
}

const Modal: React.FC<ModalProps> = ({ content, onClickConfirm }) => {
  const dispatch = useDispatch();
  const isOpen = useSelector((state: RootState) => state.modal.isOpen);

  return (
    isOpen &&
    createPortal(
      <Dialog>
        <ModalContent>
          {content}
          <Menu>
            <Button onClick={() => dispatch(closeModal())}>취소</Button>
            <Button onClick={onClickConfirm}>확인</Button>
          </Menu>
        </ModalContent>
      </Dialog>,
      document.getElementById('modal-root')!,
    )
  );
};

export default Modal;

const Dialog = styled.dialog`
  /* dialog 스타일 */
`;

const ModalContent = styled.div`
  /* ModalContent 스타일 */
`;

const Menu = styled.menu`
  display: flex;
  justify-content: space-between;
`;
