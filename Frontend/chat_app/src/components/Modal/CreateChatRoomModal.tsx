import { createChatRoom } from '@/apis/chat';
import { closeModal } from '@/redux/modalSlice';
import { Button, Input, SubHeading } from '@/styles/Common';
import { useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

function CreateChatRoomModal() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const IDRef = useRef<HTMLInputElement>(null);
  const roomNameRef = useRef<HTMLInputElement>(null);
  const handleCancle = () => {
    dispatch(closeModal());
  };
  const handleSubmit = async () => {
    const participants = [Number(IDRef.current?.value)];
    const roomName = roomNameRef.current?.value ?? 'UnNamed채팅방';
    const res = await createChatRoom({ participants, roomName });
    navigate(`/chat/${res.roomId}`);
    console.log('res', res.roomId);
  };
  return (
    <ModalContent>
      <SubHeading $padding="0.2rem 0.1rem" $margin="0px 0px">
        초대할 ID
      </SubHeading>
      <Input ref={IDRef} />
      <SubHeading $padding="0.2rem 0.1rem" $margin="0px 0px">
        채팅방 이름
      </SubHeading>
      <Input ref={roomNameRef} />
      <Menu>
        <Button onClick={handleCancle}>취소</Button>
        <Button onClick={handleSubmit}>확인</Button>
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
  justify-content: center;
  align-items: stretch;
  gap: 0.5rem;
  margin-top: 1rem;
  height: 30px;
`;
