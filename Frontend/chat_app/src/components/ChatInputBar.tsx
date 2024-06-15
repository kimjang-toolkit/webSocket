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
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30px;
`;
const InputBar = styled.input`
  border-radius: 3.727px;
  background: #f7f7fc;
  width: 259.935px;
  padding: 5.59px 7.453px;
`;
