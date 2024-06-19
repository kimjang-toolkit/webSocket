import ChatBubble from './ChatBubble';
import styled from 'styled-components';
function ChatRoom() {
  return (
    <ChatsContainer>
      <ChatBubble isUsers={true} data={{ content: 'Hello world This is fucking crazy night you', published: '1640' }} />
    </ChatsContainer>
  );
}

export default ChatRoom;

const ChatsContainer = styled.main`
  display: flex;
  flex-direction: column;
  background: white;
  gap: 8px;
`;
