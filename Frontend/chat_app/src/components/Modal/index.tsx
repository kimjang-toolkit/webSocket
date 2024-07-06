import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { createPortal } from 'react-dom';

const Modal = () => {
  const { isOpen, content } = useSelector((state: RootState) => state.modal);

  return (
    isOpen &&
    createPortal(
      <Dialog>
        <ModalContent>{content}</ModalContent>
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
