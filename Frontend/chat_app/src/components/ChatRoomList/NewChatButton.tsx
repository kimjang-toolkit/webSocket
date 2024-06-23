import styled from 'styled-components';

function NewChatButton() {
  return <ButtonContainer>채팅방 생성</ButtonContainer>;
}

export default NewChatButton;

const ButtonContainer = styled.button`
  display: flex;
  height: 40px;
  margin: 8px 12px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  align-self: stretch;
  border-radius: 8px;
  background: #000;
  color: white;
`;
