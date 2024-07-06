import { createChatRoom } from '@/apis/chat';
import { closeModal } from '@/redux/modalSlice';
import { Button, Input, SubHeading } from '@/styles/Common';
import { useRef } from 'react';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';

function CreateChatRoomModal() {
  const dispatch = useDispatch();
  const IDRef = useRef<HTMLInputElement>(null);
  const roomNameRef = useRef<HTMLInputElement>(null);
  const handleCancle = () => {
    dispatch(closeModal());
  };
  const handleSubmit = () => {
    const participants = [Number(IDRef.current?.value)];
    const roomName = roomNameRef.current?.value ?? 'UnNamed채팅방';
    const res = createChatRoom({ participants, roomName });
  };
  return (
    <ModalContent>
      <SubHeading $margin="0px 0px">초대할 ID</SubHeading>
      <Input ref={IDRef} />
      <SubHeading $margin="0px 0px">채팅방 이름</SubHeading>
      <Input ref={roomNameRef} />
      <Menu>
        <Button onClick={handleCancle} />
        <Button onClick={handleSubmit} />
      </Menu>
    </ModalContent>
  );
}

export default CreateChatRoomModal;

const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
`;
const Menu = styled.div`
  display: flex;
`;
