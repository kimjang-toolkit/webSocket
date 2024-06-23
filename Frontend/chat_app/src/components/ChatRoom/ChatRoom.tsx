import { chatFormat } from '@/types/types';
import ChatBubble from './ChatBubble';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

interface chatRoomProps {
  chatDatas: chatFormat[];
}
function ChatRoom({ chatDatas }: chatRoomProps) {
  const user = useSelector((state: RootState) => state.user);
  console.log('chatDatas', chatDatas);
  return (
    <ChatsContainer>
      {chatDatas.map((chat, index) => {
        const hour = String(chat.createDate?.hour).padStart(2, '0');
        const minute = String(chat.createDate.min).padStart(2, '0');
        return (
          <ChatBubble
            key={index}
            isOthers={chat.sender.id !== user.id}
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
