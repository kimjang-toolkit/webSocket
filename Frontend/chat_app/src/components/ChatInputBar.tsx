import styled from 'styled-components';
function ChatInputBar() {
  return (
    <ChatInputContainer>
      <InputBar />
    </ChatInputContainer>
  );
}

export default ChatInputBar;

const ChatInputContainer = styled.div`
  display: flex;
`;
const InputBar = styled.input``;
