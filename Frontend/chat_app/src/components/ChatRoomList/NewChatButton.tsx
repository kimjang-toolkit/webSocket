import { Button } from '@/styles/Common';
import styled from 'styled-components';

interface NewChatButtonProps {
  onClick: () => void;
}

function NewChatButton({ onClick }: NewChatButtonProps) {
  return (
    <Wrapper>
      <Button onClick={onClick}>채팅방 생성</Button>
    </Wrapper>
  );
}

export default NewChatButton;

const Wrapper = styled.div`
  display: flex;
  height: 40px;
  margin: 12px;
`;
