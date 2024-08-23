import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { createPortal } from 'react-dom';
import CreateChatRoomModal from '@/components/Modal/CreateChatRoomModal';
import { useCallback, useEffect, useRef } from 'react';

const Modal = () => {
  const { isOpen, type } = useSelector((state: RootState) => state.modal);
  const ref = useRef<HTMLDialogElement>(null);
  const renderContent = useCallback(() => {
    switch (type) {
      case 'CREATE_CHAT_ROOM':
        return <CreateChatRoomModal />;
      default:
        return null;
    }
  }, [type]);

  useEffect(() => {
    if (isOpen) {
      ref.current?.showModal();
    } else {
      ref.current?.close();
    }
  }, [isOpen]);

  return createPortal(<Dialog ref={ref}>{renderContent()}</Dialog>, document.getElementById('modal-root')!);
};

export default Modal;

const Dialog = styled.dialog`
  border: 0;
  margin: 0 auto;
  margin-top: 20%;
  padding: 1.2rem 1.5rem;
  border-radius: 10px;
`;
