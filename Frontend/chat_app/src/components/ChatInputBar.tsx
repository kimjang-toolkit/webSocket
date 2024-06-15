import styled from 'styled-components';
import AddFileButton from '@assets/icons/addFileButton.svg';
import SendButton from '@assets/icons/sendButton.svg';

function ChatInputBar() {
  return (
    <ChatInputContainer>
      <AddFileButton />
      <InputBar />
      <SendButton />
    </ChatInputContainer>
  );
}

export default ChatInputBar;

const ChatInputContainer = styled.div`
  // position: fixed;
  // bottom: 0;
  display: flex;
  height: 30px;
  background: gray;
`;
const InputBar = styled.input``;
