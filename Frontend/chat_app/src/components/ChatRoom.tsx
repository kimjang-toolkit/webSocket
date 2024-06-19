import { chatFormat } from '@/types/types';
import ChatBubble from './ChatBubble';
import styled from 'styled-components';

interface chatRoomProps {
  chatDatas: chatFormat[];
}
function ChatRoom({ chatDatas }: chatRoomProps) {
  return (
    <ChatsContainer>
      {chatDatas.map((chat, index) => {
        const hour = String(chat.createdDate.hour).padStart(2, '0');
        const minute = String(chat.createdDate.min).padStart(2, '0');
        console.log('chat userId', chat.userId, chat.userId === 1);
        return (
          <ChatBubble
            key={index}
            isOthers={!chat.userId === 1}
            data={{ content: chat.content, published: `${hour}${minute}` }}
          />
        );
      })}
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
