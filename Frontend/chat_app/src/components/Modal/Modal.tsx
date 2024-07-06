import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { createPortal } from 'react-dom';
import CreateChatRoomModal from '@/components/Modal/CreateChatRoomModal';
import { useRef } from 'react';

const Modal = () => {
  const { isOpen, type } = useSelector((state: RootState) => state.modal);
  const ref = useRef<HTMLDialogElement>(null);
  const renderContent = () => {
    switch (type) {
      case 'CREATE_CHAT_ROOM':
        return <CreateChatRoomModal />;
      default:
        return null;
    }
  };
  if (isOpen) {
    ref.current?.showModal();
  } else {
    ref.current?.close();
  }

  return createPortal(
    <Dialog ref={ref}>
      <ModalContent>{renderContent()}</ModalContent>
    </Dialog>,
    document.getElementById('modal-root')!,
  );
};

export default Modal;

const Dialog = styled.dialog``;

const ModalContent = styled.div`
  /* ModalContent 스타일 */
`;
